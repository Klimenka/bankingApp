package nl.inholland.service;

import nl.inholland.model.Account;
import nl.inholland.model.Login;
import nl.inholland.model.Role;
import nl.inholland.model.User;
import nl.inholland.repository.AccountRepository;
import nl.inholland.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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
            return getBankAccountsByDate(dateOfOpening);
        } else if (dateOfOpening == null && accountType != null) {
            return getBankAccountsByType(accountType);
        } else if (dateOfOpening != null && accountType != null) {
            return getBankAccountsByTypeAndByDate(dateOfOpening, accountType);
        } else {
            if (getUserRole().equals("Owner")) {
                return accountRepository.findAll();
            } else {
                return accountRepository.findAll()
                        .stream()
                        .filter(account -> account.getAccountNumber() > 1)
                        .collect(Collectors.toList());
            }
        }
    }

    private String getUserRole() {
        Object Principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Login login = (Login) Principal;
        Set<Role> role = login.getUser().getRoles();
        String userRole = role.stream().map(Role::getRole).findAny().get();
        return userRole;
    }

    private List<Account> getBankAccountsByDate(LocalDate dateOfOpening) {

        if (getUserRole().equals("Owner")) {
            return accountRepository.getAccountByDateOfOpeningEquals(dateOfOpening);
        } else {
            return accountRepository.getAccountByDateOfOpeningEqualsAndAccountNumberIsNot(
                    dateOfOpening, 1);
        }
    }

    private List<Account> getBankAccountsByType(String accountType) {

        if (getUserRole().equals("Owner")) {
            return accountRepository.getAccountByAccountTypeEquals
                    (Account.AccountTypeEnum.fromValue(accountType));
        } else {
            return accountRepository.getAccountByAccountTypeEqualsAndAccountNumberIsNot
                    (Account.AccountTypeEnum.fromValue(accountType), 1);
        }
    }

    private List<Account> getBankAccountsByTypeAndByDate(LocalDate dateOfOpening,
                                                         String accountType) {

        if (getUserRole().equals("Owner")) {
            return accountRepository.getAccountByDateOfOpeningEqualsAndAccountTypeEquals
                    (dateOfOpening, Account.AccountTypeEnum.fromValue(accountType));
        } else {
            return accountRepository.getAccountByDateOfOpeningEqualsAndAccountTypeEqualsAndAccountNumberIsNot
                    (dateOfOpening, Account.AccountTypeEnum.fromValue(accountType), 1);
        }

    }

    public Account createBankAccount(Account account) {

        User user = userRepository
                .findById(account.getUserIdentification())
                .orElseThrow(() -> new IllegalArgumentException("Incorrect user Id"));

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
                .orElseThrow(() -> new IllegalArgumentException("Incorrect bank account number"));
        account.setAccountStatus(Account.AccountStatusEnum.CLOSED);

        accountRepository.save(account);
    }

}
