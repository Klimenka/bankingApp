package nl.inholland.repository;

import nl.inholland.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository <User, Long> {
    Iterable<User> getUserByUserTypeEquals(User.UserTypeEnum usertype);
    User findTopByOrderByIdDesc();
    User getUserByIdEquals(Long userId);
}
