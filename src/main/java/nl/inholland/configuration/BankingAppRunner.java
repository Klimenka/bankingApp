package nl.inholland.configuration;

import nl.inholland.model.*;
import nl.inholland.repository.*;
import nl.inholland.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

/**
 * populate the repositories with some data from external files
 * for testing purposes.
 */
@Component
public class BankingAppRunner implements ApplicationRunner {


    private AccountRepository accountRepository;
    private BankAccountConfig bankAccountConfig;
    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private TransactionRepository transactionRepository;
    private int index = 0;
    private LoginService loginService;
    private LoginRepository loginRepository;
    private int counter = 3;

    public BankingAppRunner(AccountRepository accountRepository, BankAccountConfig bankAccountConfig,
                            UserRepository userRepository, AddressRepository addressRepository,
                            TransactionRepository transactionRepository, LoginRepository loginRepository,
                            LoginService loginService) {
        this.accountRepository = accountRepository;
        this.bankAccountConfig = bankAccountConfig;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.transactionRepository = transactionRepository;
        this.loginRepository = loginRepository;
        this.loginService = loginService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        getBankOwnAccount();
        getAddressesFromFileAndSaveToInMemoryDB();
        getUsersFromFile();
        getBankAccountsFromFile();
        getTransactionsFromFile();
    }

    private void getTransactionsFromFile() throws IOException {
        Path path = Paths.get("src/main/resources/transaction.csv");
        Files.lines(path)
                .forEach(this::saveTransactionInFile);

        transactionRepository.findAll().forEach(System.out::println);
    }

    private void saveTransactionInFile(String line) {
        transactionRepository.save(new Transaction(
                line.split(",")[0],
                line.split(",")[1],
                Float.parseFloat(line.split(",")[2]),
                userRepository.getUserByIdEquals(Long.parseLong(line.split(",")[3])),
                LocalDate.parse(line.split(",")[4]),
                Transaction.TransactionTypeEnum.fromValue(line.split(",")[5]))
        );
    }

    private void getBankOwnAccount() {
        Address address = new Address("Street", 505, "2032SA", "Haarlem", "Netherlands");
        addressRepository.save(address);

        User user = new Employee("Bank", "Bank", User.SexEnum.MALE, "01.01.2019",
                address, address, "0650464266", "bank@bank.com",
                User.CommercialMessagesEnum.BANKMAIL, User.PreferredLanguageEnum.ENGLISH, "Owner", "Manager");
        userRepository.save(user);

        generateLoginAccountsForUsers(user);

        Account bankAccount = new CurrentAccount(0,
                LocalDate.now(), "euro", user);
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
        List<User> users = (List<User>) userRepository.findAll();

        index++;
        if (line.split(",")[4].equals("current")) {
            account = accountRepository.save(new CurrentAccount(
                    Float.parseFloat(line.split(",")[0]),
                    LocalDate.parse(line.split(",")[1]),
                    line.split(",")[2],
                    users.get(index)));
        } else {
            account = accountRepository.save(new SavingAccount(
                    Float.parseFloat(line.split(",")[0]),
                    LocalDate.parse(line.split(",")[1]),
                    line.split(",")[2],
                    users.get(index)));
        }
        account.buildIBAN(account.getAccountNumber());
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
        loginRepository.findAll().forEach(System.out::println);
    }

    private void saveUserToInMemoryDB(String userStringFromFile) {
        User user;

        if (userStringFromFile.split(",")[10].equals("Employee")) {
            user = userRepository.save(new Employee(
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
                    User.CommercialMessagesEnum.fromValue(userStringFromFile.split(",")[8]),
                    User.PreferredLanguageEnum.fromValue(userStringFromFile.split(",")[9]),
                    userStringFromFile.split(",")[10],
                    userStringFromFile.split(",")[11]));
        } else {
            user = userRepository.save(new Customer(
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
                    User.CommercialMessagesEnum.fromValue(userStringFromFile.split(",")[8]),
                    User.PreferredLanguageEnum.fromValue(userStringFromFile.split(",")[9]),
                    userStringFromFile.split(",")[10]));

        }
        generateLoginAccountsForUsers(user);
    }

    private void generateLoginAccountsForUsers(User user) {
        Login login;

        if (counter > 0) {
            login = loginRepository.save(new Login(user.getEmailAddress(), "test777", user));
            counter--;
        } else {
            login = loginRepository.save(new Login(user.getEmailAddress(), user));

        }
        System.out.println(login.getPassword());
        loginService.encodePassword(login);
    }


}
