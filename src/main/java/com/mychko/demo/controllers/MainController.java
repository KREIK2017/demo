package com.mychko.demo.controllers;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.mychko.demo.models.User;
import com.mychko.demo.repository.UserRepository;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index(Model model) {
        // Отримуємо поточного авторизованого користувача
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        // Якщо користувач ще не авторизований (якщо аутентифікація порожня)
        if (authentication == null || username == null) {
            return "redirect:/login"; // Перенаправляємо на сторінку входу
        }

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get(); // Отримуємо користувача
            model.addAttribute("username", user.getUsername()); // Додаємо ім'я користувача в модель
            model.addAttribute("userId", user.getId()); // Додаємо ID користувача в модель

            // Перевірка ролі користувача
            String role = authentication.getAuthorities().toString();

            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"));
            boolean isUser = authentication.getAuthorities().stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER"));

            model.addAttribute("isAdmin", isAdmin);
            model.addAttribute("isUser", isUser);

            if (role.contains("isAdmin")) {
                return "index";
            } else if (role.contains("isUser")) {
                return "index";
            }
        }

        return "index"; // Якщо нічого не підходить, повертаємо шаблон головної сторінки
    }
}
