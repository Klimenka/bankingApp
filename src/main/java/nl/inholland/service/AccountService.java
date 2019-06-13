package nl.inholland.service;

import nl.inholland.model.*;
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

    /**
     * get all bank active accounts that belong to a specific user. if accountType
     * is specified then only that type will be returned.
     */
    public List<Account> getBankAccount(long userId, String accountType) {

        if (accountType != null) {
            return accountRepository.getAccountByUserIdEqualsAndAccountTypeEqualsAndAccountStatusEquals
                    (userId, Account.AccountTypeEnum.fromValue(accountType), Account.AccountStatusEnum.OPENED);
        } else {
            return accountRepository.getAccountByUserIdEqualsAndAccountStatusEquals
                    (userId, Account.AccountStatusEnum.OPENED);
        }
    }

    /**
     * Get all bank accounts except the bank own account that cannot be seen
     * except by the Owner. Accounts can be filtered based on type
     * and date of opening and for each case there is a separate method.
     */
    public List<Account> getBankAccounts(LocalDate dateOfOpening, String accountType) {

        if (dateOfOpening != null && accountType == null) {
            return getBankAccountsByDate(dateOfOpening);
        } else if (dateOfOpening == null && accountType != null) {
            return getBankAccountsByType(accountType);
        } else if (dateOfOpening != null && accountType != null) {
            return getBankAccountsByTypeAndByDate(dateOfOpening, accountType);
        } else {
            if (getLoggedInUserRole().equals("Owner")) {
                return accountRepository.findAll();
            } else {
                return accountRepository.findAll()
                        .stream()
                        .filter(account -> account.getAccountNumber() > 1)
                        .collect(Collectors.toList());
            }
        }
    }

    /**
     * retrieve the user role from the session.
     */
    private String getLoggedInUserRole() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        CustomUserDetails login = (CustomUserDetails) principal;
        Set<Role> role = login.getUser().getRoles();
        String userRole = role.stream().map(Role::getRole).findAny().get();
        return userRole;
    }

    private List<Account> getBankAccountsByDate(LocalDate dateOfOpening) {

        if (getLoggedInUserRole().equals("Owner")) {
            return accountRepository.getAccountByDateOfOpeningEquals(dateOfOpening);
        } else {
            return accountRepository.getAccountByDateOfOpeningEqualsAndAccountNumberIsNot(
                    dateOfOpening, 1);
        }
    }

    private List<Account> getBankAccountsByType(String accountType) {

        if (getLoggedInUserRole().equals("Owner")) {
            return accountRepository.getAccountByAccountTypeEquals
                    (Account.AccountTypeEnum.fromValue(accountType));
        } else {
            return accountRepository.getAccountByAccountTypeEqualsAndAccountNumberIsNot
                    (Account.AccountTypeEnum.fromValue(accountType), 1);
        }
    }

    private List<Account> getBankAccountsByTypeAndByDate(LocalDate dateOfOpening,
                                                         String accountType) {

        if (getLoggedInUserRole().equals("Owner")) {
            return accountRepository.getAccountByDateOfOpeningEqualsAndAccountTypeEquals
                    (dateOfOpening, Account.AccountTypeEnum.fromValue(accountType));
        } else {
            return accountRepository.getAccountByDateOfOpeningEqualsAndAccountTypeEqualsAndAccountNumberIsNot
                    (dateOfOpening, Account.AccountTypeEnum.fromValue(accountType), 1);
        }

    }

    /**
     * only for customers accounts can be made, that is why there is
     * a method to check the user role.
     */
    public Account createBankAccount(Account account) {

        User user = userRepository
                .findById(account.getUserIdentification())
                .orElseThrow(() -> new IllegalArgumentException("Incorrect user Id"));

        if (!getUserRole(user).equals("Customer")) throw new IllegalArgumentException
                ("Bank account can only be created for customers");

        account.setUser(user);
        Account bankAccount = accountRepository
                .save(account);

        bankAccount.buildIBAN(bankAccount.getAccountNumber());
        accountRepository.save(bankAccount);

        return bankAccount;
    }

    private String getUserRole(User user) {
        Set<Role> role = user.getRoles();
        String userRole = role.stream().map(Role::getRole).findAny().get();

        return userRole;
    }

    public void closeBankAccount(long accountNumber) {
        if (accountNumber == 1) throw new IllegalArgumentException
                ("Closing the account of the bank itself is not allowed");

        Account account = accountRepository
                .findById(accountNumber)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect bank account number"));
        account.setAccountStatus(Account.AccountStatusEnum.CLOSED);

        accountRepository.save(account);
    }

}
