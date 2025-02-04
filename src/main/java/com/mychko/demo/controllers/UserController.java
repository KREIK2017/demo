package com.mychko.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.mychko.demo.models.User;
import com.mychko.demo.repositories.UserRepository;

@Controller
@RequestMapping("/user")
@SessionAttributes("loggedInUser") // Зберігаємо користувача в сесії
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userRepository.save(user);
        return "redirect:/user/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            Model model) {
        // Знаходимо користувача в базі за іменем та паролем
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user != null) {
            model.addAttribute("loggedInUser", user);
            return "redirect:/"; // Повертаємо на головну сторінку після успішного входу
        } else {
            model.addAttribute("error", "Невірний логін або пароль");
            return "user/login"; // Якщо не вдалося знайти користувача, повертаємо назад на сторінку входу
        }
    }

    @GetMapping("/vote")
    public String showVotingPage() {
        return "user/vote";
    }

    @GetMapping("/logout")
    public String logout(Model model) {
        model.addAttribute("loggedInUser", null);
        return "redirect:/";
    }
}
