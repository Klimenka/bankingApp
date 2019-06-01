package nl.inholland.controller;

import nl.inholland.controller.TransactionsApi;
import nl.inholland.model.Transaction;
import nl.inholland.service.TransactionService;
import org.threeten.bp.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-05-23T16:34:19.518Z[GMT]")
@Controller
public class TransactionsApiController implements TransactionsApi {

    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private TransactionService transactionService;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request,TransactionService transactionService)
    {
        this.objectMapper = objectMapper;
        this.request = request;
        this.transactionService = transactionService;
    }

    //this is used to create a transaction
    public ResponseEntity<Void> createTransaction(@ApiParam(value = ""  )  @Valid @RequestBody Transaction transaction)
    {
        transactionService.addTransaction(transaction);

        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    //this is used to retrieve all transactions
    public ResponseEntity<List<Transaction>> getTransactionHistory(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "accountNumber", required = true) String accountNumber, @ApiParam(value = "") @Valid @RequestParam(value = "dateFrom", required = false) LocalDate dateFrom, @ApiParam(value = "") @Valid @RequestParam(value = "dateTo", required = false) LocalDate dateTo)
    {
        List<Transaction> transactionList = transactionService.getTransactions();

        String accept = request.getHeader("Accept");
        return new ResponseEntity<List<Transaction>>(HttpStatus.OK);
    }

}
