package nl.inholland.controller;

import com.sun.glass.ui.Application;
import nl.inholland.BankingApp;
import nl.inholland.configuration.BankAccountConfig;
import nl.inholland.model.Address;
import nl.inholland.model.Customer;
import nl.inholland.model.Transaction;
import nl.inholland.model.User;
import nl.inholland.repository.TransactionRepository;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDate;
import java.util.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankingApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransactionsApiControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mvc;


    @Before
    public void setUp()
    {
        mvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void testGetTransaction_ByTransactionId_ShouldReturnOK() throws Exception
    {
        mvc.perform(get("/getTransaction")
        .param("transactionId", "2"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void testGetAllTransaction_ByAccountNumber_ShouldReturnOK() throws Exception
    {
        mvc.perform(get("/transactions")
                .param("accountNumber", "NL49INHO0000000003"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test(expected = AssertionError.class)
    @WithMockUser(roles = {"Employee", "Owner"})
    public void testGetAllTransaction_ByAccountNumber_ShouldThrowAssertionError() throws Exception
    {
        mvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }


    @Test(expected = AssertionError.class)
    @WithMockUser(roles = {"Employee", "Owner"})
    public void testGetTransaction_ByTransactionId_ShouldThrowAssertionError() throws Exception
    {
        mvc.perform(get("/getTransaction"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON_UTF8));
    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void testCreateTransactionShouldReturnBadRequest() throws Exception
    {
        mvc.perform(post("/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \n" +
                        "     \"accountFrom\": \"NL49INHO0000000003\",\n" +
                        "     \"accountTo\": ,\n" +
                        "     \"amount\": 40.0,\n" +
                        "     \"userPerforming\": {\n" +
                        "         \"id\": 3, \n" +
                        "         \"officialName\": \"EL Pond\",\n" +
                        "         \"preferredName\": \"Emily Pond\",\n" +
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
                        "          \"commercialMessages\": \"by bankmail\",\n"+
                        "          \"preferredLanguage\": \"Dutch\"\n" +
                        "          \"userRoles\": \"Employee\", \n" +
                        "         }, \n" +
                        "       \"date\": \"2019-05-2\",\n"+
                        "       \"transactionType\": \"deposit\" " +
                        "}"))
                .andExpect(status().isBadRequest());
    }
}
