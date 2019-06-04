package nl.inholland.controller;

import nl.inholland.model.Login;

import java.util.*;

import nl.inholland.model.User;
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

    @Test
    public void addUserTest() throws Exception {
        User user = null;
        ResponseEntity<User> responseEntity = api.addUser(user);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void createUserTokenTest() throws Exception {
        Login body = new Login();
        ResponseEntity<Void> responseEntity = api.createUserToken(body);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void deleteUserByIdTest() throws Exception {
        Long userId = 789L;
        ResponseEntity<Void> responseEntity = api.deleteUserById(userId);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void getUserByIdTest() throws Exception {
        Long userId = 789L;
        ResponseEntity<User> responseEntity = api.getUserById(userId);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void getUsersTest() throws Exception {
        String userType = "userType_example";
        ResponseEntity<List<User>> responseEntity = api.getUsers(userType);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void updateUserLoginTest() throws Exception {
        Login body = new Login();
        Long userId = 789L;
        ResponseEntity<Void> responseEntity = api.updateUserLogin(body, userId);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

}
