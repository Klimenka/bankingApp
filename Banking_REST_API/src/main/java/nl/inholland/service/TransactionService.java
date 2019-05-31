package nl.inholland.service;

import nl.inholland.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService
{
    private List<Transaction> transactionsList = new ArrayList<>();

    public void addTransaction(Transaction transaction)
    {
        this.transactionsList.add(transaction);
    }

    public List<Transaction> getTransactions()
    {
        return transactionsList;
    }
}
