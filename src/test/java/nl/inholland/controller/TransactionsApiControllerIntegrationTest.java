package nl.inholland.controller;

import nl.inholland.model.Transaction;
import nl.inholland.service.TransactionService;
import org.junit.Assert;
import org.junit.Before;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.threeten.bp.LocalDate;

import java.util.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.ZoneOffset;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionsApiControllerIntegrationTest {

    @Autowired private MockMvc mvc;
    @MockBean private TransactionService service;
    private TransactionsApi api;
    private Transaction transaction;

//    public static final long EXPECTED_TRANSACTION_ID = 1;
//    public static final String EXPECTED_ACCOUNT_FROM = "NL01INHO0000000005";
//    public static final String EXPECTED_ACCOUNT_TO = "NL01INHO0000000002";
//    public static final String EXPECTED_USER_PERFORMING = "Customer";
//    public static final OffsetDateTime EXPECTED_TIMESTAMP = OffsetDateTime.of(2017, 8, 28, 7, 9, 36, 42, ZoneOffset.UTC);

//    @Before
//    public void setUp()
//    {
//        transaction = new Transaction(1,"NL01INHO0000000005", "NL01INHO0000000002", "Customer", OffsetDateTime.of(2017, 8, 28, 7, 9, 36, 42, ZoneOffset.UTC));
//    }

//    @Test
//    public void testTransactionDetail() throws Exception
//    {
//        Assert.assertEquals(EXPECTED_TRANSACTION_ID, transaction.getTransactionId());
//        Assert.assertEquals(EXPECTED_ACCOUNT_FROM, transaction.getAccountFrom());
//        Assert.assertEquals(EXPECTED_ACCOUNT_TO, transaction.getAccountTo());
//        Assert.assertEquals(EXPECTED_USER_PERFORMING, transaction.getUserPerforming());
//        Assert.assertEquals(EXPECTED_TIMESTAMP, transaction.getTimestamp());
//    }

//    @Test
//    public void AddTransactionShouldReturnCreated() throws Exception
//    {
//        mvc.perform(post("/transaction")
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .content("{}"))
//                .andExpect(status().isCreated());
//    }

//    @Test
//    public void createTransactionTest() throws Exception {
//        Transaction body = new Transaction();
//        ResponseEntity<Void> responseEntity = api.createTransaction(body);
//        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
//    }
//
//    @Test
//    public void getTransactionHistoryTest() throws Exception {
//        String accountNumber = "accountNumber_example";
//        LocalDate dateFrom = LocalDate.now();
//        LocalDate dateTo = LocalDate.now();
//        ResponseEntity<List<Transaction>> responseEntity = api.getTransactionHistory(accountNumber, dateFrom, dateTo);
//        assertEquals(HttpStatus.NOT_IMPLEMENTED, responseEntity.getStatusCode());
//    }

}
