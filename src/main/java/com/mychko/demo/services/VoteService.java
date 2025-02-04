package com.mychko.demo.services;

import org.springframework.stereotype.Service;
import com.mychko.demo.models.Candidate;
import com.mychko.demo.models.User;
import com.mychko.demo.models.Vote;
import com.mychko.demo.repositories.CandidateRepository;
import com.mychko.demo.repositories.UserRepository;
import com.mychko.demo.repositories.VoteRepository;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;

    public VoteService(VoteRepository voteRepository, UserRepository userRepository, CandidateRepository candidateRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.candidateRepository = candidateRepository;
    }

    public void castVote(Long userId, Long candidateId) {
        User user = userRepository.findById(userId).orElseThrow();
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow();

        if (!user.hasVoted()) {
            Vote vote = new Vote(user, candidate);
            voteRepository.save(vote);
            user.setHasVoted(true);
            userRepository.save(user);
        }
    }
}
