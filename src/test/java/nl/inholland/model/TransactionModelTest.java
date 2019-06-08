package nl.inholland.model;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

public class TransactionModelTest
{
    private Transaction transaction;

    @Before
    public void setUp()
    {
        this.transaction = new Transaction();
    }

    @Test
    public void createTransactionShouldNotBeNull()
    {
        assertNotNull(transaction);
    }

    @Test(expected = IllegalArgumentException.class)
    public void amountShouldNotBeANegativeNumber()
    {
        transaction.setAmount((float) -1);
    }


}
