package com.example.mynewfishshop.controllers;

import com.example.mynewfishshop.models.User;
import com.example.mynewfishshop.repos.UserRepository;
import com.example.mynewfishshop.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @GetMapping("/edit")
    public String editProfile(Model model, Principal principal) {
        // Получите информацию о пользователе по имени
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "profile-edit"; // имя вашего шаблона
    }
    @PostMapping("/update")
    public String updateProfile(@ModelAttribute User user,
                                @RequestParam(required = false) String password,
                                RedirectAttributes redirectAttributes) {
        if (user.getId() == null) {
            throw new IllegalArgumentException("ID пользователя не может быть null");
        }

        // Используем метод updateUser из UserService
        userService.updateUser(user, password);
        redirectAttributes.addFlashAttribute("successMessage", "Профиль успешно обновлён!");
        return "redirect:/dashboard"; // Перенаправление на личный кабинет
    }

}
