package com.mychko.demo.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mychko.demo.models.Candidate;
import com.mychko.demo.models.User;
import com.mychko.demo.models.Vote;
import com.mychko.demo.repositories.CandidateRepository;
import com.mychko.demo.repositories.UserRepository;
import com.mychko.demo.repositories.VoteRepository;

@Controller
@RequestMapping("/vote")
public class VoteController {
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final CandidateRepository candidateRepository;

    public VoteController(VoteRepository voteRepository, UserRepository userRepository, CandidateRepository candidateRepository) {
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.candidateRepository = candidateRepository;
    }

    @GetMapping
    public String showVotePage(Model model) {
        // Отримуємо список кандидатів з репозиторія
        model.addAttribute("candidates", candidateRepository.findAll());
        return "vote"; // Назва вашої сторінки голосування
    }

    @PostMapping("/cast")
    public String castVote(@RequestParam Long candidateId, @SessionAttribute(name = "userId", required = false) Long userId) {
        if (userId == null) {
            return "redirect:/login"; // Направляти на сторінку входу
        }

        User user = userRepository.findById(userId).orElseThrow();
        Candidate candidate = candidateRepository.findById(candidateId).orElseThrow();

        if (!user.hasVoted()) {
            Vote vote = new Vote(user, candidate);
            voteRepository.save(vote);
            user.setHasVoted(true);
            userRepository.save(user);
        }
        return "redirect:/vote/castVote";
    }
}
