package com.example.schoolorganizer.web;

import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.dto.RegisterUserDTO;
import com.example.schoolorganizer.model.User;
import jakarta.servlet.http.HttpSession;
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
    public String getHomeForm(HttpSession httpSession, Model model) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser != null) {
            model.addAttribute("user", loggedUser);
            return "home";
        }

        return "redirect:/signin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/signin";
    }

    @GetMapping("/error")
    public String getErrorPage() {
        return "error-page";
    }
}
