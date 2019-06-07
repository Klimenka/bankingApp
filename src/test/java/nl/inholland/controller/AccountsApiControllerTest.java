package nl.inholland.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.inholland.configuration.BankAccountConfig;
import nl.inholland.configuration.BankingAppRunner;
import nl.inholland.model.Account;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import nl.inholland.model.CurrentAccount;
import nl.inholland.repository.*;
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
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@SpringBootTest
//@AutoConfigureMockMvc
@WebMvcTest(AccountsApiController.class)
public class AccountsApiControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private AccountService service;
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
    private Account account;

    @Before
    public void setup() {
        account = Mockito.mock(Account.class);
    }

    @Test
    public void givenAccountWithUserIdAndAccountTypeShouldReturnIsOK() throws Exception {
        List<Account> accounts = Arrays.asList(account);
        given(service.getBankAccount(1, "current")).willReturn(accounts);

        mvc.perform(get("/accounts/{userId}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .param("accountType", "current"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAccountsWithDateAndAccountTypeShouldReturnIsOK() throws Exception {
        List<Account> accounts = Arrays.asList(account);
        given(service.getBankAccounts(LocalDate.now(), "current")).willReturn(accounts);

        mvc.perform(get("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .param("dateOfOpening", LocalDate.now().toString())
                .param("accountType", "current"))
                .andExpect(status().isOk());
    }

    @Test
    public void whenCreateAccountShouldReturnCreated() throws Exception {
        mvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"balance\": 0,\n" +
                        "    \"dateOfOpening\": \"2019-06-05\",\n" +
                        "    \"one\": 3,\n" +
                        "    \"currency\": \"euro\",\n" +
                        "    \"accountStatus\": \"opened\",\n" +
                        "    \"accountType\": \"current\"\n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void whenCloseAccountShouldReturnNoContent() throws Exception {

        mvc.perform(delete("/accounts/{accountNumber}", 2L))
                .andExpect(status().isNoContent());
    }

}