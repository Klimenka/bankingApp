package nl.inholland.controller;

import nl.inholland.model.Transaction;
import nl.inholland.model.User;
import nl.inholland.repository.UserRepository;
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
    @MockBean private TransactionsApi api;
    private Transaction transaction;
    @MockBean private UserRepository userRepository;

//    @Before
//    public void setUp()
//    {
//        transaction = new Transaction("NL49INHO0000000003", "NL01INHO0000000002", (float)1000,
//                userRepository.getUserByIdEquals((long)1), OffsetDateTime.of(2017, 8, 28, 7, 9, 36, 42, ZoneOffset.UTC),
//                Transaction.TransactionTypeEnum.TRANSACTION);
//    }


//    public void testTransactionLimitShouldGiveException()
//    {
//
//    }

//    @Test
//    public void createTransactionTest() throws Exception {
//        ResponseEntity<Object> responseEntity = api.createTransaction(transaction);
//        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
//    }
//
//    @Test
//    public void getTransactionHistoryTest() throws Exception {
//        OffsetDateTime dateTo = OffsetDateTime.now();
//        OffsetDateTime dateFrom = dateTo.minusMonths(1);
//
//        ResponseEntity<List<Transaction>> responseEntity = api.getTransactionHistory(transaction.getAccountFrom(), dateFrom, dateTo);
//        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//    }

}
