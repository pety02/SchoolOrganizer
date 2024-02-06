package com.example.schoolorganizer.web;

import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.dto.RegisterUserDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/signup")
    public String getRegistrationForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new RegisterUserDTO());
        }
        return "signup";
    }

    @GetMapping("/signin")
    public String getLoginForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new LoginUserDTO());
        }
        return "signin";
    }

    @GetMapping("/home")
    public String getHomeForm(Model model, LoginUserDTO user) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", user);
        }

        return "home";
    }
}
