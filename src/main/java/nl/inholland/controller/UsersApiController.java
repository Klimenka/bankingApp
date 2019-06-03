package nl.inholland.controller;

import nl.inholland.model.Login;
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
public class UsersApiController implements UsersApi {

    private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<Object> addUser(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Object body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> createUserToken
            (@ApiParam(value = "Logs a user in and return an auth token, if the specified username and password are correct." ,required=true )
             @Valid @RequestBody Login body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteUserById(@Min(1L)@ApiParam(value = "The id of the user to return",required=true, allowableValues = "") @PathVariable("userId") Long userId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Object> getUserById(@Min(1L)@ApiParam(value = "The id of the user to return",required=true, allowableValues = "") @PathVariable("userId") Long userId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Object>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<Object>> getUsers(@ApiParam(value = "", allowableValues = "Customer, Employee") @Valid @RequestParam(value = "userType", required = false) String userType) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<List<Object>>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> updateUserLogin
            (@ApiParam(value = "" ,required=true )
             @Valid @RequestBody Login body,
             @Min(1L)
             @ApiParam(value = "The id of the user to return",required=true, allowableValues = "")
             @PathVariable("userId") Long userId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

}
