package nl.inholland.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class CurrentAccountTest {

    private Account currentAccount;

    @Before
    public void setUp() {
        User user = new Employee("Bank", "Bank", User.SexEnum.MALE,
                "01.01.2019", new Address("Street", 505,
                "2032SA", "Haarlem", "Netherlands"),
                new Address("Street", 505, "2032SA",
                        "Haarlem", "Netherlands"), "0650464266",
                "bank@bank.com", User.CommercialMessagesEnum.BANKMAIL,
                User.PreferredLanguageEnum.ENGLISH, "Owner", "Manager");

        currentAccount = new CurrentAccount(0,
                LocalDate.now(), "euro", user);
    }

    @Test
    public void createCurrentAccountShouldNotBeNull() {
        assertNotNull(currentAccount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void passNegativeAccountNumberShouldThrowException() {
        currentAccount.buildIBAN(-1);
    }

    @Test
    public void passAccountNumberShouldReturnTheCorrectIBAN() {
        currentAccount.buildIBAN(5);
        assertEquals("NL92INHO0000000005", currentAccount.getIBAN());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNegativeBalanceShouldThrowException() {
        currentAccount.setBalance(-1);
    }

    @Test
    public void getAccountTypeShouldReturnTheCorrectType() {
        assertEquals(Account.AccountTypeEnum.CURRENT, currentAccount.getAccountType());
    }

    @Test
    public void setAccountTypeToCurrentShouldReturnTheCorrectType() {
        currentAccount.setAccountType(Account.AccountTypeEnum.fromValue("current"));
        assertEquals(Account.AccountTypeEnum.CURRENT, currentAccount.getAccountType());
    }

    @Test
    public void setAccountTypeToUndefinedTypeShouldReturnNull() {
        currentAccount.setAccountType(Account.AccountTypeEnum.fromValue("expired"));
        assertEquals(null, currentAccount.getAccountType());
    }

    @Test
    public void getAccountStatusShouldReturnTheCorrectStatus() {
        assertEquals(Account.AccountStatusEnum.OPENED, currentAccount.getAccountStatus());
    }

    @Test
    public void setAccountStatusToDefinedStatusShouldReturnTheCorrectStatus() {
        currentAccount.setAccountStatus(Account.AccountStatusEnum.fromValue("opened"));
        assertEquals(Account.AccountStatusEnum.OPENED, currentAccount.getAccountStatus());

        currentAccount.setAccountStatus(Account.AccountStatusEnum.fromValue("closed"));
        assertEquals(Account.AccountStatusEnum.CLOSED, currentAccount.getAccountStatus());
    }

    @Test
    public void setAccountStatusToUndefinedStatusShouldReturnNull() {
        currentAccount.setAccountStatus(Account.AccountStatusEnum.fromValue("deleted"));
        assertEquals(null, currentAccount.getAccountStatus());
    }

}