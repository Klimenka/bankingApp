package nl.inholland.repository;

import nl.inholland.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Iterable<User> getUserByRolesEquals(String roles);

    User findTopByOrderByIdDesc();

    //retrievedUser.getRoles().contains("Employee"));
    User getUserByIdEquals(Long userId);
}
