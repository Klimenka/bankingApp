package nl.inholland.model;

import nl.inholland.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

import static org.junit.Assert.*;

public class TransactionModelTest
{
    private Transaction transactionExample;

    @Before
    public void setUp()
    {
        this.transactionExample = new Transaction("NL01INHO0000000002", "NL01INHO0000000003", (float) 60,
                new Customer("KL Poll", "Kile Poll",
                        User.SexEnum.fromValue("male"), "21.12.1972", new Address("Example",
                        111, "1111OP", "Nen", "The Netherlands"),
                        new Address("Example", 111, "1111OP", "Nen",
                                "The Netherlands"), "0650464266", "example@gmail.com",
                        User.CommercialMessagesEnum.fromValue("by bankmail"), User.PreferredLanguageEnum.fromValue("Dutch"),
                        "Customer"), LocalDate.now(), Transaction.TransactionTypeEnum.TRANSACTION);
    }

    @Test
    public void createTransactionShouldNotBeNull()
    {
        assertNotNull(transactionExample);
    }

    @Test
    public void getTransactionIdShouldNotBeNull() {
        assertNotNull(transactionExample.getTransactionId());
    }

    @Test
    public void getUserPerformingShouldNotBeNull() {
        assertNotNull(transactionExample.getUserPerforming());
    }

    @Test
    public void getAccountFromShouldNotBeNull() {
        assertNotNull(transactionExample.getAccountFrom());
    }

    @Test
    public void getAccountToShouldNotBeNull() {
        assertNotNull(transactionExample.getAccountTo());
    }

    @Test
    public void getAccountToShouldBeNull() {
        transactionExample.setAccountTo(null);
        assertNull(transactionExample.getAccountTo());
    }

    @Test(expected = NullPointerException.class)
    public void getAmountShouldNotBeNull() {
        transactionExample.setAmount(null);
        assertNotNull(transactionExample.getAmount());
    }

    @Test
    public void getDateShouldNotBeNull() {
        assertNotNull(LocalDate.now().toString(), transactionExample.getDate());
    }

    @Test
    public void dayLimitShouldBeTwenty() {
        assertEquals(20, transactionExample.getDayLimit());
    }

    @Test
    public void transactionLimitShouldBeTenThousand() {
        assertEquals(10000, transactionExample.getTransactionLimit(), 0.0f);
    }

    @Test
    public void absoluteLimitShouldBeTen() {
        assertEquals(10, transactionExample.getAbsoluteLimit(), 0.0f);
    }

    @Test(expected = IllegalArgumentException.class)
    public void amountShouldNotBeANegativeNumber()
    {
        transactionExample.setAmount((float) -1);
    }

    @Test
    public void getTransactionTypeEnumShouldBeCorrect() {
        assertEquals(Transaction.TransactionTypeEnum.TRANSACTION, transactionExample.getTransactionType());
    }

    @Test
    public void getTransactionStatusShouldBeCorrect() {
        assertEquals(Transaction.TransactionStatusEnum.PENDING, transactionExample.getTransactionStatus());
    }

    @Test
    public void getAccountFromShouldBeCorrect() {
        assertEquals("NL01INHO0000000002", transactionExample.getAccountFrom());
    }

    @Test
    public void getAccountToShouldBeCorrect() {
        assertEquals("NL01INHO0000000003", transactionExample.getAccountTo());
    }
}
