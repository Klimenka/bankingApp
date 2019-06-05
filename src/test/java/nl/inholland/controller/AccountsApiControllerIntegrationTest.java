package nl.inholland.controller;

import java.time.LocalDate;
import java.util.*;

import nl.inholland.configuration.BankingAppRunner;
import nl.inholland.model.Account;
import nl.inholland.model.CurrentAccount;
import nl.inholland.model.User;
import nl.inholland.repository.AccountRepository;
import nl.inholland.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(AccountsApiController.class)
//@EnableJpaRepositories
public class AccountsApiControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AccountService service;
    private Account account;

    @Before
    public void setUp() {
        account = Mockito.mock(Account.class);
        //account= new CurrentAccount();
    }

    @Test
    public void closeBankAccountTest() throws Exception {
        /*long accountNumber = 1;
        ResponseEntity<Void> responseEntity = api.closeBankAccount(accountNumber);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());*/
    }

    @Test
    public void createBankAccountTest() throws Exception {
       /* Account body = null;
        ResponseEntity<Account> responseEntity = api.createBankAccount(body);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());*/
    }

    @Test
    public void getBankAccountTest() throws Exception {
        Iterable<Account> accounts = Arrays.asList(account);
        given(service.getBankAccount(2, "current")).willReturn(accounts);

        mvc.perform(get("/accounts").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                /*.andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].brand").value(guitar.getBrand()));*/


       /* long userId = 789L;
        String accountType = "accountType_example";
        ResponseEntity<List<Account>> responseEntity = api.getBankAccount(userId, accountType);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());*/
    }

    @Test
    public void getBankAccountsTest() throws Exception {
        Iterable<Account> allGuitars = Arrays.asList(account);
        given(service.getBankAccounts(LocalDate.now(), "current")).willReturn(allGuitars);

      /*  LocalDate dateOfOpening = java.time.LocalDate.now();
        String accountType = "accountType_example";
        ResponseEntity<List<Account>> responseEntity = api.getBankAccounts(dateOfOpening, accountType);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());*/
    }

}
