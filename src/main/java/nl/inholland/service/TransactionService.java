package nl.inholland.service;

import nl.inholland.model.Transaction;
import nl.inholland.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.threeten.bp.OffsetDateTime;

import java.util.List;

@Service
public class TransactionService
{
    @Autowired
    private TransactionRepository transactionRepository;

    //adds a transaction to the database
    public Transaction addTransaction(Transaction transaction)
    {
        //build logic to make transactions affect the balance in a bank account
        return transactionRepository.save(transaction);
    }

    //checks if the user filters the transaction with dates
    public List<Transaction> getAllTransactions(String userAccount, OffsetDateTime dateFrom, OffsetDateTime dateTo)
    {
        return transactionRepository.findAllByAccountFromEqualsAndTimestampGreaterThanEqualAndTimestampLessThanEqual(userAccount, dateFrom, dateTo);
    }

    //retrieves one transaction from the user
    public Transaction getTransaction(long transactionId)
    {
        return this.transactionRepository
                .findById(transactionId)
                .orElseThrow(IllegalArgumentException::new);
    }
}
