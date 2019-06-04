package nl.inholland.service;

import nl.inholland.model.Account;
import nl.inholland.repository.AccountRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;


@Service
public class AccountService {

    private AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Iterable<Account> getBankAccount(long userId, String accountType) {

        if (accountType != null) {
            return accountRepository.getAccountByUserIdEqualsAndAccountTypeEquals
                    (userId, Account.AccountTypeEnum.fromValue(accountType));
        } else {
            return accountRepository.getAccountByUserIdEquals(userId);
        }
    }

    public Iterable<Account> getBankAccounts(LocalDate dateOfOpening, String accountType) {

        if (dateOfOpening != null && accountType == null) {
            return accountRepository.getAccountByDateOfOpeningGreaterThanEqualOrderByDateOfOpeningDesc
                    (dateOfOpening);
        } else if (dateOfOpening == null && accountType != null) {
            return accountRepository.getAccountByAccountTypeEquals(Account.AccountTypeEnum.fromValue
                    (accountType));
        } else if (dateOfOpening != null && accountType != null) {
            return accountRepository.getAccountByDateOfOpeningGreaterThanEqualAndAccountTypeEquals
                    (dateOfOpening, Account.AccountTypeEnum.fromValue(accountType));
        } else {
            return accountRepository.findAll();
        }
    }

    public Account createBankAccount(Account account) {
        Account bankAccount = accountRepository
                .save(account);

        bankAccount.buildIBAN(bankAccount.getAccountNumber());

        accountRepository.save(accountRepository
                .findById(bankAccount.getAccountNumber())
                .map(account1 -> bankAccount)
                .orElseThrow(IllegalArgumentException::new));
        return bankAccount;
    }

    public void closeBankAccount(long accountNumber) {
        Account account = accountRepository
                .findById(accountNumber)
                .orElseThrow(IllegalArgumentException::new);
        account.setAccountStatus(Account.AccountStatusEnum.CLOSED);

        accountRepository.save(accountRepository
                .findById(accountNumber)
                .map(account1 -> account)
                .orElseThrow(IllegalArgumentException::new));
    }

}
