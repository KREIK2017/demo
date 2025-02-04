package com.mychko.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mychko.demo.models.User;
import com.mychko.demo.repositories.UserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserRepository userRepository;

    public AdminController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        user.setIsAdmin(true);
        userRepository.save(user);
        return "redirect:/admin/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "admin/login";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String username, @RequestParam String password, Model model,
            RedirectAttributes redirectAttributes) {
        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password) && user.getIsAdmin()) {
            // Якщо користувач знайдений, пароль правильний і це адміністратор
            redirectAttributes.addFlashAttribute("loggedInUser", username);
            return "redirect:/"; // Перенаправляємо на головну сторінку
        } else {
            // Якщо помилка, повертаємо користувача на сторінку логіну з повідомленням
            model.addAttribute("error", "Невірне ім'я користувача або пароль.");
            return "admin/login"; // Повертаємо на сторінку логіну
        }
    }

    @GetMapping("/delete")
    public String deleteLoginForm() {
        return "admin/delete";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam String username) {
        // Перевірка на існування користувача
        User user = userRepository.findByUsername(username);
        if (user != null) {
            userRepository.deleteByUsername(username); // Видалення користувача
        } else {
            // Обробка випадку, якщо користувача не існує
            return "redirect:/admin/error"; // Наприклад, редирект на сторінку помилки
        }
        return "redirect:/"; // Перенаправлення на головну після видалення
    }
}
