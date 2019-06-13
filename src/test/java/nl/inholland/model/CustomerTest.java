package nl.inholland.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

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
                        "The Netherlands"), "+31624211628", "example@gmail.com",
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
        customer.setOfficialName("NM Klimenko");
        assertEquals("NM Klimenko", customer.getOfficialName());
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
    public void getMobileNumberShouldReturnCorrectValue() {
        assertEquals("+31624211628", customer.getMobileNumber());
    }

    @Test
    public void setMobileNumberShouldReturnCorrectValue() {
        customer.setMobileNumber("+31644411628");
        assertEquals("+31644411628", customer.getMobileNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setMobileNumberConsistedOneDigitShouldThrowException() {
        customer.setMobileNumber("1");
    }

    @Test
    public void getEmailAddressShouldReturnCorrectValue() {
        assertEquals("example@gmail.com", customer.getEmailAddress());
    }

    @Test
    public void setEmailAddressShouldReturnCorrectValue() {
        customer.setEmailAddress("test@gmail.com");
        assertEquals("test@gmail.com", customer.getEmailAddress());
    }

    @Test
    public void getCommercialMessagesShouldReturnCorrectValue() {

        assertEquals(User.CommercialMessagesEnum.BANKMAIL, customer.getCommercialMessages());
    }

    @Test
    public void setCommercialMessagesToPostShouldReturnCorrectValue() {
        customer.setCommercialMessages(User.CommercialMessagesEnum.fromValue("by post"));
        assertEquals(User.CommercialMessagesEnum.POST, customer.getCommercialMessages());
    }

    @Test
    public void setCommercialMessagesToPigeonsShouldBeNull() {
        customer.setCommercialMessages(User.CommercialMessagesEnum.fromValue("by pigeons"));
        assertEquals(null, customer.getCommercialMessages());
    }

    @Test
    public void getPreferredLanguageShouldReturnCorrectValue() {

        assertEquals(User.CommercialMessagesEnum.BANKMAIL, customer.getCommercialMessages());
    }

    @Test
    public void setPreferredLanguageToEnglishShouldReturnCorrectValue() {
        customer.setPreferredLanguage(User.PreferredLanguageEnum.fromValue("English"));
        assertEquals(User.PreferredLanguageEnum.ENGLISH, customer.getPreferredLanguage());
    }

    @Test
    public void setPreferredLanguageToRussianShouldBeNull() {
        customer.setPreferredLanguage(User.PreferredLanguageEnum.fromValue("Russian"));
        assertEquals(null, customer.getPreferredLanguage());
    }

    @Test
    public void getRolesShouldReturnCorrectValue() {
        assertEquals("[Role{role='Customer'}]", customer.getRoles().toString());
    }


}
