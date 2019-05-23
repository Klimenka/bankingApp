package io.swagger.api;

import io.swagger.model.Error;
import org.threeten.bp.LocalDate;
import io.swagger.model.Transaction;

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
public class TransactionsApiControllerIntegrationTest {

    @Autowired
    private TransactionsApi api;

    @Test
    public void createTransactionTest() throws Exception {
        Transaction body = new Transaction();
        ResponseEntity<Void> responseEntity = api.createTransaction(body);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

    @Test
    public void getTransactionHistoryTest() throws Exception {
        String accountNumber = "accountNumber_example";
        LocalDate dateFrom = LocalDate.now();
        LocalDate dateTo = LocalDate.now();
        ResponseEntity<List<Transaction>> responseEntity = api.getTransactionHistory(accountNumber, dateFrom, dateTo);
        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
    }

}
