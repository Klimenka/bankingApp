package nl.inholland.repository;

import nl.inholland.model.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoginRepository extends JpaRepository<Login, String> {

    Login findByUserName(String username);
}
