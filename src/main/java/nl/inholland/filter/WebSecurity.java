package nl.inholland.filter;

import nl.inholland.model.Login;
import nl.inholland.model.User;
import nl.inholland.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/**
 * This class is a helper class to help give the right authority
 * based on the user ID
 */
@Component
public class WebSecurity {

    @Autowired
    private UserRepository userRepository;

    private User getCurrentUser(Authentication authentication) {

        Login currentUser;
        Object principal = authentication.getPrincipal();
        if (principal instanceof Login) {
            currentUser = (Login) principal;
            return userRepository
                    .findById(currentUser.getUser().getId())
                    .orElseThrow(IllegalArgumentException::new);
        } else {
            return null;
        }
    }

    public boolean checkUserId(Authentication authentication, long id) {

        User currentUser = getCurrentUser(authentication);
        if (currentUser == null) return false;

        return (currentUser != null && currentUser.getId() == id)
                || (currentUser != null && id != 1 && currentUser.getRoles()
                .stream()
                .anyMatch(role -> role.getRole().equals("Employee")))
                || (currentUser != null && currentUser.getRoles()
                .stream()
                .anyMatch(role -> role.getRole().equals("Owner")));
    }

}
