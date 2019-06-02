package nl.inholland.api;

import nl.inholland.model.Login;

import java.util.*;

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
        Object body = null;
        ResponseEntity<Object> responseEntity = api.addUser(body);
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
        ResponseEntity<Object> responseEntity = api.getUserById(userId);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void getUsersTest() throws Exception {
        String userType = "userType_example";
        ResponseEntity<List<Object>> responseEntity = api.getUsers(userType);
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
