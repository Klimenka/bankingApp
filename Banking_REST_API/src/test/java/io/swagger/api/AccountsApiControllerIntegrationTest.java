package io.swagger.api;

import io.swagger.model.Account;
import io.swagger.model.Error;
import org.threeten.bp.LocalDate;

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
public class AccountsApiControllerIntegrationTest {

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
        Account body = new Account();
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
