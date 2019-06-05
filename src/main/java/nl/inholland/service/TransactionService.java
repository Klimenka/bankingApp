package nl.inholland.service;

import nl.inholland.model.Transaction;
import nl.inholland.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.threeten.bp.OffsetDateTime;

@Service
public class TransactionService
{
    //@Autowired
    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    //adds a transaction to the database
    //perhaps add an exception
    public Transaction addTransaction(Transaction transaction)
    {
        return transactionRepository.save(transaction);
    }

    //checks if the user filters the transaction with dates
    public Iterable<Transaction> getAllTransactions(String userAccount, OffsetDateTime dateFrom, OffsetDateTime dateTo)
    {
        if(dateFrom == null && dateTo == null)
        {
            //return transactionRepository.findAllByAccountFrom(userAccount);
            return transactionRepository.findAll();
        }
        else
        {
            return transactionRepository.findAllByAccountFromAndTimestampAndTimestamp(userAccount, dateFrom, dateTo);
        }
    }

    //retrieves one transaction from the user
    public Iterable<Transaction> getTransaction(long transactionId)
    {
        return this.transactionRepository.findAllByTransactionId(transactionId);
    }
}
