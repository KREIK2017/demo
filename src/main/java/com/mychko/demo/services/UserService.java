package com.mychko.demo.services;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mychko.demo.models.User;
import com.mychko.demo.repositories.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public void deleteUser(String username) {
        userRepository.deleteByUsername(username);
    }
}