package com.mychko.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import com.mychko.demo.models.User;


public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
    public User findByUsernameAndPassword(String username, String password);

    @Modifying
    @Transactional
    void deleteByUsername(String username);
}
