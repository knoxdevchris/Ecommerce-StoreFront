package com.storefront.backend.service;

import com.storefront.backend.entity.User;
import java.util.List;
import java.util.Optional;

public interface UserService {
    
    List<User> getAllUsers();
    
    Optional<User> getUserById(Long id);
    
    Optional<User> getUserByEmail(String email);
    
    Optional<User> getUserByUsername(String username);
    
    List<User> getActiveUsers();
    
    User saveUser(User user);
    
    void deleteUser(Long id);
    
    boolean validatePassword(User user, String password);
    
    boolean updatePassword(Long userId, String newPassword);
}
