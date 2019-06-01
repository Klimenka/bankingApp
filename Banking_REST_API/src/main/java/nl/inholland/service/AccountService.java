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
            return accountRepository.getAccountByUserIdEqualsAndAccountTypeEquals(userId, Account.AccountTypeEnum.fromValue(accountType));
        } else {
            return accountRepository.getAccountByUserIdEquals(userId);
        }
    }

    public Iterable<Account> getBankAccounts(LocalDate dateOfOpening, String accountType) {

        if (dateOfOpening != null && accountType == null) {
            return accountRepository.getAccountByDateOfOpeningGreaterThanEqualOrderByDateOfOpeningDesc(dateOfOpening);
        } else if (dateOfOpening == null && accountType != null) {
            return accountRepository.getAccountByAccountTypeEquals(Account.AccountTypeEnum.fromValue(accountType));
        } else if (dateOfOpening != null && accountType != null) {
            return accountRepository.getAccountByDateOfOpeningGreaterThanEqualAndAccountTypeEquals(dateOfOpening, Account.AccountTypeEnum.fromValue(accountType));
        } else {
            return accountRepository.findAll();
        }
    }

    public Account createBankAccount(Account account) {
        Account bankAccount = accountRepository.save(account);
        //bankAccount.buildIBAN(/*bankAccount.getAccountNumber()*/);
        //public void buildIBAN(/*long accountNumber*/) {
          /*  DecimalFormat df = new DecimalFormat("0000000000");
            String accountNumberFormatted = df.format(bankAccount.getAccountNumber());
            String IBAN = new Iban.Builder()
                    .countryCode(CountryCode.NL)
                    .bankCode("INHO")
                    .accountNumber(accountNumberFormatted)
                    .build().toString();

            //IBAN = iban.toString();
            //return iban.toString();
        //}
        bankAccount.setIBAN(IBAN);*/
        return bankAccount;
    }

    public void closeBankAccount(String accountNumber) {
        Account account = accountRepository.findById(accountNumber).orElseThrow(IllegalArgumentException::new);
        account.setAccountStatus(Account.AccountStatusEnum.CLOSED);

        accountRepository.save(accountRepository.findById(accountNumber)
                .map(account1 -> account).orElseThrow(IllegalArgumentException::new));
    }

}
