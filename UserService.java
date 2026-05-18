package com.realestate.service;

import com.realestate.model.User;
import com.realestate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service layer for User management.
 * Contains business logic for registration, login, and profile management.
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean register(User user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return false; // Email already registered
        }
        // Generate unique ID if not present
        if (user.getUserId() == null || user.getUserId().isEmpty()) {
            user.setUserId(UUID.randomUUID().toString().substring(0, 8));
        }
        userRepository.save(user);
        return true;
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(String id) {
        return userRepository.findById(id);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
    
    public boolean validatePassword(String password) {
        // Simple validation: at least 6 characters
        return password != null && password.length() >= 6;
    }
}
