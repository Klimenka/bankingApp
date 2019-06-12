package nl.inholland.controller;

import nl.inholland.BankingApp;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = BankingApp.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsersApiControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() throws Exception {

        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    /**
     * It tests getUsers(String userType) with the path:/users GET
     * In this case, an employee calls a request.
     * This is supposed to be allowed
     */
    @Test
    @WithUserDetails(value = "example@student.inholland.nl")
    public void testByEmployeeGetAllUsersShouldReturnIsOkStatus() throws Exception {

        mockMvc.perform(get("/users")
                .param("userType", "Customer"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    /**
     * It tests getUsers(String userType) with the path:/users GET
     * In this case, a customer calls a request.
     * This is supposed to be forbidden
     */
    @Test
    @WithUserDetails(value = "example@hotmail.com")
    public void testByCustomerGetAllUsersShouldReturnIsForbiddenStatus() throws Exception {

        mockMvc.perform(get("/users"))
                .andExpect(status().isForbidden());
    }

    /**
     * It tests getUser(Long userId) with the path:/users/{userId} GET
     * In this case, an employee calls a request with a different ID.
     * This employee ID = 5, but in the request an ID = 2
     * This is supposed to be allowed
     */
    @Test
    @WithUserDetails(value = "example@student.inholland.nl")
    public void testGetOneUserByEmployee() throws Exception {

        mockMvc.perform(get("/users/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.officialName").value("EL Pond"))
        ;
    }

    /**
     * It tests getUser(Long userId) with the path:/users/{userId} GET
     * In this case, a customer calls a request with a different ID.
     * This customer ID = 4, but in the request an ID = 3
     * This is supposed to be forbidden
     */
    @Test
    @WithUserDetails(value = "example@hotmail.com")
    public void testGetOneUserByCustomerWithDifferentId() throws Exception {

        mockMvc.perform(get("/users/3"))
                .andExpect(status().isForbidden())

        ;
    }

    /**
     * It tests getUser(Long userId) with the path:/users/{userId} GET
     * In this case, a customer calls a request with the same ID.
     * This customer ID = 4
     * This is supposed to be allowed
     */
    @Test
    @WithUserDetails(value = "example@hotmail.com")
    public void testGetOneUserByCustomerWIthTheSameId() throws Exception {

        mockMvc.perform(get("/users/4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.officialName").value("JG Smitt"))
        ;
    }

    /**
     * It tests createUser(User user) with the path:/users POST
     * In this case, a customer calls a request.
     * This customer ID = 4
     * This is supposed to be forbidden
     */
    @Test
    @WithUserDetails(value = "example@hotmail.com")
    public void createUserByCustomerShouldBeForbidden() throws Exception {

        mockMvc.perform(post("/users"))
                .andExpect(status().isForbidden());

    }

    /**
     * It tests createUser(User user) with the path:/users POST
     * In this case, an employee calls a request.
     * This employee ID = 5
     * This is supposed to be allowed
     * request should return a Login object (with a new account)
     * To check it a userName is tested. It exists in a Login object, but not in User.
     */
    @Test
    @WithUserDetails(value = "example@student.inholland.nl")
    public void createUserByEmployeeShouldBeAllowedAndReturnNewLoginDetails() throws Exception {

        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\n" +
                        "        \"officialName\": \"KL Poll\",\n" +
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
                        "        \"mobileNumber\": \"+31611132156\",\n" +
                        "        \"emailAddress\": \"example@kolerp.com\",\n" +
                        "        \"commrcialMessages\": \"by bankmail\",\n" +
                        "        \"preferedLanguage\": \"Dutch\",\n" +
                        "        \"roles\":[{\"role\":\"Employee\"}]\n" +
                        "    }"))

                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userName").value("example@kolerp.com"));
    }

    /**
     * It tests deleteUser(Long userId) with the path:/users DELETE
     * In this case, an employee calls a request with a different ID.
     * This customer ID = 5
     * This is supposed to be allowed
     */
    @Test
    @WithUserDetails(value = "example@student.inholland.nl")
    public void testDeleteOneUserByEmployee() throws Exception {
        mockMvc.perform(post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "\n" +
                        "        \"officialName\": \"KL Poll\",\n" +
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
                        "        \"mobileNumber\": \"+31611132156\",\n" +
                        "        \"emailAddress\": \"gert@kolerp.com\",\n" +
                        "        \"commrcialMessages\": \"by bankmail\",\n" +
                        "        \"preferedLanguage\": \"Dutch\",\n" +
                        "        \"roles\":[{\"role\":\"Employee\"}]\n" +
                        "    }"));
        mockMvc.perform(delete("/users/6"))
                .andExpect(status().isNoContent())
        ;
    }

    /**
     * It tests deleteUser(Long userId) with the path:/users DELETE
     * In this case, a customer calls a request.
     * This is supposed to be forbidden
     */
    @Test
    @WithUserDetails(value = "example@hotmail.com")
    public void testDeleteOneUserByCustomer() throws Exception {
        mockMvc.perform(delete("/users/5"))
                .andExpect(status().isForbidden())

        ;
    }


    @Test
    @WithUserDetails(value = "example@student.inholland.nl")
    public void updateUserLoginByEmployeeShouldReturnOK() throws Exception {
        mockMvc.perform(put("/users/{userId}/updatePassword", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\": \"example@gmail.com\",\n" +
                        "\"password\": \"newPassword\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "example@hotmail.com")
    public void updateUserLoginByeCustomerShouldReturnOK() throws Exception {
        mockMvc.perform(put("/users/{userId}/updatePassword", 4L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\": \"example@hotmail.com\",\n" +
                        "\"password\": \"newPassword\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "example@student.inholland.nl")
    public void updateUserLoginByEmployeeWithNoInputShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/users/{userId}/updatePassword", 2L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\": ,\n" +
                        "\"password\": }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(value = "example@hotmail.com")
    public void updateUserLoginByCustomerWithNoInputShouldReturnBadRequest() throws Exception {
        mockMvc.perform(put("/users/{userId}/updatePassword", 4L)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\": ,\n" +
                        "\"password\": }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithUserDetails(value = "example@student.inholland.nl")
    public void createLoginTokenWithEmployeeShouldReturnOK() throws Exception {
        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\": \"example@student.inholland.nl\",\n" +
                        "\"password\": \"Password\"}"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = "example@hotmail.com")
    public void createLoginTokenWithCustomerShouldReturnOK() throws Exception {
        mockMvc.perform(post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userName\": \"example@hotmail.com\",\n" +
                        "\"password\": \"Password\"}"))
                .andExpect(status().isOk());
    }
}
