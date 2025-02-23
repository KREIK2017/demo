package com.mychko.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mychko.demo.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
