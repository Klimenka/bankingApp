package nl.inholland.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-02T11:27:08.122Z[GMT]")
@Controller
public class AccountsApiController implements AccountsApi {

    private static final Logger log = LoggerFactory.getLogger(AccountsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Void> closeBankAccount(@ApiParam(value = "the account number",required=true) @PathVariable("accountNumber") String accountNumber) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Object> createBankAccount(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Object body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Object>> getBankAccount(@Min(1L)@ApiParam(value = "The id of the user to return",required=true, allowableValues = "") @PathVariable("userId") Long userId,@ApiParam(value = "", allowableValues = "current, saving") @Valid @RequestParam(value = "accountType", required = false) String accountType) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<List<Object>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Object>> getBankAccounts(@ApiParam(value = "") @Valid @RequestParam(value = "dateOfOpening", required = false) LocalDate dateOfOpening,@ApiParam(value = "", allowableValues = "current, saving") @Valid @RequestParam(value = "accountType", required = false) String accountType) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<List<Object>>(HttpStatus.NOT_IMPLEMENTED);
    }

}
