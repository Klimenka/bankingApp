package nl.inholland.service;

import nl.inholland.model.Transaction;
import nl.inholland.repository.TransactionRepository;

import java.time.OffsetDateTime;

public class TransactionService
{
    private TransactionRepository transactionRepository;

    //adds a transaction to the database
    //perhaps add an exception
    public void addTransaction(Transaction transaction)
    {
        transactionRepository.save(transaction);
    }

    //checks if the user filters the transaction with dates
    public Iterable<Transaction> getAllTransactions(String userAccount, OffsetDateTime dateFrom, OffsetDateTime dateTo)
    {
        if(dateFrom == null && dateTo == null)
        {
            return transactionRepository.getAllByAccount(userAccount);
        }
        else
        {
            return transactionRepository.getAll(userAccount, dateFrom, dateTo);
        }
    }

    //retrieves one transaction from the user
    public Transaction getTransaction(long transactionId, String userAccount)
    {
        return this.transactionRepository.findByIdAndAccount(transactionId, userAccount);
    }
}
