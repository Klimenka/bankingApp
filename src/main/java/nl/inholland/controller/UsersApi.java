/**
 * NOTE: This class is auto generated by the swagger code generator program (3.0.8).
 * https://github.com/swagger-api/swagger-codegen
 * Do not edit the class manually.
 */
package nl.inholland.controller;

import nl.inholland.model.Error;
import nl.inholland.model.Login;
import io.swagger.annotations.*;
import nl.inholland.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-02T11:27:08.122Z[GMT]")
@Api(value = "users", description = "the users API")
public interface UsersApi {

    @ApiOperation(value = "Adds a new user", nickname = "addUser", notes = "Adds a new user and returns it", response = User.class, authorizations = {
        @Authorization(value = "api_key")    }, tags={ "users", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "the user has been created.", response = User.class),
        @ApiResponse(code = 400, message = "Bad request.", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error.class) })
    @RequestMapping(value = "/users",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<User> addUser(@ApiParam(value = "" ,required=true )  @Valid @RequestBody User body);


    @ApiOperation(value = "Returns an auth token", nickname = "createUserToken", notes = "", tags={ "users", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "Token send"),
        @ApiResponse(code = 400, message = "Bad request.", response = Error.class),
        @ApiResponse(code = 404, message = "The specified resource was not found", response = Error.class) })
    @RequestMapping(value = "/users/login",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.POST)
    ResponseEntity<Void> createUserToken(@ApiParam(value = "Logs a user in and return an auth token, if the specified username and password are correct." ,required=true )  @Valid @RequestBody Login body);


    @ApiOperation(value = "Delete a user by user ID", nickname = "deleteUserById", notes = "", authorizations = {
        @Authorization(value = "api_key")    }, tags={ "users", })
    @ApiResponses(value = { 
        @ApiResponse(code = 204, message = "No content"),
        @ApiResponse(code = 400, message = "Bad request.", response = Error.class),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error.class) })
    @RequestMapping(value = "/users/{userId}",
        produces = { "application/json" }, 
        method = RequestMethod.DELETE)
    ResponseEntity<Void> deleteUserById(@Min(1L)@ApiParam(value = "The id of the user to return",required=true, allowableValues = "") @PathVariable("userId") Long userId);


    @ApiOperation(value = "Gets a user by user ID", nickname = "getUserById", notes = "Gets a user by user ID", response = Object.class, tags={ "users", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "a user object", response = Object.class) })
    @RequestMapping(value = "/users/{userId}",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<Object> getUserById(@Min(1L)@ApiParam(value = "The id of the user to return",required=true, allowableValues = "") @PathVariable("userId") Long userId);


    @ApiOperation(value = "get all users", nickname = "getUsers", notes = "getting all users (employees and customers) by Employee", response = Object.class, responseContainer = "List", authorizations = {
        @Authorization(value = "api_key")    }, tags={ "users", })
    @ApiResponses(value = { 
        @ApiResponse(code = 200, message = "OK", response = Object.class, responseContainer = "List"),
        @ApiResponse(code = 401, message = "Unauthorized", response = Error.class),
        @ApiResponse(code = 404, message = "The specified resource was not found", response = Error.class) })
    @RequestMapping(value = "/users",
        produces = { "application/json" }, 
        method = RequestMethod.GET)
    ResponseEntity<List<User>> getUsers(@ApiParam(value = "", allowableValues = "Customer, Employee") @Valid @RequestParam(value = "userType", required = false) String userType);


    @ApiOperation(value = "update the user's login info", nickname = "updateUserLogin", notes = "calling this will allow the user to change his/her credentials", tags={ "users", })
    @ApiResponses(value = { 
        @ApiResponse(code = 201, message = "user credentials has successfully been updated"),
        @ApiResponse(code = 400, message = "Bad request.", response = Error.class),
        @ApiResponse(code = 404, message = "The specified resource was not found", response = Error.class) })
    @RequestMapping(value = "/users/{userId}/account",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.PUT)
    ResponseEntity<Void> updateUserLogin(@ApiParam(value = "" ,required=true )  @Valid @RequestBody Login body,@Min(1L)@ApiParam(value = "The id of the user to return",required=true, allowableValues = "") @PathVariable("userId") Long userId);

}
