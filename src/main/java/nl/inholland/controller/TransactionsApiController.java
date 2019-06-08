package nl.inholland.controller;

import nl.inholland.model.Transaction;
import nl.inholland.model.Error;
import nl.inholland.service.TransactionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-02T11:27:08.122Z[GMT]")
@Controller
public class TransactionsApiController implements TransactionsApi {
    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private TransactionService transactionService = new TransactionService();
    private String accept = "";
    private Error error = new Error();

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request, TransactionService transactionService) {
        this.transactionService = transactionService;
        this.objectMapper = objectMapper;
        this.request = request;
    }

    //creates a transaction
    public ResponseEntity<Object> createTransaction(@ApiParam(value = "") @Valid @RequestBody Transaction body) {
        accept = request.getHeader("Accept");

        if (transactionService.checkTransactionLimit(body.getAmount()))
            if (transactionService.checkDayLimit(body.getAccountFrom()))
                if (transactionService.checkTransactionPossibility(body)) {
                    transactionService.addTransaction(body);
                    return new ResponseEntity<>("operation was a success", HttpStatus.CREATED);
                } else {
                    error.setMessage("Unable to perform the transaction");
                    return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
                }
            else {
                error.setMessage("You have exceeded your daily limit");
                return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
            }
        else
        {
            error.setMessage("Please choose a number between 1 and 10000");
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    //retrieves transactions
    public ResponseEntity<List<Transaction>> getTransactionHistory(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(name = "accountNumber", required = true) String accountNumber, @ApiParam(value = "") @Valid @RequestParam(value = "dateFrom", required = false) LocalDate dateFrom, @ApiParam(value = "") @Valid @RequestParam(value = "dateTo", required = false) LocalDate dateTo)
    {
        if (dateTo == null && dateFrom == null)
        {
            dateTo = LocalDate.now();
            dateFrom = dateTo.minusMonths(1);
        }

        accept = request.getHeader("Accept");
        return new ResponseEntity<List<Transaction>>(transactionService.getAllTransactions(accountNumber, dateFrom, dateTo), HttpStatus.OK);
    }

    //retrieves one transaction
    @RequestMapping("/getTransaction")
    public ResponseEntity<Transaction> getTransaction(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(name = "transactionId", required = true) long transactionId)
    {
        accept = request.getHeader("Accept");
        return new ResponseEntity<Transaction>(transactionService.getTransaction(transactionId), HttpStatus.OK);
    }
}
