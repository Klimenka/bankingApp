package nl.inholland.controller;

import io.swagger.annotations.ApiParam;
import nl.inholland.model.Login;
import nl.inholland.model.User;
import nl.inholland.service.LoginService;
import nl.inholland.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2019-06-02T11:27:08.122Z[GMT]")
@RestController
public class UsersApiController implements UsersApi {

    private UserService userService;
    private LoginService loginService;


    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(UserService userService, LoginService loginService) {

        this.userService = userService;
        this.loginService = loginService;
    }

    public ResponseEntity<Login> addUser(@ApiParam(value = "", required = true) @Valid @RequestBody User user) {

        return new ResponseEntity<Login>(userService.createUser(user), HttpStatus.CREATED);
    }

    public ResponseEntity<Void> createUserToken
            (@ApiParam(value = "Logs a user in and return an auth token, if the specified username and password are correct.", required = true)
             @Valid @RequestBody Login body) {

        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteUserById(@Min(1L) @ApiParam(value = "The id of the user to return", required = true, allowableValues = "") @PathVariable("userId") Long userId) {

        userService.deleteUser(userId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<User> getUserById(@Min(1L)
                                            @ApiParam(value = "The id of the user to return", required = true, allowableValues = "")
                                            @PathVariable("userId") Long userId) {

        return new ResponseEntity<User>(userService.getUser(userId), HttpStatus.OK);
    }

    public ResponseEntity<List<User>> getUsers(
            @ApiParam(value = "", allowableValues = "Customer, Employee")
            @Valid @RequestParam(value = "userType", required = false, defaultValue = "none") String userType) {

        return new ResponseEntity<List<User>>
                ((List<User>) userService.getUsers(userType), HttpStatus.OK);
    }

    public ResponseEntity<Login> updateUserLogin
            (@ApiParam(value = "", required = true)
             @Valid @RequestBody Login body,
             @Min(1L)
             @ApiParam(value = "The id of the user to return", required = true, allowableValues = "")
             @PathVariable("userId") Long userId) {

        return new ResponseEntity<Login>(loginService.updatePassword(body.getUserName(), body.getPassword()), HttpStatus.OK);
    }

}
