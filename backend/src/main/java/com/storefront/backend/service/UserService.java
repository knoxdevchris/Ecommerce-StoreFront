package com.storefront.backend.service;

import org.springframework.stereotype.Service;

import com.storefront.backend.entity.User;
import com.storefront.backend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User createUser(User user) {
        return userRepository.save(user);
    }
    public User loginUser(User user) {
        return userRepository.findByEmail(user.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
    }
    public User updateUserProfile(Long userId, User updates) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setEmail(updates.getEmail());
        user.setPassword(updates.getPassword());
        return userRepository.save(user);
    }



}
