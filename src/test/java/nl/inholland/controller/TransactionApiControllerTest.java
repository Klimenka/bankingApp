package nl.inholland.controller;

import nl.inholland.configuration.BankAccountConfig;
import nl.inholland.configuration.BankingAppRunner;
import nl.inholland.model.Address;
import nl.inholland.model.Customer;
import nl.inholland.model.Transaction;
import nl.inholland.model.User;
import nl.inholland.repository.*;
import nl.inholland.service.AccountService;
import nl.inholland.service.LoginService;
import nl.inholland.service.TransactionService;
import nl.inholland.service.UserService;
import org.junit.Before;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionsApiController.class)
public class TransactionApiControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private TransactionService service;
    @MockBean
    AccountRepository accountRepository;
    @MockBean
    UserRepository userRepository;
    @MockBean
    AddressRepository addressRepository;
    @MockBean
    TransactionRepository transactionRepository;
    @MockBean
    LoginRepository loginRepository;
    @MockBean
    BankAccountConfig bankAccountConfig;
    @MockBean
    BankingAppRunner bankingAppRunner;
    private Transaction transaction;

    @Before
    //@WithUserDetails(value = "example@student.inholland.nl")
    public void setUp()
    {
        User user = new Customer("EL Pond", "Emily Pond", User.SexEnum.FEMALE,
                "13.10.1990", new Address("ExampleHolm", 13,
                "1412KL", "Klopp", "Netherlands"),
                new Address("ExampleHolm", 13, "1412KL",
                        "Klopp", "Netherlands"), "+31667533778",
                "example@student.inholland.nl", User.CommercialMessagesEnum.BANKMAIL,
                User.PreferredLanguageEnum.DUTCH, "Customer");

        transaction = new Transaction("NL49INHO0000000003", "NL76INHO0000000002", (float)150,
                user, LocalDate.now(), Transaction.TransactionTypeEnum.TRANSACTION);
        transaction.setTransactionId(1L);
    }

    @Test
    @WithMockUser(roles = {"Customer"})
    public void whenUserCreatesTransactionShouldReturnBadRequest() throws Exception
    {
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transaction.toString()))
                 .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"Customer"})
    public void whenUserCreatesTransactionShouldReturnIsCreated() throws Exception
    {
        //mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"accountFrom\": \"NL49INHO0000000003\",\n" +
                        "    \"accountTo\": \"NL76INHO0000000002\",\n" +
                        "    \"amount\": 40.0,\n" +
                        "    \"userPerforming\": {\n" +
                        "        \"id\": 3,\n" +
                        "        \"officialName\": \"EL Pond\",\n" +
                        "        \"preferedName\": \"Emily Pond\",\n" +
                        "        \"sex\": \"female\",\n" +
                        "        \"dateOfBirth\": \"13.10.1990\",\n" +
                        "        \"primaryAddress\": {\n" +
                        "            \"street\": \"ExampleHolm\",\n" +
                        "            \"houseNumber\": 13,\n" +
                        "            \"postCode\": \"1412KL\",\n" +
                        "            \"city\": \"Klopp\",\n" +
                        "            \"country\": \"The Netherlands\"\n" +
                        "        },\n" +
                        "        \"postAddress\": {\n" +
                        "            \"street\": \"ExampleHolm\",\n" +
                        "            \"houseNumber\": 13,\n" +
                        "            \"postCode\": \"1412KL\",\n" +
                        "            \"city\": \"Klopp\",\n" +
                        "            \"country\": \"The Netherlands\"\n" +
                        "        },\n" +
                        "        \"mobileNumber\": \"+31667533778\",\n" +
                        "        \"emailAddress\": \"example@student.inholland.nl\",\n" +
                        "        \"commrcialMessages\": \"by bankmail\",\n" +
                        "        \"preferedLanguage\": \"Dutch\",\n" +
                        "        \"userType\": \"Customer\"\n" +
                        "    },\n" +
                        "    \"date\": \"2019-05-02\",\n" +
                        "    \"transactionType\": \"transaction\"\n" +
                        "}"))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    @WithMockUser(roles = {"Customer"})
    public void getAllTransaction_ByAccountNumber_ShouldReturnIsOk() throws Exception {
        mvc.perform(get("/transactions?accountNumber=NL49INHO0000000003")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"Customer"})
    public void getTransaction_ByTransactionId_ShouldReturnIsOk() throws Exception
    {
        mvc.perform(get("/getTransaction?transactionId={transactionId}", (long)2)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getAllTransaction_ByAccountNumber_ShouldReturnIsMovingTemporarily() throws Exception {
        mvc.perform(get("/transactions?accountNumber=NL49INHO0000000003")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMovedTemporarily());
    }

    @Test
    public void getTransaction_ByTransactionId_ShouldReturnIsMovingTemporarily() throws Exception
    {
        mvc.perform(get("/getTransaction?transactionId={transactionId}", (long)2)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMovedTemporarily());
    }

    @Test
    public void whenUserCreatesTransactionShouldReturnIsMovedTemporarily() throws Exception
    {
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transaction.toString()))
                .andExpect(status().isMovedTemporarily());
    }
}
