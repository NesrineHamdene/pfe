package com.project.ModuleUser.service;

import com.project.ModuleUser.entities.User;
import com.project.ModuleUser.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceUser implements IServiceUser {

    @Autowired // Injection explicite via @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
