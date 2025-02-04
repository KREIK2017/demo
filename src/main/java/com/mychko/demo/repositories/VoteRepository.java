package com.mychko.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mychko.demo.models.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
}