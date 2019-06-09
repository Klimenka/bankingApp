package nl.inholland.controller;


import nl.inholland.configuration.BankAccountConfig;
import nl.inholland.configuration.BankingAppRunner;
import nl.inholland.model.*;
import nl.inholland.repository.*;
import nl.inholland.service.AccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
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
        User user = new Employee("Bank", "Bank", User.SexEnum.MALE,
                "01.01.2019", new Address("Street", 505,
                "2032SA", "Haarlem", "Netherlands"),
                new Address("Street", 505, "2032SA",
                        "Haarlem", "Netherlands"), "0650464266",
                "bank@bank.com", User.CommercialMessagesEnum.BANKMAIL,
                User.PreferredLanguageEnum.ENGLISH, "Owner", "Manager");

        account = new CurrentAccount(0,
                LocalDate.now(), "euro", user);
    }

    @WithMockUser(roles = "Employee")
    @Test
    public void givenBankAccountWithUserIdAndAccountTypeOfTheBankItself_accessedByEmployee_shouldReturnForbidden()
            throws Exception {
        List<Account> accounts = Arrays.asList(account);
        given(service.getBankAccount(1, "current")).willReturn(accounts);

        mvc.perform(get("/accounts/{userId}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .param("accountType", "current"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = {"Employee", "Owner"})
    @Test
    public void givenBankAccountsWithDateAndAccountTypeShouldReturnIsOK() throws Exception {
        List<Account> accounts = Arrays.asList(account);
        given(service.getBankAccounts(LocalDate.now(), "current")).willReturn(accounts);

        mvc.perform(get("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .param("dateOfOpening", LocalDate.now().toString())
                .param("accountType", "current"))
                .andExpect(status().isOk());
    }

    @WithMockUser(roles = "Customer")
    @Test
    public void givenBankAccountsWithDateAndAccountTypeAccessedByCustomerShouldReturnForbidden()
            throws Exception {
        List<Account> accounts = Arrays.asList(account);
        given(service.getBankAccounts(LocalDate.now(), "current")).willReturn(accounts);

        mvc.perform(get("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .param("dateOfOpening", LocalDate.now().toString())
                .param("accountType", "current"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = {"Employee", "Owner"})
    @Test
    public void whenCreateBankAccountShouldReturnCreated() throws Exception {
        mvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"balance\": 0,\n" +
                        "    \"dateOfOpening\": \"2019-06-05\",\n" +
                        "    \"userIdentification\": 3,\n" +
                        "    \"currency\": \"euro\",\n" +
                        "    \"accountStatus\": \"opened\",\n" +
                        "    \"accountType\": \"current\"\n" +
                        "}"))
                .andExpect(status().isCreated());
    }

    @WithMockUser(roles = "Customer")
    @Test
    public void whenCustomerCreateBankAccountShouldReturnForbidden() throws Exception {
        mvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isForbidden());
    }

    @WithMockUser(roles = {"Employee", "Owner"})
    @Test
    public void whenCloseBankAccountShouldReturnNoContent() throws Exception {
        mvc.perform(delete("/accounts/{accountNumber}", 2L))
                .andExpect(status().isNoContent());
    }

    @WithMockUser(roles = "Customer")
    @Test
    public void whenCustomerCloseBankAccountShouldReturnForbidden() throws Exception {
        mvc.perform(delete("/accounts/{accountNumber}", 2L))
                .andExpect(status().isForbidden());
    }

}