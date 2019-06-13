package nl.inholland.controller;


import nl.inholland.model.Account;
import nl.inholland.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Generated;
import javax.validation.constraints.*;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-02T11:27:08.122Z[GMT]")
@RestController
public class AccountsApiController implements AccountsApi {

    private AccountService accountService;

    @Autowired
    public AccountsApiController(AccountService accountService) {
        this.accountService = accountService;
    }

    public ResponseEntity<String> closeBankAccount(@ApiParam(value = "the account number",
            required = true) @PathVariable("accountNumber") long accountNumber) {

        accountService.closeBankAccount(accountNumber);
        return new ResponseEntity<String>("The account has been successfully closed.", HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<Account> createBankAccount(@ApiParam(value = "", required = true)
                                                     @Valid @RequestBody Account body) {

        return new ResponseEntity<Account>(accountService.createBankAccount(body), HttpStatus.CREATED);
    }

    public ResponseEntity<List<Account>> getBankAccount
            (@Min(1L) @ApiParam(value = "The id of the user to return", required = true, allowableValues = "")
             @PathVariable("userId") long userId,
             @ApiParam(value = "", allowableValues = "current, saving")
             @Valid @RequestParam(value = "accountType", required = false) String accountType) {

        return new ResponseEntity<List<Account>>
                (accountService.getBankAccount(userId, accountType), HttpStatus.OK);
    }

    public ResponseEntity<List<Account>> getBankAccounts
            (@ApiParam(value = "")
             @RequestParam(value = "dateOfOpening", required = false) LocalDate dateOfOpening,
             @ApiParam(value = "", allowableValues = "current, saving")
             @Valid @RequestParam(value = "accountType", required = false) String accountType) {

        return new ResponseEntity<List<Account>>
                (accountService.getBankAccounts(dateOfOpening, accountType), HttpStatus.OK);
    }

}
