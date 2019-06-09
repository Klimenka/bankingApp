package nl.inholland.controller;

import nl.inholland.configuration.BankAccountConfig;
import nl.inholland.configuration.BankingAppRunner;

import nl.inholland.model.*;

import nl.inholland.repository.*;
import nl.inholland.service.LoginService;
import nl.inholland.service.UserService;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import springfox.documentation.spring.web.json.Json;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(UsersApiController.class)
public class UserControllerUnitTest {

    @MockBean
    AccountRepository accountRepository;
    @MockBean
    UserRepository userRepository;
    @MockBean
    AddressRepository addressRepository;
    @MockBean
    TransactionRepository transactionRepository;
    @MockBean
    private LoginService loginService;
    @MockBean
    LoginRepository loginRepository;
    @MockBean
    BankAccountConfig bankAccountConfig;
    @MockBean
    BankingAppRunner bankingAppRunner;
    @Autowired
    WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService service;
    private User user;

    @MockBean
    private Login login;

    @Before
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        user = new Employee("KL Poll", "Kile Poll",
                User.SexEnum.fromValue("male"), "21.12.1972", new Address("Example",
                111, "1111OP", "Nen", "The Netherlands"),
                new Address("Example", 111, "1111OP", "Nen",
                        "The Netherlands"), "+3111132156", "example@gmail.com",
                User.CommercialMessagesEnum.fromValue("by bankmail"), User.PreferredLanguageEnum.fromValue("Dutch"),
                "Employee", "Accountant");


    }


    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void getUsersTest() throws Exception {

        String userType = "";
        Iterable<User> allUsers = Arrays.asList(user);
        given(service.getUsers(userType)).willReturn(allUsers);

        mvc.perform(get("/users"))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void getUsersByUserIdTest() throws Exception {

        mvc.perform(get("/users/{userId}", 1L))
                .andExpect(status().isOk());

    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void whenCreateUserShouldReturnCreated() throws Exception {

        mvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{ \"officialName\": \"KL Poll\",\n" +
                        "        \"preferedName\": \"Kile Poll\",\n" +
                        "        \"sex\": \"male\",\n" +
                        "        \"dateOfBirth\": \"21.12.1972\",\n" +
                        "        \"primaryAddress\": {\n" +
                        "            \"street\": \"Example\",\n" +
                        "            \"houseNumber\": 111,\n" +
                        "            \"postCode\": \"1111OP\",\n" +
                        "            \"city\": \"Nen\",\n" +
                        "            \"country\": \"The Netherlands\"\n" +
                        "        },\n" +
                        "        \"postAddress\": {\n" +
                        "           \"street\": \"Example\",\n" +
                        "            \"houseNumber\": 111,\n" +
                        "            \"postCode\": \"1111OP\",\n" +
                        "            \"city\": \"Nen\",\n" +
                        "            \"country\": \"The Netherlands\"\n" +
                        "        },\n" +
                        "        \"mobileNumber\": \"+3111132156\",\n" +
                        "        \"emailAddress\": \"example@gmail.com\",\n" +
                        "        \"commrcialMessages\": \"by bankmail\",\n" +
                        "        \"preferedLanguage\": \"Dutch\",\n" +
                        "        \"userType\": \"Customer\"\n}"))
                .andExpect(status().isCreated());


    }

    @Test
    @WithMockUser(roles = {"Employee", "Owner"})
    public void updateUserLoginShouldReturnOK() throws Exception {

        login = new Login(user.getEmailAddress(), user);
        mvc.perform(put("/users/{userId}/updatePassword", 1L )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\": \"example@gmail.com\",\n"+
                                "\"password\": \"newPassword\"}"))
                .andExpect(status().isOk());

    }

    @Test
    public void CreateTokenShouldReturnOK() throws Exception {
        mvc.perform(
                post("/users/login" )
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"userName\": \"example@gmail.com\",\n"+
                                "\"password\": \"Password\"}"))
                .andExpect(status().isOk());

    }

}
