package nl.inholland.controller;

import nl.inholland.model.Transaction;
import nl.inholland.repository.AccountRepository;
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
@RestController
public class TransactionsApiController implements TransactionsApi {
    private TransactionService transactionService = new TransactionService();
    private String accept = "";
    private AccountRepository accountRepository;

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request, TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    // This is used to create a transaction. It does this by sending a request body (which is a Transaction) to the service.
    // This endpoint must first check if the user has reached the transaction limit or day limit. If not, the request body will proceed.
    // If the service finds out if the transaction is possible, the transaction will be added to the database, and the status will become
    // 'SUCCESSFULL'. However, if something wasn't correct during the transaction process, the body will still be added to the database, but the
    // transaction will affect the accounts and the status will be 'FAILED'.
    public ResponseEntity<Object> createTransaction(@ApiParam(value = "") @Valid @RequestBody Transaction body)
    {
        if (transactionService.checkTransactionLimit(body.getAmount()))
            if (transactionService.checkDayLimit(body.getAccountFrom())) {
                if (transactionService.checkTransactionPossibility(body)) {
                    body.setTransactionStatus(Transaction.TransactionStatusEnum.SUCCESSFUL);
                    return new ResponseEntity<>(transactionService.addTransaction(body), HttpStatus.CREATED);
                } else {
                    body.setTransactionStatus(Transaction.TransactionStatusEnum.FAILED);
                    return new ResponseEntity<>(transactionService.addTransaction(body), HttpStatus.BAD_REQUEST);
                }
            }
            else {
                body.setTransactionStatus(Transaction.TransactionStatusEnum.FAILED);
                return new ResponseEntity<>(transactionService.addTransaction(body), HttpStatus.BAD_REQUEST);
            }
        else {
            body.setTransactionStatus(Transaction.TransactionStatusEnum.FAILED);
            return new ResponseEntity<>(transactionService.addTransaction(body), HttpStatus.BAD_REQUEST);
        }
    }

    // This endpoint is used to retrieve all transaction from the user. It does this by usign an account number which will be used to
    // to check inside the transaction repository if this account has made a transaction before. Additionally, the user can also decide at
    // which time period they will like to see their transactions. By default, the user will see their transaction history from one month ago
    // till the instance they've selected this function
    public ResponseEntity<List<Transaction>> getTransactionHistory(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(name = "accountNumber", required = true) String accountNumber, @ApiParam(value = "") @Valid @RequestParam(value = "dateFrom", required = false) LocalDate dateFrom, @ApiParam(value = "") @Valid @RequestParam(value = "dateTo", required = false) LocalDate dateTo)
    {
        if (dateTo == null && dateFrom == null)
        {
            dateTo = LocalDate.now();
            dateFrom = dateTo.minusMonths(1);
        }

        return new ResponseEntity<List<Transaction>>(transactionService.getAllTransactions(accountNumber, dateFrom, dateTo), HttpStatus.OK);
    }

    // This endpoint is used to retrieve one specific transaction. It is commonly be used to see more details from a specific transaction. This can be done
    // by using the transaction Id from the transaction history.
    public ResponseEntity<Transaction> getTransaction(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(name = "transactionId", required = true) long transactionId)
    {
        return new ResponseEntity<Transaction>(transactionService.getTransaction(transactionId), HttpStatus.OK);
    }
}
