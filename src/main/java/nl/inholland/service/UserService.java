package nl.inholland.service;

import nl.inholland.model.User;
import nl.inholland.repository.AddressRepository;
import nl.inholland.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService {

    private UserRepository userRepository;
    private AddressRepository addressRepository;

    public UserService(UserRepository userRepository, AddressRepository addressRepository) {
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    public Iterable<User> getUsers(String userType) {
        if(userType.equals("none")){
            return userRepository.findAll();
        }
        else{
            return userRepository.getUserByUserTypeEquals(User.UserTypeEnum.fromValue(userType));
        }

    }

    public User createUser(User user){
        addressRepository.save(user.getPrimaryAddress());
        userRepository.save(user);

        //createLogin

        return userRepository.findTopByOrderByIdDesc();
    }
    public User getUser(Long userId){

        return userRepository.getUserByIdEquals(userId);
    }

    public void deleteUser(Long userId){
        User user = userRepository
                .findById(userId)
                .orElseThrow(IllegalArgumentException::new);
        userRepository.delete(user);
    }
}
