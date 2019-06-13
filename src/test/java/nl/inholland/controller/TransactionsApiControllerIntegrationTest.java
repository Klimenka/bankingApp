package nl.inholland.controller;

import nl.inholland.BankingApp;
import org.junit.Before;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void testCreateTransactionShouldReturnIsCreated() throws Exception
    {
        mvc.perform(post("/transactions")
                .contentType("application/json")
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
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void testCreateTransactionShouldReturnBadRequest() throws Exception
    {
        mvc.perform(post("/transactions")
                .contentType("application/json")
                .content("{\"accountFrom\": \"NL49INHO0000000003\",\n" +
                        "     \"accountTo\": ,\n" +
                        "     \"amount\": 40.0,\n" +
                        "     \"userPerforming\": {\n" +
                        "         \"id\": 3, \n" +
                        "         \"officialName\": \"EL Pond\",\n" +
                        "         \"preferedName\": \"Emily Pond\",\n" +
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
                        "          \"emailAddress\": \"example@student.inholland.nl\",\n"+
                        "          \"commrcialMessages\": \"by bankmail\",\n"+
                        "          \"preferedLanguage\": \"Dutch\"\n" +
                        "          \"userRoles\": \"Employee\", \n" +
                        "         }, \n" +
                        "       \"date\": \"2019-05-02\",\n"+
                        "       \"transactionType\": \"deposit\" }"))
                .andExpect(status().isBadRequest());
    }

    @Test(expected = NestedServletException.class)
    @WithMockUser(roles = {"Employee", "Owner"})
    public void testCreateTransactionShouldThrowNestedServletException() throws Exception
    {
        mvc.perform(post("/transactions")
                .contentType("application/json")
                .content("{\n" +
                        "\n" +
                        "     \"accountFrom\": \"NL49INHO0000000003\",\n" +
                        "     \"accountTo\": \"NL76INHO000000010000\",\n" +
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
                .andExpect(status().isCreated());
    }

    @Test(expected = AssertionError.class)
    @WithMockUser(roles = {"Employee", "Owner"})
    public void testGetAllTransaction_ByAccountNumber_ShouldThrowAssertionError() throws Exception
    {
        mvc.perform(get("/transactions"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test(expected = AssertionError.class)
    @WithMockUser(roles = {"Employee", "Owner"})
    public void testGetTransaction_ByTransactionId_ShouldThrowAssertionError() throws Exception
    {
        mvc.perform(get("/getTransaction"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test(expected = AssertionError.class)
    public void testCreateTransactionShouldThrowAssertionError() throws Exception
    {
        mvc.perform(post("/transactions")
                .contentType("application/json")
                .content("{}"))
                .andExpect(status().isCreated());
    }
}
