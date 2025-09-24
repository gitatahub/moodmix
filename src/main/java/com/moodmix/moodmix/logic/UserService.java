package com.moodmix.moodmix.logic;

import com.moodmix.moodmix.repository.entities.User;
import com.moodmix.moodmix.repository.repositories.UserRepository;

import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;




@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User save(User user) {

        return userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email){
        return userRepository.existsByEmail(email);
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    @Transactional
    public User register(String username, String email, String rawPassword){

        username = username.trim();
        email = email.trim().toLowerCase();

        if (userRepository.existsByUsername(username)){
            throw new IllegalArgumentException("Username already exists");
        }
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email already exists");
        }
        User u = new User();
            u.setUsername(username);
            u.setEmail(email);
            u.setPasswordHash(passwordEncoder.encode(rawPassword));
        try {
            return userRepository.save(u);
        } catch (DataIntegrityViolationException e) {
            // in case two requests race each other
            throw new IllegalArgumentException("Username or email already exists");
        }
    }

    public User login(String usernameOrEmail, String rawPassword){
        String input = usernameOrEmail.trim();

        Optional<User> optionalUser = userRepository.findByUsername(input);
        if (optionalUser.isEmpty()) {
            optionalUser = userRepository.findByEmail(input.toLowerCase());
        }

        User user = optionalUser.orElseThrow(() ->
                new IllegalArgumentException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return user;


    }
}
