package nl.inholland.model;

import org.junit.Test;
import static org.junit.Assert.assertNotNull;

public class TransactionModelTest
{
    @Test
    public void createTransactionShouldNotBeNull()
    {
        Transaction transaction = new Transaction();
        assertNotNull(transaction);
    }
}
