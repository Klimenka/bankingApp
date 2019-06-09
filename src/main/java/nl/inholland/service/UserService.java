package nl.inholland.service;

import nl.inholland.model.*;
import nl.inholland.repository.AddressRepository;
import nl.inholland.repository.LoginRepository;
import nl.inholland.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    private UserRepository userRepository;
    private AddressRepository addressRepository;
    private LoginService loginService;
    private AccountService accountService;

    public UserService(UserRepository userRepository, AddressRepository addressRepository,
                       LoginService loginService, AccountService accountService) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.loginService = loginService;
        this.accountService = accountService;
    }

    public Iterable<User> getUsers(String userType) {
        if (userType.equals("none")) {
            return userRepository.findAll();
        } else {

            return userRepository.getAllUsers(userType);
        }

    }

    public Login createUser(User user) {
        addressRepository.save(user.getPrimaryAddress());
        User userCreated = userRepository.save(user);

        if (getUserRole(userCreated).equals("Customer")) {
            Account bankAccount = new CurrentAccount();
            bankAccount.setUserIdentification(userCreated.getId());
            accountService.createBankAccount(bankAccount);
        }

        return loginService.createLogin(userCreated);
    }

    private String getUserRole(User user) {
        Set<Role> role = user.getRoles();
        String userRole = role.stream().map(Role::getRole).findAny().get();

        return userRole;
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
