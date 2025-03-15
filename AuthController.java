package com.example.mynewfishshop.controllers;

import com.example.mynewfishshop.models.ClientModel;
import com.example.mynewfishshop.models.User;
import com.example.mynewfishshop.repos.ClientRepo;
import com.example.mynewfishshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientRepo clientRepo;

    @GetMapping("/register")
    public String showRegistrationForm(Model model,Authentication authentication) {
        model.addAttribute("user", new User());
        model.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        // Проверьте, существует ли пользователь с таким же email
        if (userService.existsByEmail(user.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email уже зарегистрирован!");
            return "redirect:/register"; // Перенаправление обратно на страницу регистрации
        }
        userService.register(user);
        redirectAttributes.addFlashAttribute("successMessage", "Регистрация успешна!");
        return "redirect:/login"; // Перенаправление на страницу логина
    }

    @GetMapping("/login")
    public String showLoginForm(Model model,Authentication authentication) {
        model.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
        return "login"; // Страница логина
    }
    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String password, RedirectAttributes redirectAttributes) {
        // Проверьте данные для входа
        if (!userService.authenticate(email, password)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Неверный email или пароль!");
            return "redirect:/login"; // Перенаправление обратно на страницу логина
        }
        // Процесс входа пользователя
        return "redirect:/dashboard"; // Перенаправление на личный кабинет
    }

    @GetMapping("/dashboard")
    @PreAuthorize("isAuthenticated()") // Доступ только для авторизованных пользователей
    public String dashboard(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        model.addAttribute("username", username); // Передача имени пользователя в модель
        return "dashboard"; // Личный кабинет
    }
    @GetMapping("/logout")
    public String logout(Authentication authentication) {
        // Логика, которая выполняется при выходе (например, запись в логи)
        if (authentication != null) {
            // Можно добавить дополнительную логику, если нужно
            System.out.println("Пользователь " + authentication.getName() + " вышел из системы.");
        }
        return "logout"; // Возвращает имя шаблона для страницы выхода
    }
    @GetMapping("/about")
    public String getAboutInfo(Model model,Authentication authentication){
        model.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
        return "about";
    }
    @GetMapping("/contacts")
    public String getContacts(Model model,Authentication authentication){
        model.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated());
        return "contacts";
    }
    @PostMapping("/contacts")
    public RedirectView handleContactForm(@RequestParam String name, @RequestParam String email, @RequestParam String message, RedirectAttributes redirectAttributes) {
        ClientModel clientModel = new ClientModel();
        clientModel.setName(name);
        clientModel.setEmail(email);
        clientModel.setMessage(message);
        clientRepo.save(clientModel);
        redirectAttributes.addFlashAttribute("successMessage", "Ваше сообщение успешно отправлено!");
        return new RedirectView("/contacts"); // Перенаправление обратно на страницу контактов
    }
    @GetMapping("/privacy-policy")
    public String getPrivacyPolicy(Model model,Authentication authentication){
        model.addAttribute("isAuthenticated",authentication!=null&&authentication.isAuthenticated());
        return "privacy-policy";
    }
}
