package com.mychko.demo.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.mychko.demo.models.User;
import com.mychko.demo.repository.UserRepository;
import com.mychko.demo.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    private UserRepository userRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "user/register";
        }
        userService.registerUser(user);
        return "redirect:/index";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, HttpServletRequest request) {
        Optional<User> optionalUser = userRepository.findByUsername(username); // Отримуємо Optional<User>

        if (optionalUser.isPresent()) { // Перевіряємо, чи юзер знайдений
            User user = optionalUser.get(); // Отримуємо User з Optional
            request.getSession().setAttribute("user", user); // Зберігаємо весь об'єкт User в сесії
            System.out.println("User saved in session: " + user.getUsername()); // Додати логування

            return "redirect:/";
        }

        return "redirect:/login?error"; // Якщо користувача нема, повертаємо на сторінку логіну з помилкою
    }

    @GetMapping("/logout")
    public String logoutUser(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }
}