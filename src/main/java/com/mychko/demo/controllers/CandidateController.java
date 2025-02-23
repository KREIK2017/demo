package com.mychko.demo.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mychko.demo.models.Candidate;
import com.mychko.demo.models.Vote;
import com.mychko.demo.repository.CandidateRepository;
import com.mychko.demo.repository.VoteRepository;

@Controller
@RequestMapping("/c")
public class CandidateController {

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired

    private VoteRepository voteRepository;

    // Отримати список кандидатів
    @GetMapping("/candidates")
    public String getCandidates(Model model) {
        model.addAttribute("candidates", candidateRepository.findAll());
        return "candidates-list";
    }

    // Додати нового кандидата
    @GetMapping("/candidate/add")
    public String showAddCandidateForm(Model model) {
        model.addAttribute("candidate", new Candidate());
        return "add-candidate";
    }

    @PostMapping("/candidate/add")
    public String addCandidate(@ModelAttribute Candidate candidate) {
        candidateRepository.save(candidate);
        return "redirect:/c/candidates";
    }

    // Показати сторінку видалення кандидатів
    @GetMapping("/candidate/delete")
    public String showDeleteCandidatePage(Model model) {
        model.addAttribute("candidates", candidateRepository.findAll());
        return "delete-candidate";
    }

    // Видалити кандидата (краще використовувати POST або DELETE)
    @PostMapping("/candidate/delete/{id}")
    public String deleteCandidate(@PathVariable Long id) {
        candidateRepository.deleteById(id);
        return "redirect:/c/candidate/delete";
    }

    // Перегляд всіх голосів за всіма кандидатами
    @GetMapping("/votes-delete")
public String viewAllVotes(Model model) {
    List<Candidate> candidates = candidateRepository.findAll();
    model.addAttribute("candidates", candidates);

    // Для кожного кандидата отримуємо голоси і групуємо їх
    Map<Long, List<Vote>> votesForCandidates = new HashMap<>();
    for (Candidate candidate : candidates) {
        List<Vote> votes = voteRepository.findByCandidateId(candidate.getId());
        votesForCandidates.put(candidate.getId(), votes);
    }
    model.addAttribute("votesForCandidates", votesForCandidates);

    return "all-votes";
}


    // Видалення голосу
    @PostMapping("/vote/delete-vote")
    public String deleteVote(@RequestParam Long voteId) {
        Optional<Vote> voteOptional = voteRepository.findById(voteId);
        if (voteOptional.isPresent()) {
            Vote vote = voteOptional.get();
            Long candidateId = vote.getCandidateId();
            Candidate candidate = candidateRepository.findById(candidateId).orElse(null);

            if (candidate != null) {
                // Видаляємо голос
                voteRepository.delete(vote);

                // Зменшуємо кількість голосів за кандидата
                candidate.setVoteCount(candidate.getVoteCount() - 1);
                candidateRepository.save(candidate);
            }
        }

        return "all-votes"; // Перенаправлення на сторінку з усіма голосами
    }

}
