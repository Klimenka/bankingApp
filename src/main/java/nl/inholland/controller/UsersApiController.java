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

     private static final Logger log = LoggerFactory.getLogger(UsersApiController.class);

    private final ObjectMapper objectMapper;
    private UserService userService;
    private LoginService loginService;
    private final HttpServletRequest request;

    @org.springframework.beans.factory.annotation.Autowired
    public UsersApiController(ObjectMapper objectMapper, HttpServletRequest request, UserService userService, LoginService loginService) {
        this.objectMapper = objectMapper;
        this.request = request;
        this.userService = userService;
        this.loginService = loginService;
    }

    public ResponseEntity<Login> addUser(@ApiParam(value = "", required = true) @Valid @RequestBody User user) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Login>(userService.createUser(user), HttpStatus.CREATED);
    }

    public ResponseEntity<Void> createUserToken
            (@ApiParam(value = "Logs a user in and return an auth token, if the specified username and password are correct.", required = true)
             @Valid @RequestBody Login body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteUserById(@Min(1L) @ApiParam(value = "The id of the user to return", required = true, allowableValues = "") @PathVariable("userId") Long userId) {
        String accept = request.getHeader("Accept");
        userService.deleteUser(userId);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<User> getUserById(@Min(1L)
                                            @ApiParam(value = "The id of the user to return", required = true, allowableValues = "")
                                            @PathVariable("userId") Long userId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<User>(userService.getUser(userId), HttpStatus.OK);
    }

    public ResponseEntity<List<User>> getUsers(
            @ApiParam(value = "", allowableValues = "Customer, Employee")
            @Valid @RequestParam(value = "userType", required = false, defaultValue = "none") String userType) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<List<User>>
                ((List<User>) userService.getUsers(userType), HttpStatus.OK);
    }

    public ResponseEntity<Login> updateUserLogin
            (@ApiParam(value = "", required = true)
             @Valid @RequestBody Login body,
             @Min(1L)
             @ApiParam(value = "The id of the user to return", required = true, allowableValues = "")
             @PathVariable("userId") Long userId) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Login>(loginService.updatePassword(body.getUserName(), body.getPassword()), HttpStatus.OK);
    }

}
