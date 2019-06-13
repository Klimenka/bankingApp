package nl.inholland.service;

import nl.inholland.model.Account;
import nl.inholland.model.Transaction;
import nl.inholland.repository.AccountRepository;
import nl.inholland.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;


    private Transaction transaction = new Transaction();
    private Account account;

    // After the process is completed, the transaction will be added to the database. The user will then be able to retrieve the transactions from
    // the database.
    public Transaction addTransaction(Transaction body) {
        return transactionRepository.save(body);
    }

    // Retrieves all the user's transactions from the database by using the dates and the user's account number.
    public List<Transaction> getAllTransactions(String userAccount, LocalDate dateFrom, LocalDate dateTo) {
        return transactionRepository.findAllByAccountFromEqualsAndDateGreaterThanEqualAndDateLessThanEqual(userAccount, dateFrom, dateTo);
    }

    // Retrieves one transaction from the user by using the transaction Id
    public Transaction getTransaction(long transactionId) {
        return this.transactionRepository
                .findById(transactionId)
                .orElseThrow(IllegalArgumentException::new);
    }

    // Checks if the amount of transaction is below the dayLimit
    public Boolean checkDayLimit(String account) {
        List<Transaction> transactionList = this.transactionRepository.findAllByAccountFromEqualsAndDateEquals(account, LocalDate.now());
        return transactionList.size() < transaction.getDayLimit();
    }

    // Checks if the amount is within the transaction limit
    public Boolean checkTransactionLimit(float amount) {
        return amount > 1 || amount < transaction.getTransactionLimit();
    }

    // Checks if the transaction will not reach the absolute limit of balance in the user's account
    private Boolean checkAbsoluteLimit(Transaction body) {
        account = accountRepository.getAccountByIban(body.getAccountFrom());
        return (account.getBalance() - body.getAmount()) > transaction.getAbsoluteLimit();
    }

    // In this function, the service will first check the transaction type. If it's a TRANSACTION or WITHDRAW, it must first check if the balance
    // inside the user's account is below 10 euros. If not, then it can check if the balances can be updated.
    public Boolean checkTransactionPossibility(Transaction body) {
        if (body.getTransactionType() == Transaction.TransactionTypeEnum.TRANSACTION || body.getTransactionType() == Transaction.TransactionTypeEnum.WITHDRAW)
            if (checkAbsoluteLimit(body))
                return updateBalance(body);
            else
                return false;
        else if (body.getTransactionType() == Transaction.TransactionTypeEnum.DEPOSIT)
            return updateBalance(body);

        return false;
    }

    // This function is used to update the balance in a user's account depending on the transaction type.
    private Boolean updateBalance(Transaction body) {
        if (body.getTransactionType() == Transaction.TransactionTypeEnum.TRANSACTION)
            return performTransaction(body);
        else if (body.getTransactionType() == Transaction.TransactionTypeEnum.WITHDRAW)
            return performWithdraw(body);
        if (body.getTransactionType() == Transaction.TransactionTypeEnum.DEPOSIT)
            return performDeposit(body);

        return false;
    }

    // This function is used to subtract the amount from the user's account
    private void subtractAmountFromBalance(String accountNumber, float amount) {
        account = accountRepository.getAccountByIban(accountNumber);
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);
    }

    // This function is used to add the amount to the user's account
    private void addAmountToBalance(String accountNumber, float amount) {
        account = accountRepository.getAccountByIban(accountNumber);
        account.setBalance(account.getBalance() + amount);
        accountRepository.save(account);
    }

    // This function is used to check if an account belongs to the user.
    private Boolean checkIfAccountBelongsToUser(Transaction body, String accountNumber) {
        account = accountRepository.getAccountByIban(accountNumber);
        return body.getUserPerforming().getId().equals(account.getUser().getId());
    }

    // This function is used to retrieve the account type by using the account number.
    private Account.AccountTypeEnum getAccountType(String accountNumber) {
        return accountRepository.getAccountByIban(accountNumber).getAccountType();
    }

    // This function is used to perform a withdraw. It must first check if the account type is 'CURRENT' and if the account belongs to the user.
    private Boolean performWithdraw(Transaction body) {
        if (getAccountType(body.getAccountFrom()) == Account.AccountTypeEnum.CURRENT && checkIfAccountBelongsToUser(body, body.getAccountFrom())) {
            subtractAmountFromBalance(body.getAccountFrom(), body.getAmount());
            return true;
        }

        return false;
    }

    // This function is used to perform a deposit. It must first check if the account type is 'CURRENT' and if the account belongs to the user.
    private Boolean performDeposit(Transaction body) {
        if (getAccountType(body.getAccountFrom()) == Account.AccountTypeEnum.CURRENT && checkIfAccountBelongsToUser(body, body.getAccountFrom())) {
            addAmountToBalance(body.getAccountFrom(), body.getAmount());
            return true;
        }

        return false;
    }

    // This function is used to perform a transaction based on the account type.
    private Boolean performTransaction(Transaction body) {
        if (getAccountType(body.getAccountFrom()) == Account.AccountTypeEnum.CURRENT) {
            return performTransactionIfAccountTypeIsCurrent(body);
        } else if (getAccountType(body.getAccountFrom()) == Account.AccountTypeEnum.SAVING) {
            return performTransactionIfAccountTypeIsSaving(body);
        }

        return false;
    }

    // This transaction function must first check if the first account belongs to the primary user. if it does, then it must check the account type
    // from the other user and if that account belongs to the user. However, since this project does not give us access to all other database, if
    // a user wants to send money to an account from another bank, then the function will only check if the account belongs to the user.
    private Boolean performTransactionIfAccountTypeIsCurrent(Transaction body) {
        if (checkIfAccountBelongsToUser(body, body.getAccountFrom()) && (getAccountType(body.getAccountTo()) == Account.AccountTypeEnum.CURRENT && checkIfAccountBelongsToUser(body, body.getAccountTo()))) {
            subtractAmountFromBalance(body.getAccountFrom(), body.getAmount());
            addAmountToBalance(body.getAccountTo(), body.getAmount());
            return true;
        } else if (checkIfAccountBelongsToUser(body, body.getAccountFrom()) && (getAccountType(body.getAccountTo()) == Account.AccountTypeEnum.SAVING && checkIfAccountBelongsToUser(body, body.getAccountTo()))) {
            subtractAmountFromBalance(body.getAccountFrom(), body.getAmount());
            addAmountToBalance(body.getAccountTo(), body.getAmount());
            return true;
        } else if (checkIfAccountBelongsToUser(body, body.getAccountFrom()) && (getAccountType(body.getAccountTo()) == Account.AccountTypeEnum.CURRENT)) {
            subtractAmountFromBalance(body.getAccountFrom(), body.getAmount());
            addAmountToBalance(body.getAccountTo(), body.getAmount());
            return true;
        }

        return false;
    }

    // This transaction function must first check if the first account belongs to the primary user. If it does, then it must check if the account type
    // of the secondary account and if it belongs to the same user.
    private Boolean performTransactionIfAccountTypeIsSaving(Transaction body) {
        if (checkIfAccountBelongsToUser(body, body.getAccountFrom()) && (getAccountType(body.getAccountTo()) == Account.AccountTypeEnum.SAVING && checkIfAccountBelongsToUser(body, body.getAccountTo()))) {
            subtractAmountFromBalance(body.getAccountFrom(), body.getAmount());
            addAmountToBalance(body.getAccountTo(), body.getAmount());
            return true;
        } else if (checkIfAccountBelongsToUser(body, body.getAccountFrom()) && (getAccountType(body.getAccountTo()) == Account.AccountTypeEnum.CURRENT && checkIfAccountBelongsToUser(body, body.getAccountTo()))) {
            subtractAmountFromBalance(body.getAccountFrom(), body.getAmount());
            addAmountToBalance(body.getAccountTo(), body.getAmount());
            return true;
        }

        return false;
    }
}
