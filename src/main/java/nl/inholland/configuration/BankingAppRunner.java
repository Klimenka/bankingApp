package nl.inholland.configuration;

import nl.inholland.model.*;
import nl.inholland.repository.AccountRepository;
import nl.inholland.repository.AddressRepository;
import nl.inholland.repository.TransactionRepository;
import nl.inholland.repository.UserRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.threeten.bp.OffsetDateTime;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

@Component
public class BankingAppRunner implements ApplicationRunner {

    private AccountRepository accountRepository;
    private BankAccountConfig bankAccountConfig;
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private TransactionRepository transactionRepository;

    public BankingAppRunner(AccountRepository accountRepository, BankAccountConfig bankAccountConfig,
                            UserRepository userRepository, AddressRepository addressRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.bankAccountConfig = bankAccountConfig;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        getBankOwnAccount();
        getBankAccountsFromFile();
        getAddressesFromFileAndSaveToInMemoryDB();
        getUsersFromFile();
        getTransactionsFromFile();
    }

    private void getTransactionsFromFile() throws IOException
    {
        Path path = Paths.get("src/main/resources/transaction.csv");
        Files.lines(path)
                .forEach(line -> saveTransactionInFile(line));

        transactionRepository.findAll().forEach(System.out::println);
    }

    private void saveTransactionInFile(String line)
    {
        if(line.split(",")[3].equals("Customer"))
        {
            transactionRepository.save(new Transaction(
                            line.split(",")[0],
                            line.split(",")[1],
                            Float.parseFloat(line.split(",")[2]),
                            line.split(",")[3],
                            OffsetDateTime.parse(line.split(",")[4]),
                    Transaction.TransactionTypeEnum.fromValue(line.split(",")[5])
                    )
            );
        }
        else if(line.split(",")[3].equals("Employee"))
        {
            transactionRepository.save(new Transaction(
                            line.split(",")[0],
                            line.split(",")[1],
                            Float.parseFloat(line.split(",")[2]),
                            line.split(",")[3],
                            OffsetDateTime.parse(line.split(",")[4]),
                    Transaction.TransactionTypeEnum.fromValue(line.split(",")[5])
                    )
            );
        }
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

    private void getAddressesFromFileAndSaveToInMemoryDB() throws IOException {
        Files.lines(Paths.get("src/main/resources/addresses.csv"))
                .forEach(line -> addressRepository.save(
                        new Address(line.split(",")[0],
                                Integer.parseInt(line.split(",")[1]),
                                line.split(",")[2],
                                line.split(",")[3],
                                line.split(",")[4]
                        )));

    }

    private void getUsersFromFile() throws IOException {
        Path path = Paths.get("src/main/resources/users.csv");
        Files.lines(path)

                .forEach(
                        line -> saveUserToInMemoryDB(line)
                );
        userRepository.findAll().forEach(System.out::println);
    }

    private void saveUserToInMemoryDB(String userStringFromFile) {
        if (userStringFromFile.split(",")[10].equals("Employee")) {
            userRepository.save(new Employee(
                    userStringFromFile.split(",")[0],
                    userStringFromFile.split(",")[1],
                    User.SexEnum.fromValue(userStringFromFile.split(",")[2]),
                    userStringFromFile.split(",")[3],
                    addressRepository.getAddressByPostCodeAndHouseNumber(userStringFromFile.split(",")[4].split(" ")[0],
                            Integer.parseInt(userStringFromFile.split(",")[4].split(" ")[1])),
                    addressRepository.getAddressByPostCodeAndHouseNumber(userStringFromFile.split(",")[5].split(" ")[0],
                            Integer.parseInt(userStringFromFile.split(",")[5].split(" ")[1])),
                    userStringFromFile.split(",")[6],
                    userStringFromFile.split(",")[7],
                    User.CommrcialMessagesEnum.fromValue(userStringFromFile.split(",")[8]),
                    User.PreferedLanguageEnum.fromValue(userStringFromFile.split(",")[9]),
                    User.UserTypeEnum.fromValue(userStringFromFile.split(",")[10]),
                    userStringFromFile.split(",")[11]));
        } else {
            userRepository.save(new Customer(
                    userStringFromFile.split(",")[0],
                    userStringFromFile.split(",")[1],
                    User.SexEnum.fromValue(userStringFromFile.split(",")[2]),
                    userStringFromFile.split(",")[3],
                    addressRepository.getAddressByPostCodeAndHouseNumber(userStringFromFile.split(",")[4].split(" ")[0],
                            Integer.parseInt(userStringFromFile.split(",")[4].split(" ")[1])),
                    addressRepository.getAddressByPostCodeAndHouseNumber(userStringFromFile.split(",")[5].split(" ")[0],
                            Integer.parseInt(userStringFromFile.split(",")[5].split(" ")[1])),
                    userStringFromFile.split(",")[6],
                    userStringFromFile.split(",")[7],
                    User.CommrcialMessagesEnum.fromValue(userStringFromFile.split(",")[8]),
                    User.PreferedLanguageEnum.fromValue(userStringFromFile.split(",")[9]),
                    User.UserTypeEnum.fromValue(userStringFromFile.split(",")[10])));
        }

    }

}
