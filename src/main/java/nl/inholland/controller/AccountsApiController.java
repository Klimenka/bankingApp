package nl.inholland.controller;

import nl.inholland.model.Account;
import nl.inholland.model.Login;
import nl.inholland.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-02T11:27:08.122Z[GMT]")
@RestController
public class AccountsApiController implements AccountsApi {

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;
    private AccountService accountService;

    @Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request,
                                 AccountService accountService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.accountService = accountService;
    }


    public ResponseEntity<Void> closeBankAccount(@ApiParam(value = "the account number",
            required = true) @PathVariable("accountNumber") long accountNumber) {

        String accept = request.getHeader("Accept");
        accountService.closeBankAccount(accountNumber);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Account> createBankAccount(@ApiParam(value = "", required = true)
                                                     @Valid @RequestBody Account body) {

        String accept = request.getHeader("Accept");
        //accountService.createBankAccount(body);
        return new ResponseEntity<Account>(accountService.createBankAccount(body), HttpStatus.CREATED);
    }

    public ResponseEntity<List<Account>> getBankAccount
            (@Min(1L) @ApiParam(value = "The id of the user to return", required = true, allowableValues = "")
             @PathVariable("userId") long userId,
             @ApiParam(value = "", allowableValues = "current, saving")
             @Valid @RequestParam(value = "accountType", required = false) String accountType) {

        String accept = request.getHeader("Accept");
        return new ResponseEntity<List<Account>>
                (accountService.getBankAccount(userId, accountType), HttpStatus.OK);
    }

    public ResponseEntity<List<Account>> getBankAccounts
            (@ApiParam(value = "")
             @RequestParam(value = "dateOfOpening", required = false) LocalDate dateOfOpening,
             @ApiParam(value = "", allowableValues = "current, saving")
             @Valid @RequestParam(value = "accountType", required = false) String accountType) {

        String accept = request.getHeader("Accept");

        //retrieving data from the session
        Object princi = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Login login = (Login) princi;
        System.out.println(login.getUser().getId());

        return new ResponseEntity<List<Account>>
                (accountService.getBankAccounts(dateOfOpening, accountType), HttpStatus.OK);
    }

}
