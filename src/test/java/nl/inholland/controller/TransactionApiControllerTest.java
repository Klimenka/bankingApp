package nl.inholland.controller;

import nl.inholland.configuration.BankAccountConfig;
import nl.inholland.configuration.BankingAppRunner;
import nl.inholland.model.*;
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
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionsApiController.class)
public class TransactionApiControllerTest
{
    @Autowired
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;
    @MockBean
    private AddressRepository addressRepository;
    @MockBean
    private LoginRepository loginRepository;
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private LoginService loginService;
    @MockBean
    private AccountService accountService;
    @MockBean
    private BankAccountConfig bankAccountConfig;
    @MockBean
    private BankingAppRunner bankingAppRunner;

    private Transaction transaction;

    @Before
    public void setUp()
    {
        User user = new Customer("EL Pond", "Emily Pond", User.SexEnum.FEMALE,
                "13.10.1990", new Address("ExampleHolm", 13,
                "1412KL", "Klopp", "Netherlands"),
                new Address("ExampleHolm", 13, "1412KL",
                        "Klopp", "Netherlands"), "+31667533778",
                "example@student.inholland.nl", User.CommercialMessagesEnum.BANKMAIL,
                User.PreferredLanguageEnum.DUTCH, "Customer");

        this.transaction = new Transaction("NL49INHO0000000003", "NL76INHO0000000002", (float)340,
                user, LocalDate.now(), Transaction.TransactionTypeEnum.TRANSACTION);
    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void givenTransaction_whenGetAllTransactionsShouldReturnOK() throws Exception
    {
        List<Transaction> transactions = Arrays.asList(transaction);
        given(transactionService.getAllTransactions(transaction.getAccountFrom(), LocalDate.now(), LocalDate.now().minusMonths(1)))
                .willReturn(transactions);

        mvc.perform(get("/transactions")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("accountNumber", "NL49INHO0000000003"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void givenTransaction_whenGetTransactionShouldReturnIsOk() throws Exception
    {
        List<Transaction> transactions = Arrays.asList(transaction);
        given(transactionService.getTransaction(transaction.getTransactionId()))
                .willReturn(transaction);

        mvc.perform(get("/getTransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .param("transactionId", "2"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void getAllTransaction_ByAccountNumber_ShouldReturnIsOk() throws Exception {
        mvc.perform(get("/transactions?accountNumber=NL49INHO0000000003")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void getTransaction_ByTransactionId_ShouldReturnIsOk() throws Exception
    {
        mvc.perform(get("/getTransaction?transactionId={transactionId}", (long)2)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void whenUserCreatesTransactionShouldReturnBadRequest() throws Exception
    {
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\n" +
                        "     \"accountFrom\": \"NL49INHO0000000003\",\n" +
                        "     \"accountTo\": \"NL76INHO0000000002\",\n" +
                        "     \"amount\": 40.0,\n" +
                        "     \"userPerforming\": {\n" +
                        "         \"id\": 3,\n" +
                        "         \"officialName\": \"EL Pond\",\n" +
                        "         \"preferedName\": \"Emily Pond\",\n" +
                        "         \"sex\": \"female\",\n" +
                        "         \"dateOfBirth\": \"13.10.1990\",\n" +
                        "         \"primaryAddress\": {\n" +
                        "               \"street\": \"ExampleHolm\",\n" +
                        "               \"houseNumber\": 13,\n" +
                        "               \"postCode\": \"1412KL\",\n" +
                        "               \"city\": \"Klopp\",\n" +
                        "               \"country\": \"The Netherlands\"\n" +
                        "           },\n" +
                        "          \"postAddress\": {\n" +
                        "               \"street\": \"ExampleHolm\",\n" +
                        "               \"houseNumber\": 13,\n" +
                        "               \"postCode\": \"1412KL\",\n" +
                        "               \"city\": \"Klopp\",\n" +
                        "               \"country\": \"The Netherlands\"\n" +
                        "           },\n" +
                        "          \"mobileNumber\": \"+31667533778\",\n" +
                        "          \"emailAddress\": \"example@student.inholland.nl\",\n" +
                        "          \"commrcialMessages\": \"by bankmail\",\n" +
                        "          \"preferedLanguage\": \"Dutch\",\n" +
                        "          \"userType\": \"Customer\"\n" +
                        "         },\n" +
                        "       \"date\": \"2019-05-02\",\n" +
                        "       \"transactionType\": \"transaction\"\n" +
                        "  }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void givenTransaction_whenGetAllTransactionsShouldReturnBadRequest() throws Exception
    {
        List<Transaction> transactions = Arrays.asList(transaction);
        given(transactionService.getAllTransactions(transaction.getAccountFrom(), LocalDate.now(), LocalDate.now().minusMonths(1)))
                .willReturn(transactions);

        mvc.perform(get("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .param("accountNumber", transaction.getAccountFrom())
                .param("dateFrom", LocalDate.now().toString())
                .param("dateTo", LocalDate.now().minusMonths(1).toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void givenTransaction_whenGetTransactionShouldReturnBadRequest() throws Exception
    {
        List<Transaction> transactions = Arrays.asList(transaction);
        given(transactionService.getTransaction(transaction.getTransactionId()))
                .willReturn(transaction);

        mvc.perform(get("/getTransaction")
                .contentType(MediaType.APPLICATION_JSON)
                .param("transactionId", "???"))
                .andExpect(status().isBadRequest());
    }

    @Test(expected = AssertionError.class)
    public void givenTransaction_whenGetTransactionShouldThrowAssertionError() throws Exception
    {
        List<Transaction> transactions = Arrays.asList(transaction);
        given(transactionService.getTransaction(transaction.getTransactionId()))
                .willReturn(transaction);

        mvc.perform(get("/getTransaction")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .param("transactionId", "2"))
                .andExpect(status().isOk());
    }

    @Test(expected = AssertionError.class)
    public void givenTransaction_whenGetAllTransactionsShouldThrowAssertionError() throws Exception
    {
        List<Transaction> transactions = Arrays.asList(transaction);
        given(transactionService.getAllTransactions(transaction.getAccountFrom(), LocalDate.now(), LocalDate.now().minusMonths(2)))
                .willReturn(transactions);

        mvc.perform(get("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .param("accountNumber", transaction.getAccountFrom())
                .param("dateFrom", LocalDate.now().toString())
                .param("dateTo", LocalDate.now().minusMonths(2).toString()))
                .andExpect(status().isBadRequest());
    }

    @Test(expected = AssertionError.class)
    public void givenTransaction_whenGetAllTransactionsByAccountNumberShouldThrowAssertionError() throws Exception
    {
        List<Transaction> transactions = Arrays.asList(transaction);
        given(transactionService.getAllTransactions(transaction.getAccountFrom(), LocalDate.now(), LocalDate.now().minusMonths(1)))
                .willReturn(transactions);

        mvc.perform(get("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .param("accountNumber", "NL49INHO0000000003"))
                .andExpect(status().isOk());
    }

    @Test(expected = AssertionError.class)
    public void whenUserCreatesTransactionShouldThrowAssertionError() throws Exception
    {
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\n" +
                        "     \"accountFrom\": \"NL49INHO0000000003\",\n" +
                        "     \"accountTo\": \"NL76INHO0000000002\",\n" +
                        "     \"amount\": 40.0,\n" +
                        "     \"userPerforming\": {\n" +
                        "         \"id\": 3,\n" +
                        "         \"officialName\": \"EL Pond\",\n" +
                        "         \"preferedName\": \"Emily Pond\",\n" +
                        "         \"sex\": \"female\",\n" +
                        "         \"dateOfBirth\": \"13.10.1990\",\n" +
                        "         \"primaryAddress\": {\n" +
                        "               \"street\": \"ExampleHolm\",\n" +
                        "               \"houseNumber\": 13,\n" +
                        "               \"postCode\": \"1412KL\",\n" +
                        "               \"city\": \"Klopp\",\n" +
                        "               \"country\": \"The Netherlands\"\n" +
                        "           },\n" +
                        "          \"postAddress\": {\n" +
                        "               \"street\": \"ExampleHolm\",\n" +
                        "               \"houseNumber\": 13,\n" +
                        "               \"postCode\": \"1412KL\",\n" +
                        "               \"city\": \"Klopp\",\n" +
                        "               \"country\": \"The Netherlands\"\n" +
                        "           },\n" +
                        "          \"mobileNumber\": \"+31667533778\",\n" +
                        "          \"emailAddress\": \"example@student.inholland.nl\",\n" +
                        "          \"commrcialMessages\": \"by bankmail\",\n" +
                        "          \"preferedLanguage\": \"Dutch\",\n" +
                        "          \"userType\": \"Customer\"\n" +
                        "         },\n" +
                        "       \"date\": \"2019-05-02\",\n" +
                        "       \"transactionType\": \"transaction\"\n" +
                        "  }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getTransaction_ByTransactionId_ShouldThrowAssertionError() throws Exception
    {
        mvc.perform(get("/getTransaction?transactionId={transactionId}", (long)2)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMovedTemporarily());
    }

    @Test
    public void getAllTransaction_ByAccountNumber_ShouldReturnIsMovingTemporarily() throws Exception {
        mvc.perform(get("/transactions?accountNumber=NL49INHO0000000003")
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
