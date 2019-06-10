package nl.inholland.controller;


import nl.inholland.BankingApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankingApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountsApiControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @Before
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithUserDetails(value = "bank@bank.com") //role owner
    @Test
    public void getAllBankAccounts_shouldRetrieveAnArrayOfAccounts() throws Exception {

        mvc.perform(get("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .param("dateOfOpening", LocalDate.now().toString())
                .param("accountType", "current"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].accountNumber").exists());
    }

    @WithUserDetails(value = "bank@bank.com") //role owner
    @Test
    public void getAllBankAccountsByOwnerOrEmployee_shouldSucceedWith200() throws Exception {

        mvc.perform(get("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .param("dateOfOpening", LocalDate.now().toString())
                .param("accountType", "current"))
                .andExpect(status().isOk());
    }

    @WithUserDetails(value = "example@example.com") //role employee
    @Test
    public void getAllBankAccountsByCustomer_shouldReturnForbidden403() throws Exception {

        mvc.perform(get("/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .param("dateOfOpening", LocalDate.now().toString())
                .param("accountType", "current"))
                .andExpect(status().isForbidden());
    }


    @WithUserDetails(value = "bank@bank.com") //role owner
    @Test
    public void getBankAccountByUserId_shouldRetrieveAnArrayOfAccounts() throws Exception {

        mvc.perform(get("/accounts/{userId}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .param("accountType", "current"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].accountNumber").exists());
    }

    @WithUserDetails(value = "bank@bank.com") //role owner
    @Test
    public void getBankAccountByUserId_byOwnerOrEmployee_shouldSucceedWith200() throws Exception {

        mvc.perform(get("/accounts/{userId}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .param("accountType", "current"))
                .andExpect(status().isOk());
    }

    @WithUserDetails(value = "example@gmail.com") //role employee
    @Test
    public void getBankAccountOfTheBankItself_byEmployee_shouldReturnForbidden403() throws Exception {

        mvc.perform(get("/accounts/{userId}", 1L)
                .accept(MediaType.APPLICATION_JSON)
                .param("accountType", "current"))
                .andExpect(status().isForbidden());
    }

    @WithUserDetails(value = "example@example.com") //role customer
    @Test
    public void getBankAccountByUserId_byTheCustomerHimself_shouldSucceedWith200() throws Exception {

        mvc.perform(get("/accounts/{userId}", 3L)
                .accept(MediaType.APPLICATION_JSON)
                .param("accountType", "current"))
                .andExpect(status().isOk());
    }

    @WithUserDetails(value = "example@example.com") //role customer
    @Test
    public void getAccountByUserId_byTheCustomerNotHimself_shouldReturnForbidden403() throws Exception {

        mvc.perform(get("/accounts/{userId}", 5L)
                .accept(MediaType.APPLICATION_JSON)
                .param("accountType", "current"))
                .andExpect(status().isForbidden());
    }

    @WithUserDetails(value = "example@gmail.com") //role employee
    @Test
    public void testCreateAccount_shouldReturnCreated201() throws Exception {
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

    @WithUserDetails(value = "example@gmail.com") //role employee
    @Test
    public void createBankAccountForNonExistedCustomer_shouldReturnBadRequest400() throws Exception {
        mvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"balance\": 0,\n" +
                        "    \"dateOfOpening\": \"2019-06-05\",\n" +
                        "    \"userIdentification\": 1000000000,\n" +
                        "    \"currency\": \"euro\",\n" +
                        "    \"accountStatus\": \"opened\",\n" +
                        "    \"accountType\": \"current\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails(value = "example@gmail.com") //role employee
    @Test
    public void createBankAccountForEmployeeAccount_shouldReturnBadRequest400() throws Exception {
        mvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"balance\": 0,\n" +
                        "    \"dateOfOpening\": \"2019-06-05\",\n" +
                        "    \"userIdentification\": 2,\n" +
                        "    \"currency\": \"euro\",\n" +
                        "    \"accountStatus\": \"opened\",\n" +
                        "    \"accountType\": \"current\"\n" +
                        "}"))
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails(value = "example@gmail.com") //role employee
    @Test
    public void createBankAccountByOwnerOrEmployee_shouldReturnCreated201() throws Exception {
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

    @WithUserDetails(value = "example@example.com") //role customer
    @Test
    public void createBankAccountByCustomer_shouldReturnForbidden403() throws Exception {
        mvc.perform(post("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isForbidden());
    }

    @WithUserDetails(value = "example@gmail.com") //role employee
    @Test
    public void insertInvalidAccountNumberOnCloseAccount_shouldReturnBadRequest400() throws Exception {
        mvc.perform(delete("/accounts/{accountNumber}", 923827362733L))
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails(value = "example@gmail.com") //role employee
    @Test
    public void closeBankAccountOfTheBankItself_byEmployee_shouldReturnBadRequest400() throws Exception {
        mvc.perform(delete("/accounts/{accountNumber}", 1L))
                .andExpect(status().isBadRequest());
    }

    @WithUserDetails(value = "example@gmail.com") //role employee
    @Test
    public void closeBankAccountBYOwnerOrEmployee_shouldReturnNoContent204() throws Exception {
        mvc.perform(delete("/accounts/{accountNumber}", 4L))
                .andExpect(status().isNoContent());
    }

    @WithUserDetails(value = "example@example.com") //role customer
    @Test
    public void closeBankAccountByCustomer_shouldReturnForbidden403() throws Exception {
        mvc.perform(delete("/accounts/{accountNumber}", 4L))
                .andExpect(status().isForbidden());
    }

}
