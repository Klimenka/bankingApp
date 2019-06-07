package nl.inholland.service;

import nl.inholland.model.CustomUserDetails;
import nl.inholland.model.Login;
import nl.inholland.model.User;
import nl.inholland.repository.LoginRepository;
import nl.inholland.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.Optional;


@Service
public class LoginService implements UserDetailsService {

    private LoginRepository loginRepository;
    private UserRepository userRepository;

    public LoginService(LoginRepository loginRepository) {

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

    public Login createLogin(Login login) {
        User user = userRepository
                .findById(login.getUserId())
                .orElseThrow(IllegalArgumentException::new);

        login.setUser(user);
        Login newLogin = loginRepository.save(login);

        return login;
    }
}
