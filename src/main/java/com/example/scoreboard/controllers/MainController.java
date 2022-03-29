package com.example.scoreboard.controllers;

import com.example.scoreboard.entites.User;
import com.example.scoreboard.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class MainController {
    private UserServiceImpl userService;
//    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public MainController(UserServiceImpl userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
//        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login_page";
    }

    @GetMapping("/registration")
    public String showRegistrationPage(Model model) {
        model.addAttribute("user", new User());
        return "registration_page";
    }

    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, @RequestParam("confirm") String confirm, Model model) {
        if (bindingResult.hasErrors()) return "registration_page";

        if (!confirm.equals(user.getPassword())) {
            model.addAttribute("confirm", "passwords do not match");
            return "registration_page";
        }

        if (userService.findByEmail(user.getEmail()) == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userService.save(user);
        } else {
            model.addAttribute("registrationError", "User with current email is already exist");
            return "registration_page";
        }
        return "redirect:/login";
    }
}
