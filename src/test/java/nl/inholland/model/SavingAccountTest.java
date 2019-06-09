package nl.inholland.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class SavingAccountTest {

    private Account savingAccount;

    @Before
    public void setUp() {
        User user = new Employee("Bank", "Bank", User.SexEnum.MALE,
                "01.01.2019", new Address("Street", 505,
                "2032SA", "Haarlem", "Netherlands"),
                new Address("Street", 505, "2032SA",
                        "Haarlem", "Netherlands"), "0650464266",
                "bank@bank.com", User.CommercialMessagesEnum.BANKMAIL,
                User.PreferredLanguageEnum.ENGLISH, "Owner", "Manager");

        savingAccount = new SavingAccount(0,
                LocalDate.now(), "euro", user);
    }

    @Test
    public void createSavingAccountShouldNotBeNull() {
        assertNotNull(savingAccount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void passNegativeAccountNumberShouldThrowException() {
        savingAccount.buildIBAN(-1);
    }

    @Test
    public void passAccountNumberShouldReturnTheCorrectIBAN() {
        savingAccount.buildIBAN(5);
        assertEquals("NL92INHO0000000005", savingAccount.getIBAN());
    }

    @Test(expected = IllegalArgumentException.class)
    public void setNegativeBalanceShouldThrowException() {
        savingAccount.setBalance(-1);
    }

    @Test
    public void getAccountTypeShouldReturnTheCorrectType() {
        assertEquals(Account.AccountTypeEnum.SAVING, savingAccount.getAccountType());
    }

    @Test
    public void setAccountTypeToSavingShouldReturnTheCorrectType() {
        savingAccount.setAccountType(Account.AccountTypeEnum.fromValue("saving"));
        assertEquals(Account.AccountTypeEnum.SAVING, savingAccount.getAccountType());
    }

    @Test
    public void setAccountTypeToUndefinedTypeShouldReturnNull() {
        savingAccount.setAccountType(Account.AccountTypeEnum.fromValue("expired"));
        assertEquals(null, savingAccount.getAccountType());
    }

    @Test
    public void getAccountStatusShouldReturnTheCorrectStatus() {
        assertEquals(Account.AccountStatusEnum.OPENED, savingAccount.getAccountStatus());
    }

    @Test
    public void setAccountStatusToDefinedStatusShouldReturnTheCorrectStatus() {
        savingAccount.setAccountStatus(Account.AccountStatusEnum.fromValue("opened"));
        assertEquals(Account.AccountStatusEnum.OPENED, savingAccount.getAccountStatus());

        savingAccount.setAccountStatus(Account.AccountStatusEnum.fromValue("closed"));
        assertEquals(Account.AccountStatusEnum.CLOSED, savingAccount.getAccountStatus());
    }

    @Test
    public void setAccountStatusToUndefinedStatusShouldReturnNull() {
        savingAccount.setAccountStatus(Account.AccountStatusEnum.fromValue("deleted"));
        assertEquals(null, savingAccount.getAccountStatus());
    }
}