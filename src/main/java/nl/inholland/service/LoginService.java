package nl.inholland.service;

import nl.inholland.model.Login;
import nl.inholland.repository.LoginRepository;
import org.springframework.stereotype.Service;


@Service
public class LoginService {

    private LoginRepository loginRepository;

    public LoginService(LoginRepository loginRepository) {

        this.loginRepository = loginRepository;
    }

    public Login getLogin(String userName) {
        return loginRepository.findByUserName(userName);

    }

}
