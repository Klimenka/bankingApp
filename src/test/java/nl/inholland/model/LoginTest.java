package nl.inholland.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class LoginTest {

    private Login login;
    private User user = new Customer("KL Poll", "Kile Poll",
            User.SexEnum.fromValue("male"), "21.12.1972", new Address("Example",
            111, "1111OP", "Nen", "The Netherlands"),
            new Address("Example", 111, "1111OP", "Nen",
                    "The Netherlands"), "0650464266", "example@gmail.com",
            User.CommercialMessagesEnum.fromValue("by bankmail"), User.PreferredLanguageEnum.fromValue("Dutch"),
            "Customer");

    @Before
    public void setUp() {
        login = new Login(user.getEmailAddress(), user);
    }

    @Test
    public void loginShouldNotBeNull() {
        assertNotNull(login);
    }

    @Test
    public void createLoginShouldNotBeNull() {
        assertNotNull(login);
    }

    @Test
    public void getUserShouldNotBeNull() {
        assertNotNull(login.getUser());
    }

    @Test
    public void setUserToUserWithNameRyanReynolds() {
        User newUser = new Customer("R Reynolds", "Ryan",
                User.SexEnum.fromValue("male"), "21.12.1972", new Address("Example",
                111, "1111OP", "Nen", "The Netherlands"),
                new Address("Example", 111, "1111OP", "Nen",
                        "The Netherlands"), "0650464266", "example@gmail.com",
                User.CommercialMessagesEnum.fromValue("by bankmail"), User.PreferredLanguageEnum.fromValue("Dutch"),
                "Customer");
        login.setUser(newUser);
        assertEquals(newUser, login.getUser());

    }

    @Test
    public void getUserNameShouldNotBeNull() {
        assertNotNull(login.getUserName());
    }

    @Test
    public void getPasswordShouldNotBeNull() {
        assertNotNull(login.getPassword());
    }

    @Test
    public void setPasswordToNewPasswordShouldChangeThePasswordToNewPassword() {
        login.setPassword("NewPassword");
        assertEquals("NewPassword", login.getPassword());
    }

    @Test
    public void checkPasswordShouldGiveTrue() {
        String password = "password";
        login.setPassword(password);
        assertEquals(true, login.checkPassword(password));
    }

}
