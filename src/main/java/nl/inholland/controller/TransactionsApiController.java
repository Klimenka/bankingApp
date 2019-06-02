package nl.inholland.controller;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-02T11:27:08.122Z[GMT]")
@Controller
public class TransactionsApiController implements TransactionsApi
{
    private static final Logger log = LoggerFactory.getLogger(TransactionsApiController.class);
    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    private TransactionService transactionService = new TransactionService();
    private String accept = "";

    @org.springframework.beans.factory.annotation.Autowired
    public TransactionsApiController(ObjectMapper objectMapper, HttpServletRequest request, TransactionService transactionService)
    {
        this.transactionService = transactionService;
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> createTransaction(@ApiParam(value = ""  )  @Valid @RequestBody Transaction body)
    {
        if(body.getTransactionType() == Transaction.TransactionTypeEnum.TRANSACTION)
            if(body.getAccountTo() != null && body.getAccountFrom() != null)
                transactionService.addTransaction(body);
            else
            {
                accept = request.getHeader("Bad request");
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
        else if(body.getTransactionType() == Transaction.TransactionTypeEnum.WITHDRAW)
            if(body.getAccountTo() == null && body.getAccountFrom() != null)
                transactionService.addTransaction(body);
            else
            {
                accept = request.getHeader("Bad request");
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }
        else if(body.getTransactionType() == Transaction.TransactionTypeEnum.DEPOSIT)
            if(body.getAccountTo() != null && body.getAccountFrom() == null)
                transactionService.addTransaction(body);
            else
            {
                accept = request.getHeader("Bad request");
                return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
            }

        accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.ACCEPTED);
    }

    public ResponseEntity<List<Transaction>> getTransactionHistory(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "accountNumber", required = true) String accountNumber,@ApiParam(value = "") @Valid @RequestParam(value = "dateFrom", required = false) LocalDate dateFrom,@ApiParam(value = "") @Valid @RequestParam(value = "dateTo", required = false) LocalDate dateTo) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<List<Transaction>>(HttpStatus.NOT_IMPLEMENTED);
    }
}
