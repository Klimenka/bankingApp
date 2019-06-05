package nl.inholland.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.inholland.model.Account;
import nl.inholland.model.CurrentAccount;
import nl.inholland.repository.AccountRepository;
import nl.inholland.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
//@WebMvcTest(AccountsApiController.class)
public class AccountsApiControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private AccountService service;
    //private AccountRepository repository;
    private Account account;


    @Before
    public void setup() {
        account = Mockito.mock(Account.class);
        //account = new CurrentAccount();
    }


    @Test
    public void getBankAccountTest() throws Exception {
        Iterable<Account> accounts = Arrays.asList(account);
        given(service.getBankAccount(1, "current")).willReturn(accounts);

        mvc.perform(get("/accounts/{userId}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .param("accountType", "current")
                .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void getBankAccountsTest() throws Exception {
        Iterable<Account> accounts = Arrays.asList(account);
        given(service.getBankAccounts(LocalDate.now(), "current")).willReturn(accounts);

        mvc.perform(get("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .param("dateOfOpening", LocalDate.now().toString())
                .param("accountType", "current")
                .content("{}"))
                .andExpect(status().isOk());
    }


}