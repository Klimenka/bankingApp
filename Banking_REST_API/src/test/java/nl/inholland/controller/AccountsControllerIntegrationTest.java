package nl.inholland.controller;

import nl.inholland.model.Account;
import nl.inholland.model.CurrentAccount;

import java.time.LocalDate;
import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountsControllerIntegrationTest {

    @Autowired
    private AccountsApi api;

    @Test
    public void closeBankAccountTest() throws Exception {
        String accountNumber = "accountNumber_example";
        ResponseEntity<Void> responseEntity = api.closeBankAccount(accountNumber);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void createBankAccountTest() throws Exception {
        Account body = new CurrentAccount();
        ResponseEntity<Account> responseEntity = api.createBankAccount(body);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void getBankAccountTest() throws Exception {
        Long userId = 789L;
        String accountType = "accountType_example";
        ResponseEntity<List<Account>> responseEntity = api.getBankAccount(userId, accountType);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void getBankAccountsTest() throws Exception {
        LocalDate dateOfOpening = LocalDate.now();
        String accountType = "accountType_example";
        ResponseEntity<List<Account>> responseEntity = api.getBankAccounts(dateOfOpening, accountType);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

}