package nl.inholland.controller;

import nl.inholland.model.*;

import java.util.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsersApiControllerIntegrationTest {

    @Autowired
    private UsersApi api;

    private User user;
    @Before
    public void setup() {
        user = new Customer("KL Poll", "Kile Poll",
                User.SexEnum.fromValue("male"), "21.12.1972", new Address("Example",
                111, "1111OP", "Nen", "The Netherlands"),
                new Address("Example",111, "1111OP", "Nen",
                        "The Netherlands"), "+3111132156", "example@gmail.com",
                User.CommercialMessagesEnum.fromValue("by bankmail"), User.PreferredLanguageEnum.fromValue("Dutch"),
                "Customer");
    }
    //  @Test
 /*   public void addUserTest() throws Exception {

        ResponseEntity<User> responseEntity = api.addUser(user);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        System.out.println(responseEntity.getBody().getId());
    }
*/
    //   @Test
//    public void createUserTokenTest() throws Exception {
//        Login body = new Login();
//        ResponseEntity<Void> responseEntity = api.createUserToken(body);
//        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
//    }

    @Test
    public void getUserByIdTest() throws Exception {
        Long userId = 1L;
        ResponseEntity<User> responseEntity = api.getUserById(userId);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

   /* @Test
    public void getUsersTest() throws Exception {
        String userType = "";
        ResponseEntity<List<User>> responseEntity = api.getUsers(userType);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
*/
//    @Test
//    public void updateUserLoginTest() throws Exception {
//        Login body = new Login();
//        Long userId = 789L;
//        ResponseEntity<Void> responseEntity = api.updateUserLogin(body, userId);
//        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
//    }

    @Test
    public void deleteUserByIdTest() throws Exception {
        Long userId = 1L;
        ResponseEntity<Void> responseEntity = api.deleteUserById(userId);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

}
