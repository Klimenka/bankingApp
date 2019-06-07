package nl.inholland.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SavingAccountTest {

    private Account savingAccount;

    @Before
    public void setUp() {
        savingAccount = new SavingAccount();
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
}