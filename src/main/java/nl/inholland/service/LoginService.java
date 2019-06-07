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
    private UserRepository userRepository;

    public LoginService(LoginRepository loginRepository, UserRepository userRepository) {
        this.userRepository = userRepository;
        this.loginRepository = loginRepository;
    }

    /*public Login getLogin(String userName) {
        return loginRepository.findByUserName(userName);
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Login> optionalUsers = loginRepository.findByUserName(username);
        optionalUsers
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));

        return optionalUsers
                .map(CustomUserDetails::new).get();
    }

    public Login createLogin(User user) {
        Login login = new Login(user.getEmailAddress(), user);
        Login loginForEncode = loginRepository.save(login);
        encodePassword(loginForEncode);
        return login;
    }

    public Login encodePassword(Login login) {
        login.setPassword(passwordEncoder.encode(login.getPassword()));
        return loginRepository.save(login);
    }

}
