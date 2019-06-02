package nl.inholland.configuration;

import nl.inholland.model.Account;
import nl.inholland.model.CurrentAccount;
import nl.inholland.model.SavingAccount;
import nl.inholland.repository.AccountRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Component
public class BankingAppRunner implements ApplicationRunner {

    private AccountRepository accountRepository;
    private BankAccountConfig bankAccountConfig;

    public BankingAppRunner(AccountRepository accountRepository, BankAccountConfig bankAccountConfig) {
        this.accountRepository = accountRepository;
        this.bankAccountConfig = bankAccountConfig;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        getBankOwnAccount();
        getBankAccountsFromFile();

    }

    private void getBankOwnAccount() {
        Account bankAccount = new CurrentAccount(0,
                LocalDate.now(), 0, "euro");
        bankAccount.setIBAN(bankAccountConfig.getIBAN());
        accountRepository.save(bankAccount);
    }

    private void getBankAccountsFromFile() throws IOException {
        Path path = Paths.get("src/main/resources/bankAccounts.csv");
        Files.lines(path)
                .forEach(line -> saveBankAccounts(line));

        accountRepository.findAll().forEach(System.out::println);
    }

    private void saveBankAccounts(String line) {
        Account account;
        if (line.split(",")[6].equals("current")) {
            account = accountRepository.save(new CurrentAccount(
                    Float.parseFloat(line.split(",")[1]),
                    LocalDate.parse(line.split(",")[2]),
                    Long.parseLong(line.split(",")[3]),
                    line.split(",")[4]));
        } else {
            account = accountRepository.save(new SavingAccount(
                    Float.parseFloat(line.split(",")[1]),
                    LocalDate.parse(line.split(",")[2]),
                    Long.parseLong(line.split(",")[3]),
                    line.split(",")[4]));
        }
        account.buildIBAN();
        accountRepository.save(accountRepository.findById(account.getAccountNumber())
                .map(account1 -> account).orElseThrow(IllegalArgumentException::new));
    }
}
