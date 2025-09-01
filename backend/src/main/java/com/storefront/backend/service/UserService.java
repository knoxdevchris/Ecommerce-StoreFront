package com.storefront.backend.service;

import com.storefront.backend.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    
    // Get user by ID
    Optional<User> getUserById(Long id);
    
    // Get user by username
    Optional<User> getUserByUsername(String username);

    // Get user by email
    Optional<User> getUserByEmail(String email);

    // Get all users
    List<User> getAllUsers();
    
    // Get active users
    List<User> getActiveUsers();
    
    // Save user (create or update)
    User saveUser(User user);

    // Delete user
    void deleteUser(Long userId);

    // Update user
    User updateUser(User user);

    // Validate password
    boolean validatePassword(User user, String password);
}
