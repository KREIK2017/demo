package com.mychko.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mychko.demo.models.Vote;

public interface VoteRepository extends JpaRepository<Vote, Long> {
    void deleteByCandidateId(Long candidateId);
    Optional<Vote> findByUserIdAndCandidateId(Long userId, Long candidateId); // Для перевірки голосу конкретного користувача
    List<Vote> findByCandidateId( Long candidateId); 
    List<Vote> findAll();
    boolean existsByUserId(Long userId);  // Перевірка, чи голосував вже користувач
    long countByCandidateId(Long candidateId);
    
}
