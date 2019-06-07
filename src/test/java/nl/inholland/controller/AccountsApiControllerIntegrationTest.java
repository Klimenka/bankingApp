package nl.inholland.controller;

import java.time.LocalDate;
import java.util.*;

import nl.inholland.BankingApp;
import nl.inholland.configuration.BankingAppRunner;
import nl.inholland.model.Account;
import nl.inholland.model.CurrentAccount;
import nl.inholland.model.User;
import nl.inholland.repository.AccountRepository;
import nl.inholland.service.AccountService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONArray;
import org.json.JSONException;
import org.junit.Assert;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.validation.constraints.AssertTrue;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankingApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountsApiControllerIntegrationTest {

    @LocalServerPort
    private int port;
    private TestRestTemplate template = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    private String createFullUrl(String uri) {
        return "http://localhost:" + port + uri;
    }

    @Test
    public void testGetAllAccountsShouldRetrieveAnArrayOfAccounts() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = template.exchange(
                createFullUrl("/accounts"),
                HttpMethod.GET, entity, String.class);

        JSONArray array = new JSONArray(response.getBody());
        Assert.assertTrue(array.length() >= 1);
    }

    @Test
    public void testGetAccountByUserShouldRetrieveAnArrayOfAccounts() throws Exception {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<String> response = template.exchange(
                createFullUrl("/accounts/1"),
                HttpMethod.GET, entity, String.class);

        JSONArray array = new JSONArray(response.getBody());
        Assert.assertTrue(array.length() >= 1);
    }

    @Test
    public void createBankAccountTest() throws Exception {
       /* Account body = null;
        ResponseEntity<Account> responseEntity = api.createBankAccount(body);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());*/
    }

    @Test
    public void getBankAccountTest() throws Exception {


       /* long userId = 789L;
        String accountType = "accountType_example";
        ResponseEntity<List<Account>> responseEntity = api.getBankAccount(userId, accountType);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());*/
    }

    @Test
    public void getBankAccountsTest() throws Exception {

      /*  LocalDate dateOfOpening = java.time.LocalDate.now();
        String accountType = "accountType_example";
        ResponseEntity<List<Account>> responseEntity = api.getBankAccounts(dateOfOpening, accountType);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());*/
    }

}
