package nl.inholland.filter;

import nl.inholland.model.Login;
import nl.inholland.model.User;
import nl.inholland.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class WebSecurity {

    @Autowired
    private UserRepository userRepository;

    public boolean checkUserId(Authentication authentication, long id) {

        Object principal = authentication.getPrincipal();
        Login currentUser = (Login) principal;

        User retrievedUser =
                userRepository
                        .findById(currentUser.getUser().getId())
                        .orElseThrow(IllegalArgumentException::new);

        return (retrievedUser != null && retrievedUser.getId() == id)
                || (retrievedUser != null
                && retrievedUser.getRoles().contains("Employee"));
    }

}
