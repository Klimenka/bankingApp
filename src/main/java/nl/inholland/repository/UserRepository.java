package nl.inholland.repository;

import nl.inholland.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u JOIN u.roles ur WHERE ur.role like ?1")
    Iterable<User> getAllUsers(String role);
    User getUserByIdEquals(Long userId);
}
