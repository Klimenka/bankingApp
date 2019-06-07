package nl.inholland.service;

import nl.inholland.model.Address;
import nl.inholland.model.Login;
import nl.inholland.model.User;
import nl.inholland.repository.AddressRepository;
import nl.inholland.repository.LoginRepository;
import nl.inholland.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private LoginService loginService;


    public UserService(UserRepository userRepository, AddressRepository addressRepository, LoginService loginService) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.loginService = loginService;
    }

    public Iterable<User> getUsers(String userType) {
        if (userType.equals("none")) {
            return userRepository.findAll();
        } else {
            return userRepository.getUserByRolesEquals(userType);
        }

    }

    public Login createUser(User user) {
        addressRepository.save(user.getPrimaryAddress());
        User userCreated = userRepository.save(user);

        return loginService.createLogin(userCreated);
    }

    public User getUser(Long userId) {

        return userRepository.getUserByIdEquals(userId);
    }

    public void deleteUser(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(IllegalArgumentException::new);
        userRepository.delete(user);
    }
}
