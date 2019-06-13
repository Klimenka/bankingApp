package nl.inholland.service;

import nl.inholland.model.CustomUserDetails;
import nl.inholland.model.Login;
import nl.inholland.model.User;
import nl.inholland.repository.LoginRepository;
import nl.inholland.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.Optional;


@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Login> user = loginRepository.findByUserName(username);

        user.orElseThrow(() -> new UsernameNotFoundException("username not found"));

        return user
                .map(CustomUserDetails::new).get();
    }

    public Login updatePassword(String username, String password) {
        Optional<Login> optionalUser = loginRepository.findByUserName(username);
        optionalUser.orElseThrow(() -> new UsernameNotFoundException("username not found"));

        Login user = optionalUser.get();
        user.setPassword(password);

        Login userForEncoding = loginRepository.save(user);
        encodePassword(userForEncoding);

        return user;
    }

    /**
     * Creates a new Login object, return it, calls a method which encodes password
     * and saves the object to in memory DB.
     * Checks a email before creation, because it serves for unique user name
     *
     * @param user
     * @return Login object
     */
    public Login createLogin(User user) {
        if (loginRepository.findByUserName(user.getEmailAddress()).isPresent()) {
            throw new IllegalArgumentException("Email already in use!");
        }

        Login userCredentials = new Login(user.getEmailAddress(), user);
        Login userCredentialsForEncoding = loginRepository.save(userCredentials);
        encodePassword(userCredentialsForEncoding);
        return userCredentials;
    }

    /**
     * Encodes password
     * and saves the object to in memory DB.
     *
     * @param userCredentials
     * @return
     */
    public Login encodePassword(Login userCredentials) {
        userCredentials.setPassword(passwordEncoder.encode(userCredentials.getPassword()));
        return loginRepository.save(userCredentials);
    }
    
}
