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
                        "The Netherlands"), "+3111132156", "example@gmail.com",
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
    public void getMobileNumber() {

    }
}
