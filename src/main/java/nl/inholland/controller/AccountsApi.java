package nl.inholland.controller;

import nl.inholland.model.Account;
import io.swagger.annotations.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-02T11:27:08.122Z[GMT]")
@Api(value = "accounts", description = "the accounts API")
public interface AccountsApi {

    @ApiOperation(value = "close a bank account", nickname = "closeBankAccount", notes = "Calling this will allow an employee to close a specific bank account", authorizations = {
            @Authorization(value = "api_key")}, tags = {"accounts",})
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "the bank account has successfully been closed"),
            @ApiResponse(code = 400, message = "Bad request."),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "The specified resource was not found")})
    @RequestMapping(value = "/accounts/{accountNumber}",
            produces = {"application/json"},
            method = RequestMethod.DELETE)
    ResponseEntity<String> closeBankAccount(@ApiParam(value = "the account number", required = true) @PathVariable("accountNumber") long accountNumber);


    @ApiOperation(value = "create a bank account", nickname = "createBankAccount", notes = "Calling this will allow epmployees to open an account for a specific customer", response = Account.class, authorizations = {
            @Authorization(value = "api_key")}, tags = {"accounts",})
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "the account has been created", response = Account.class),
            @ApiResponse(code = 400, message = "Bad request."),
            @ApiResponse(code = 401, message = "Unauthorized")})
    @RequestMapping(value = "/accounts",
            produces = {"application/json"},
            consumes = {"application/json"},
            method = RequestMethod.POST)
    ResponseEntity<Account> createBankAccount(@ApiParam(value = "", required = true) @Valid @RequestBody Account body);


    @ApiOperation(value = "get bank accounts of a specific user", nickname = "getBankAccount", notes = "Calling this will allow both customer and employee to check data of a specific account userId and filter them by bank account type", response = Account.class, responseContainer = "List", tags = {"accounts",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "the account data", response = Account.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad request."),
            @ApiResponse(code = 404, message = "The specified resource was not found")})
    @RequestMapping(value = "/accounts/{userId}",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Account>> getBankAccount(@Min(1L) @ApiParam(value = "The id of the user to return", required = true, allowableValues = "") @PathVariable("userId") long userId, @ApiParam(value = "", allowableValues = "current, saving") @Valid @RequestParam(value = "accountType", required = false) String accountType);


    @ApiOperation(value = "get all bank accounts", nickname = "getBankAccounts", notes = "Calling this will allow Employees to check data of all accounts or filter them by the date of opening Asc or Desc or based on the type of bank account [saving or current]", response = Account.class, responseContainer = "List", authorizations = {
            @Authorization(value = "api_key")}, tags = {"accounts",})
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "the accounts data", response = Account.class, responseContainer = "List"),
            @ApiResponse(code = 400, message = "Bad request."),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 404, message = "The specified resource was not found")})
    @RequestMapping(value = "/accounts",
            produces = {"application/json"},
            method = RequestMethod.GET)
    ResponseEntity<List<Account>> getBankAccounts(@ApiParam(value = "") @RequestParam(value = "dateOfOpening", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfOpening, @ApiParam(value = "", allowableValues = "current, saving") @Valid @RequestParam(value = "accountType", required = false) String accountType);

}
