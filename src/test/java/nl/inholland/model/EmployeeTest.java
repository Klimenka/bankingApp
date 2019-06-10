package nl.inholland.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class EmployeeTest {

    private Employee employee;

    @Before
    public void setup() {
        employee = new Employee("KL Poll", "Kile Poll",
                User.SexEnum.fromValue("male"), "21.12.1972", new Address("Example",
                111, "1111OP", "Nen", "The Netherlands"),
                new Address("Example", 111, "1111OP", "Nen",
                        "The Netherlands"), "+31624211628", "example@gmail.com",
                User.CommercialMessagesEnum.fromValue("by bankmail"), User.PreferredLanguageEnum.fromValue("Dutch"),
                "Employee", "Accountant"
        );
    }

    @Test
    public void createEmployeeShouldNotBeNull() {

        assertNotNull(employee);
    }

    @Test
    public void getOfficialNameOfEmployeeShouldBeCorrect() {

        assertEquals("KL Poll", employee.getOfficialName());
    }

    @Test
    public void setOfficialNameToStringValueShouldReturnThisValue() {
        employee.setOfficialName("NM Klimenko");
        assertEquals("NM Klimenko", employee.getOfficialName());
    }

    @Test
    public void getPreferredNameOfEmployeeShouldBeCorrect() {

        assertEquals("Kile Poll", employee.getPreferredName());
    }

    @Test
    public void setPreferredNameToStringValueShouldReturnThisValue() {
        employee.setPreferredName("Nadia Klimenko");
        assertEquals("Nadia Klimenko", employee.getPreferredName());
    }

    @Test
    public void getSexShouldReturnCorrectValue() {

        assertEquals(User.SexEnum.MALE, employee.getSex());
    }

    @Test
    public void setSexToFemaleShouldReturnCorrectValue() {
        employee.setSex(User.SexEnum.fromValue("female"));
        assertEquals(User.SexEnum.FEMALE, employee.getSex());
    }

    @Test
    public void setSexToAnimalShouldBeNull() {
        employee.setSex(User.SexEnum.fromValue("animal"));
        assertEquals(null, employee.getSex());
    }

    @Test
    public void getDateOfBirthShouldReturnLocalDateFormatWithCorrectValue() {
        assertEquals(LocalDate.parse("1972-12-21"), employee.getDateOfBirth());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setDateOfBirthToDateInTheFutureShouldThrowException() {

        employee.setDateOfBirth("23.08.2019");
    }

    @Test
    public void setDateOfBirthShouldReturnCorrectValue() {
        employee.setDateOfBirth("25.02.1988");
        assertEquals(LocalDate.parse("1988-02-25"), employee.getDateOfBirth());
    }

    @Test
    public void getPrimaryAddressShouldNotBeNull() {

        assertNotNull(employee.getPrimaryAddress());
    }

    @Test
    public void getPostAddressShouldNotBeNull() {

        assertNotNull(employee.getPostAddress());
    }

    @Test
    public void getMobileNumberShouldReturnCorrectValue() {
        assertEquals("+31624211628", employee.getMobileNumber());
    }

    @Test
    public void setMobileNumberShouldReturnCorrectValue() {
        employee.setMobileNumber("+31644411628");
        assertEquals("+31644411628", employee.getMobileNumber());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setMobileNumberConsistedOneDigitShouldThrowException() {
        employee.setMobileNumber("1");
    }

    @Test
    public void getEmailAddressShouldReturnCorrectValue() {
        assertEquals("example@gmail.com", employee.getEmailAddress());
    }

    @Test
    public void setEmailAddressShouldReturnCorrectValue() {
        employee.setEmailAddress("test@gmail.com");
        assertEquals("test@gmail.com", employee.getEmailAddress());
    }

    @Test
    public void getCommercialMessagesShouldReturnCorrectValue() {

        assertEquals(User.CommercialMessagesEnum.BANKMAIL, employee.getCommercialMessages());
    }

    @Test
    public void setCommercialMessagesToFemaleShouldReturnCorrectValue() {
        employee.setCommercialMessages(User.CommercialMessagesEnum.fromValue("by post"));
        assertEquals(User.CommercialMessagesEnum.POST, employee.getCommercialMessages());
    }

    @Test
    public void setCommercialMessagesToPigeonsShouldBeNull() {
        employee.setCommercialMessages(User.CommercialMessagesEnum.fromValue("by pigeons"));
        assertEquals(null, employee.getCommercialMessages());
    }

    @Test
    public void getPreferredLanguageShouldReturnCorrectValue() {

        assertEquals(User.CommercialMessagesEnum.BANKMAIL, employee.getCommercialMessages());
    }

    @Test
    public void setPreferredLanguageToEnglishShouldReturnCorrectValue() {
        employee.setPreferredLanguage(User.PreferredLanguageEnum.fromValue("English"));
        assertEquals(User.PreferredLanguageEnum.ENGLISH, employee.getPreferredLanguage());
    }

    @Test
    public void setPreferredLanguageToRussianShouldBeNull() {
        employee.setPreferredLanguage(User.PreferredLanguageEnum.fromValue("Russian"));
        assertEquals(null, employee.getPreferredLanguage());
    }

    @Test
    public void getRolesShouldReturnCorrectValue() {
        assertEquals("[Role{role='Employee'}]", employee.getRoles().toString());
    }


}
