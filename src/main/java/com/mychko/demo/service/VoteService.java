package com.mychko.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.mychko.demo.repository.VoteRepository;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    // Метод для видалення голосу
    public void deleteVote(Long userId, Long candidateId) {
        voteRepository.findByUserIdAndCandidateId(userId, candidateId).ifPresent(vote -> {
            voteRepository.delete(vote);
        });
    }
}
