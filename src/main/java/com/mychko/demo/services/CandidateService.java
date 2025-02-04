package com.mychko.demo.services;

import org.springframework.stereotype.Service;
import com.mychko.demo.models.Candidate;
import com.mychko.demo.repositories.CandidateRepository;

import java.util.List;

@Service
public class CandidateService {
    private final CandidateRepository candidateRepository;

    public CandidateService(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    public List<Candidate> getAllCandidates() {
        return candidateRepository.findAll();
    }
}
