package nl.inholland.controller;

import nl.inholland.BankingApp;
import nl.inholland.configuration.BankingAppRunner;
import nl.inholland.model.Login;
import nl.inholland.model.User;

import nl.inholland.repository.UserRepository;
import nl.inholland.service.UserService;
import org.json.JSONArray;
import org.json.JSONException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.*;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = BankingAppRunner.class)
@WebAppConfiguration
public class UserIT {

    @Autowired
    private MockMvc mvc;

    private UserService userService;

    private HttpHeaders headers = new HttpHeaders();


  /*  @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void testGetAllUsersShouldRetrieveAnArrayOfUsers() throws Exception {

       // HttpEntity<String> entity = new HttpEntity<>(null, headers);
        mvc.perform(get("/users"))
                .andExpect(status().isOk());
    }
*/

}
