package com.example.schoolorganizer.web;

import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.dto.RegisterUserDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.security.UserLoggedInValidator;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This class describes a MainController. A class that manage
 * with viewing main html pages such as - registration form,
 * login form, home page and logging out of definite user's
 * personal profile.
 *
 * @author Petya Licheva
 */
@Controller
public class MainController {

    /**
     * This method shows the registration form.
     *
     * @param model   the model object.
     * @param session the http session object.
     * @return a html page via its title.
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
     * This method shows the login form.
     *
     * @param model   the model object.
     * @param session the http session object.
     * @return a html page via its title.
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
     * This method shows the home page after successful login.
     *
     * @param httpSession the http session.
     * @param model       the model.
     * @return a html page vi its title or redirects to the login
     * form in case of invalidated http session.
     */
    @GetMapping("/home")
    public String getHomeForm(HttpSession httpSession, Model model) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }

        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        model.addAttribute("user", loggedUser);
        httpSession.setAttribute("foundEvents", null);
        return "home";
    }

    /**
     * This method invalidates the current  http session
     * and redirects to the login html page.
     *
     * @param session the http session.
     * @return a html page via redirection and the title
     * of the html page.
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/signin";
    }

    /**
     * This method return the application's custom html error page.
     *
     * @return a html page via its title.
     */
    @GetMapping("/error")
    public String getErrorPage() {
        return "error-page";
    }
}