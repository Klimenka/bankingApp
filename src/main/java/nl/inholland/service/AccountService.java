package nl.inholland.service;

import nl.inholland.model.Account;
import nl.inholland.model.Login;
import nl.inholland.model.User;
import nl.inholland.repository.AccountRepository;
import nl.inholland.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class AccountService {

    private AccountRepository accountRepository;
    private UserRepository userRepository;

    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public List<Account> getBankAccount(long userId, String accountType) {

        if (accountType != null) {
            return accountRepository.getAccountByUserIdEqualsAndAccountTypeEqualsAndAccountStatusEquals
                    (userId, Account.AccountTypeEnum.fromValue(accountType), Account.AccountStatusEnum.OPENED);
        } else {
            return accountRepository.getAccountByUserIdEqualsAndAccountStatusEquals
                    (userId, Account.AccountStatusEnum.OPENED);
        }
    }

    public List<Account> getBankAccounts(LocalDate dateOfOpening, String accountType) {

        if (dateOfOpening != null && accountType == null) {
            return accountRepository.getAccountByDateOfOpeningEquals(dateOfOpening);
        } else if (dateOfOpening == null && accountType != null) {
            return accountRepository.getAccountByAccountTypeEquals(Account.AccountTypeEnum.fromValue
                    (accountType));
        } else if (dateOfOpening != null && accountType != null) {
            return accountRepository.getAccountByDateOfOpeningEqualsAndAccountTypeEquals
                    (dateOfOpening, Account.AccountTypeEnum.fromValue(accountType));
        } else {
            return accountRepository.findAll();
        }
    }

    public Account createBankAccount(Account account) {
        //Object princi = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        //Login login = (Login) princi;
        //System.out.println(login.getUser().getId());
        //System.out.println(account.getOne());

        User user = userRepository
                .findById(account.getUserIdentification())
                .orElseThrow(() -> new IllegalArgumentException("The User Id is wrong"));

        account.setUser(user);
        Account bankAccount = accountRepository
                .save(account);

        bankAccount.buildIBAN(bankAccount.getAccountNumber());
        accountRepository.save(bankAccount);

        return bankAccount;
    }

    public void closeBankAccount(long accountNumber) {
        Account account = accountRepository
                .findById(accountNumber)
                .orElseThrow(IllegalArgumentException::new);
        account.setAccountStatus(Account.AccountStatusEnum.CLOSED);

        accountRepository.save(account);
    }

}
