package com.example.schoolorganizer.web;

import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.dto.RegisterUserDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.model.User;
import com.example.schoolorganizer.security.UserLoggedInValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 */
@Controller
public class MainController {
    /**
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/signup")
    public String getRegistrationForm(Model model, HttpSession session) {
        if (UserLoggedInValidator.hasUserLoggedIn(session)) {
            session.invalidate();
        }
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new RegisterUserDTO());
        }
        return "signup";
    }

    /**
     * @param model
     * @param session
     * @return
     */
    @GetMapping("/signin")
    public String getLoginForm(Model model, HttpSession session) {
        if (UserLoggedInValidator.hasUserLoggedIn(session)) {
            session.invalidate();
        }
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new LoginUserDTO());
        }
        return "signin";
    }

    /**
     * @param httpSession
     * @param model
     * @return
     */
    @GetMapping("/home")
    public String getHomeForm(HttpSession httpSession, Model model) {
        if (UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
            model.addAttribute("user", loggedUser);
            httpSession.setAttribute("foundEvents", null);
            return "home";
        }

        return "redirect:/signin";
    }

    /**
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/signin";
    }

    /**
     * @return
     */
    @GetMapping("/error")
    public String getErrorPage() {
        return "error-page";
    }
}