package nl.inholland.service;

import nl.inholland.model.*;
import nl.inholland.repository.AddressRepository;
import nl.inholland.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

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

    /**
     * Gets all users from repository by their type (Possible values: Customer, Employee, none)
     *
     * @param userType
     * @return Iterable User list
     */
    public Iterable<User> getUsers(String userType) {
        if (userType.equals("none")) {
            return userRepository.findAll();
        } else {

            return userRepository.getAllUsers(userType);
        }

    }

    /**
     * Creates a user object, saves it to in-memory DB, generates a new Login.
     * If the user object has a role Customer, it creates a new current account for it.
     *
     * @param user
     * @return Login object
     */
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

    /**
     * Converts user; roles hash set value to String
     *
     * @param user
     * @return user role as a String
     */
    private String getUserRole(User user) {
        Set<Role> role = user.getRoles();
        String userRole = role.stream().map(Role::getRole).findAny().get();

        return userRole;
    }

    /**
     * Gets a user by userId
     *
     * @param userId
     * @return User object (Customer or Employee)
     */
    public User getUser(Long userId) {

        return userRepository.getUserByIdEquals(userId);
    }

    /**
     * Deletes a user by userId
     *
     * @param userId
     */
    public void deleteUser(Long userId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(IllegalArgumentException::new);

        userRepository.delete(user);

    }
}
