package com.mychko.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mychko.demo.models.Candidate;

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    // JpaRepository вже містить методи findAll(), save(), deleteById() та інші.
}
