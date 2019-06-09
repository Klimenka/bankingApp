package nl.inholland.controller;

import com.sun.glass.ui.Application;
import nl.inholland.BankingApp;
import nl.inholland.configuration.BankAccountConfig;
import nl.inholland.model.Address;
import nl.inholland.model.Customer;
import nl.inholland.model.Transaction;
import nl.inholland.model.User;
import nl.inholland.repository.UserRepository;
import nl.inholland.service.TransactionService;
import nl.inholland.service.UserService;
import org.json.JSONException;
import org.junit.Before;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankingApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionsApiControllerIntegrationTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mvc;

    @MockBean
    private UserRepository userRepository;
    @MockBean
    private UserService userService;

    private Transaction transaction;

    @Autowired
    private TransactionsApi api;

    @LocalServerPort
    private int port;
    private TestRestTemplate template = new TestRestTemplate();
    private HttpHeaders headers = new HttpHeaders();

    private String createFullUrl(String uri)
    {
        return "http://localhost:" + port + uri;
    }

    @Before
    public void setUp()
    {
        transaction = new Transaction("NL49INHO0000000003", "NL01INHO0000000002", (float)1000,
                userRepository.getUserByIdEquals(3L), LocalDate.now(), Transaction.TransactionTypeEnum.TRANSACTION);
    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void testCreateTransactionShouldReturnCreated() throws Exception
    {
        headers.add("Content-type", "application/json");

        HttpEntity<Transaction> entity = new HttpEntity<>(transaction, headers);
        ResponseEntity<String> responseEntity = template.exchange(
                createFullUrl("/transactions"), HttpMethod.POST, entity, String.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
}
