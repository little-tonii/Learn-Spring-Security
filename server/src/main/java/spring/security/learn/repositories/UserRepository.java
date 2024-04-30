package spring.security.learn.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.security.learn.exceptions.DataNotFoundException;
import spring.security.learn.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username) throws DataNotFoundException;
}
