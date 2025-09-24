package com.moodmix.moodmix.repository.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.moodmix.moodmix.repository.entities.User;

import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);  // custom
    Optional<User> findByEmail(String email);        // custom
    boolean existsByUsername(String username);       // custom
    boolean existsByEmail(String email);

}
