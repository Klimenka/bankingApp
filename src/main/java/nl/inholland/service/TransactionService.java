package nl.inholland.service;

import nl.inholland.model.Account;
import nl.inholland.model.Transaction;
import nl.inholland.repository.AccountRepository;
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

    @Autowired
    private AccountRepository accountRepository;

    private Transaction transaction = new Transaction();
    private Account account;
    private Error error = new Error();

    //adds a transaction to the repository
    public Transaction addTransaction(Transaction body)
    {
        return transactionRepository.save(body);
    }

    //retrieves all the user's transactions between the dates
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

    //checks if the amount of transaction is below the dayLimit
    public Boolean checkDayLimit(String account)
    {
        List<Transaction> transactionList = this.transactionRepository.findAllByAccountFromEqualsAndTimestampEquals(account, OffsetDateTime.now());
        return transactionList.size() < transaction.getDayLimit();
    }

    //checks if the amount is within the transaction limit
    public Boolean checkTransactionLimit(float amount)
    {
        return amount > 1 || amount < transaction.getTransactionLimit();
    }

    //checks if the transaction will not reach the absolute limit of balance in the user's account
    private Boolean checkAbsoluteLimit(Transaction body)
    {
        Account account = accountRepository.getAccountByIban(body.getAccountFrom());
        return (account.getBalance() - body.getAmount()) > transaction.getAbsoluteLimit();
    }

    //determines if the transaction can happen due to the transaction type
    public Boolean checkTransactionPossibility(Transaction body)
    {
        if(body.getTransactionType() == Transaction.TransactionTypeEnum.TRANSACTION || body.getTransactionType() == Transaction.TransactionTypeEnum.WITHDRAW)
            if(checkAbsoluteLimit(body)) {
                updateBalance(body);
                return true;
            }
            else
                return false;

        else if(body.getTransactionType() == Transaction.TransactionTypeEnum.DEPOSIT)
            updateBalance(body);

        return false;
    }

    //this is used to update the balance in a user's account
    private void updateBalance(Transaction body)
    {
        if(body.getTransactionType() == Transaction.TransactionTypeEnum.TRANSACTION)
        {
            account = accountRepository.getAccountByIban(body.getAccountFrom());
            account.setBalance(account.getBalance() - body.getAmount());
            accountRepository.save(account);

            account = accountRepository.getAccountByIban(body.getAccountTo());
            account.setBalance(account.getBalance() + body.getAmount());
            accountRepository.save(account);
        }
        else if(body.getTransactionType() == Transaction.TransactionTypeEnum.WITHDRAW)
        {
            account = accountRepository.getAccountByIban(body.getAccountFrom());
            account.setBalance(account.getBalance() - body.getAmount());
            accountRepository.save(account);
        }
        if(body.getTransactionType() == Transaction.TransactionTypeEnum.DEPOSIT)
        {
            account = accountRepository.getAccountByIban(body.getAccountFrom());
            account.setBalance(account.getBalance() + body.getAmount());
            accountRepository.save(account);
        }

        addTransaction(body);
    }
}
