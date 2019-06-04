package nl.inholland.controller;

import nl.inholland.model.Transaction;
import nl.inholland.service.TransactionService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.threeten.bp.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.threeten.bp.OffsetDateTime;
import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-02T11:27:08.122Z[GMT]")
@RestController
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

    @RequestMapping(value = "createTransaction", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTransaction(@ApiParam(value = ""  )  @Valid @RequestBody Transaction body)
    {
        //(note) check if a an account exists first in the addresses.csv
        // check if amount is correct!
        transactionService.addTransaction(body);

        accept = request.getHeader("Created");
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping("getTransaction")
    public ResponseEntity<List<Transaction>> getTransactionHistory(@NotNull @ApiParam(value = "", required = true) @Valid @RequestParam(value = "accountNumber", required = true) String accountNumber,@ApiParam(value = "") @Valid @RequestParam(value = "dateFrom", required = false) LocalDate dateFrom,@ApiParam(value = "") @Valid @RequestParam(value = "dateTo", required = false) LocalDate dateTo)
    {
        if(dateFrom != null && dateTo != null)
        {
            OffsetDateTime offsetDateFrom = OffsetDateTime.from(dateFrom);
            OffsetDateTime offsetDateTo = OffsetDateTime.from(dateTo);

            transactionService.getAllTransactions(accountNumber, offsetDateFrom, offsetDateTo);
        }
        else
        {
            transactionService.getAllTransactions(accountNumber, null, null);
        }

        String accept = request.getHeader("Accept");
        return new ResponseEntity<List<Transaction>>(HttpStatus.OK);
    }
}
