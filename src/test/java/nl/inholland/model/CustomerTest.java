package nl.inholland.model;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CustomerTest {
    private Customer customer;

    @Before
    public void setup() {
        customer = new Customer("KL Poll", "Kile Poll",
                User.SexEnum.fromValue("male"), "21.12.1972", new Address("Example",
                111, "1111OP", "Nen", "The Netherlands"),
                new Address("Example", 111, "1111OP", "Nen",
                        "The Netherlands"), "+3111132156", "example@gmail.com",
                User.CommercialMessagesEnum.fromValue("by bankmail"), User.PreferredLanguageEnum.fromValue("Dutch"),
                "Customer");
    }

    @Test
    public void createCustomerShouldNotBeNull() {

        assertNotNull(customer);
    }

    @Test
    public void getOfficialNameOfCustomerShouldBeCorrect() {

        assertEquals("KL Poll", customer.getOfficialName());
    }

    @Test
    public void setOfficialNameToStringValueShouldReturnThisValue() {
        customer.setOfficialName("Gerwin van Dijken");
        assertEquals("Gerwin van Dijken", customer.getOfficialName());
    }

    @Test
    public void getPreferredNameOfCustomerShouldBeCorrect() {

        assertEquals("Kile Poll", customer.getPreferredName());
    }

    @Test
    public void setPreferredNameToStringValueShouldReturnThisValue() {
        customer.setPreferredName("Nadia Klimenko");
        assertEquals("Nadia Klimenko", customer.getPreferredName());
    }

    @Test
    public void getSexShouldReturnCorrectValue() {
        assertEquals(User.SexEnum.MALE, customer.getSex());
    }

    @Test
    public void setSexToFemaleShouldReturnCorrectValue() {
        customer.setSex(User.SexEnum.fromValue("female"));
        assertEquals(User.SexEnum.FEMALE, customer.getSex());
    }

    @Test
    public void setSexToAnimalShouldBeNull() {
        customer.setSex(User.SexEnum.fromValue("animal"));
        assertEquals(null, customer.getSex());
    }

    @Test
    public void getDateOfBirthShouldReturnLocalDateFormatWithCorrectValue() {
        assertEquals(LocalDate.parse("1972-12-21"), customer.getDateOfBirth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDateOfBirthToDateInTheFutureShouldThrowException() {
        customer.setDateOfBirth("23.08.2019");
    }

    @Test
    public void setDateOfBirthShouldReturnCorrectValue() {
        customer.setDateOfBirth("25.02.1988");
        assertEquals(LocalDate.parse("1988-02-25"), customer.getDateOfBirth());
    }

    @Test
    public void getPrimaryAddressShouldNotBeNull() {
        assertNotNull(customer.getPrimaryAddress());
    }

    @Test
    public void getPostAddressShouldNotBeNull() {
        assertNotNull(customer.getPostAddress());
    }

    @Test
    public void getMobileNumber() {
    }

}
