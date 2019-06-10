package nl.inholland.controller;

import nl.inholland.configuration.BankAccountConfig;
import nl.inholland.configuration.BankingAppRunner;
import nl.inholland.model.Transaction;
import nl.inholland.repository.*;
import nl.inholland.service.AccountService;
import nl.inholland.service.TransactionService;
import nl.inholland.service.UserService;
import org.junit.Before;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
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
    public void setUp()
    {
        transaction = new Transaction("NL49INHO0000000003", "NL01INHO0000000001", (float)1000,
                userRepository.getUserByIdEquals(3L), LocalDate.now(), Transaction.TransactionTypeEnum.TRANSACTION);
    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void whenUserCreatesTransactionShouldReturnOK() throws Exception
    {
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                        "     \"accountFrom\": \"NL49INHO0000000003\",\n" +
                        "     \"accountTo\": \"NL01INHO0000000001\",\n" +
                        "     \"amount\": 1000,\n" +
                        "     \"userPerforming\": {\n" +
                        "         \"id\": 3, \n" +
                        "         \"officialName\": \"EL Pond\",\n" +
                        "         \"preferedName\": \"Emily Pond\",\n" +
                        "         \"sex\": \"female\",\n"+
                        "         \"dateOfBirth\": \"13.10.1990\",\n" +
                        "         \"primaryAddress\": {\n"+
                        "               \"street\": \"ExampleHolm\",\n"+
                        "               \"houseNumber\": 13,\n" +
                        "               \"postCode\": \"1412KL\",\n" +
                        "               \"city\": \"Klopp\",\n" +
                        "               \"country\": \"The Netherlands\"\n" +
                        "           },\n"+
                        "          \"postAddress\": {\n"+
                        "               \"street\": \"ExampleHolm\",\n" +
                        "               \"houseNumber\": 13,\n" +
                        "               \"postCode\": \"1412KL\",\n" +
                        "               \"city\": \"Klopp\",\n" +
                        "               \"country\": \"The Netherlands\"\n" +
                        "           },\n"+
                        "          \"mobileNumber\": \"+31667533778\",\n"+
                        "          \"emailAddress\": \"example@example.com\",\n"+
                        "          \"commrcialMessages\": \"by bankmail\",\n"+
                        "          \"preferedLanguage\": \"Dutch\",\n" +
                        "         }, \n" +
                        "       \"date\": \"2019-05-2\",\n"+
                        "       \"transactionType\": \"transaction\" " +
                        "}"))
                 .andExpect(status().isCreated());
    }
}
