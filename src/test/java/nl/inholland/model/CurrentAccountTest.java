package nl.inholland.model;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class CurrentAccountTest {

    private Account currentAccount;

    @Before
    public void setUp() {
        currentAccount = new CurrentAccount();
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
}