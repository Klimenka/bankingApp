package nl.inholland.controller;

import nl.inholland.configuration.BankAccountConfig;
import nl.inholland.configuration.BankingAppRunner;
import nl.inholland.model.Transaction;
import nl.inholland.repository.*;
import nl.inholland.service.AccountService;
import nl.inholland.service.TransactionService;
import nl.inholland.service.UserService;
import org.junit.Before;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TransactionsApiController.class)
public class TransactionApiControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    WebApplicationContext webApplicationContext;
    @MockBean
    private TransactionService transactionService;
    @MockBean
    private AccountRepository accountRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private AddressRepository addressRepository;
    @MockBean
    private TransactionRepository transactionRepository;
    @MockBean
    private LoginRepository loginRepository;
    @MockBean
    private BankAccountConfig bankAccountConfig;
    @MockBean
    private BankingAppRunner bankingAppRunner;
    private Transaction transaction;


    @Before
    public void setUp()
    {
        transaction = new Transaction("NL49INHO0000000003", "NL76INHO0000000002", (float)1000,
                userRepository.getUserByIdEquals(3L), LocalDate.now(), Transaction.TransactionTypeEnum.TRANSACTION);
    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void whenUserCreatesTransactionShouldReturnBadRequest() throws Exception
    {
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(transaction.toString()))
                 .andExpect(status().isBadRequest());
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
    public void getAllTransaction_ByAccountNumber_ShouldReturnIsMovingTemporarily() throws Exception {
        mvc.perform(get("/transactions?accountNumber=NL49INHO0000000003")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMovedTemporarily());
    }

    @Test
    public void getTransactionByTransactionIdShouldReturnIsMovingTemporarily() throws Exception
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
