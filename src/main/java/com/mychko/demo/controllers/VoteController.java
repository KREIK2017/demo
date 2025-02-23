package com.mychko.demo.controllers;

import com.mychko.demo.models.Candidate;
import com.mychko.demo.models.User;
import com.mychko.demo.models.Vote;
import com.mychko.demo.repository.VoteRepository;
import com.mychko.demo.service.VoteService;
import com.mychko.demo.repository.CandidateRepository;
import com.mychko.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/vote")
public class VoteController {

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private CandidateRepository candidateRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VoteService voteService;

    // Перегляд кандидатів для голосування
    @GetMapping("/vote")
    public String votePage(Model model) {
        model.addAttribute("candidates", candidateRepository.findAll()); // Всі кандидати
        return "vote"; // сторінка для голосування
    }

    // Додати голос
    @PostMapping("/vote")
    public String voteForCandidate(@RequestParam Long candidateId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        
        // Якщо користувач не авторизований
        if (username == null) {
            return "redirect:/login";
        }
    
        Optional<User> userOptional = userRepository.findByUsername(username);
    
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            
            // Перевірка, чи користувач вже голосував
            if (voteRepository.existsByUserId(user.getId())) {
                return "redirect:/vote/already-voted"; // Якщо голосував, перенаправити на сторінку з повідомленням
            }
    
            // Зберігаємо голос
            Vote vote = new Vote();
            vote.setUser(user); // Використовуємо поточного користувача
            vote.setCandidateId(candidateId);
            voteRepository.save(vote); // Зберігаємо голос
    
            // Оновлюємо кількість голосів для кандидата
            Optional<Candidate> candidateOptional = candidateRepository.findById(candidateId);
            if (candidateOptional.isPresent()) {
                Candidate candidate = candidateOptional.get();
                candidate.setVoteCount(candidate.getVoteCount() + 1); // Збільшуємо кількість голосів на 1
                candidateRepository.save(candidate); // Зберігаємо оновлену кількість голосів
            }
    
            return "redirect:/vote/candidate-votes";
        }
    
        return "redirect:/login"; // Якщо користувача не знайдено
    }
    

    // Перегляд голосів за кандидатами
    @GetMapping("/candidate-votes")
public String viewVotes(Model model) {
    List<Candidate> candidates = candidateRepository.findAll(); // Список кандидатів
    
    // Підраховуємо голоси для кожного кандидата
    for (Candidate candidate : candidates) {
        long voteCount = voteRepository.countByCandidateId(candidate.getId());
        candidate.setVoteCount((int) voteCount); // Встановлюємо кількість голосів
    }

    model.addAttribute("candidates", candidates); // Передаємо кандидатів з кількістю голосів
    return "candidate-votes"; // Сторінка перегляду голосів
}


    // Сторінка, якщо користувач вже голосував
    @GetMapping("/already-voted")
    public String alreadyVoted() {
        return "already-voted"; // Повідомлення про те, що голосування вже відбулося
    }

    // Видалити голос
    @GetMapping("/vote/delete/{candidateId}")
    public String deleteVote(@PathVariable Long candidateId, @SessionAttribute(name = "user", required = false) User user) {
        if (user == null) {
            return "redirect:/login"; // Якщо користувач не авторизований
        }

        voteService.deleteVote(user.getId(), candidateId);
        return "candidates-list";
    }
}
