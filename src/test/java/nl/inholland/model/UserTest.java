package nl.inholland.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

//This class is not used by Nadia.
public class UserTest {

    private Employee employee;
    private Customer customer;

    @Before
    public void setup() {
        employee = new Employee("KL Poll", "Kile Poll",
                User.SexEnum.fromValue("male"), "21.12.1972", new Address("Example",
                111, "1111OP", "Nen", "The Netherlands"),
                new Address("Example",111, "1111OP", "Nen",
                        "The Netherlands"), "+31611132156", "example@gmail.com",
                User.CommercialMessagesEnum.fromValue("by bankmail"), User.PreferredLanguageEnum.fromValue("Dutch"),
                "Employee", "Accountant"
        );
        customer = new Customer("KL Poll", "Kile Poll",
                User.SexEnum.fromValue("male"), "21.12.1972", new Address("Example",
                111, "1111OP", "Nen", "The Netherlands"),
                new Address("Example",111, "1111OP", "Nen",
                        "The Netherlands"), "+31611132156", "example@gmail.com",
                User.CommercialMessagesEnum.fromValue("by bankmail"), User.PreferredLanguageEnum.fromValue("Dutch"),
                "Customer");
    }

    @Test
    public void createEmployeeShouldNotBeNull() {
        assertNotNull(employee);
    }

    @Test
    public void createCustomerShouldNotBeNull() {
        assertNotNull(customer);
    }

    @Test
    public void getCustomerIdShouldNotBeNull() {
        assertNotNull(customer.getId());
    }

    @Test
    public void getEmployeeIdShouldNotBeNull() {
        assertNotNull(employee.getId());
    }

    @Test
    public void getOfficialNameOfEmployeeShouldBeCorrect() {
        assertEquals("KL Poll", employee.getOfficialName());
    }

    @Test
    public void getOfficialNameOfCustomerShouldBeCorrect() {
        assertEquals("KL Poll", customer.getOfficialName());
    }

    @Test
    public void setOfficialName() {
    }

    @Test
    public void getPreferredName() {
    }

    @Test
    public void setPreferredName() {
    }

    @Test
    public void getSex() {
    }

    @Test
    public void setSex() {
    }

    @Test
    public void getDateOfBirth() {
    }

    @Test
    public void setDateOfBirth() {
    }

    @Test
    public void getPrimaryAddress() {
    }

    @Test
    public void setPrimaryAddress() {
    }

    @Test
    public void getPostAddress() {
    }

    @Test
    public void setPostAddress() {
    }

    @Test
    public void getMobileNumber() {
    }

    @Test
    public void setMobileNumber() {
    }

    @Test
    public void getEmailAddress() {
    }

    @Test
    public void setEmailAddress() {
    }

    @Test
    public void getCommercialMessages() {
    }

    @Test
    public void setCommercialMessages() {
    }

    @Test
    public void getPreferredLanguage() {
    }

    @Test
    public void setPreferredLanguage() {
    }

    @Test
    public void getUserType() {
    }

    @Test
    public void setUserType() {
    }

    @Test
    public void toString1() {
    }
}