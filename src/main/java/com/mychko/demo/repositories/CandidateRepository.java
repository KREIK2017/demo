package com.mychko.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mychko.demo.models.Candidate;
import java.util.List;  // Додано імпорт List

public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    List<Candidate> findAll();
}
