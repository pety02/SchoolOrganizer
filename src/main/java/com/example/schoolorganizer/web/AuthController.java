package com.example.schoolorganizer.web;

import com.example.schoolorganizer.dao.IDAO;
import com.example.schoolorganizer.dao.Impl.LoginUserDAOImpl;
import com.example.schoolorganizer.dao.Impl.RegisterUserDAOImpl;
import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.dto.RegisterUserDTO;
import com.example.schoolorganizer.model.User;
import com.example.schoolorganizer.service.Impl.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

@Controller
@Slf4j
public class AuthController {
    private final UserServiceImpl userService;
    private final IDAO<User, LoginUserDTO> loginDAO;
    private final IDAO<User, RegisterUserDTO> registerDAO;

    @Autowired
    public AuthController(UserServiceImpl userService, LoginUserDAOImpl loginDAO, RegisterUserDAOImpl registerDAO) {
        this.userService = userService;
        this.loginDAO = loginDAO;
        this.registerDAO = registerDAO;
    }

    @PostMapping("/signin")
    public String login(@Valid @ModelAttribute LoginUserDTO user,
                        final BindingResult binding,
                        Model model,
                        RedirectAttributes redirectAttributes,
                        HttpSession session) {
        if (binding.hasErrors()) {
            log.error("Error logging user in: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "user", binding);
            return "redirect:signin";
        }
        try {
            String username = user.getUsername();
            String password = user.getPassword();
            user = loginDAO.fromEntityToDTO(userService.login(username, password).orElse(null));
            if (user == null) {
                String errors = "Invalid user credentials.";
                redirectAttributes.addAttribute("errors", errors);
                return "redirect:signin";
            }

            var loggedInUser = userService.getUserById(user.getUserId()).orElseThrow();
            session.setAttribute("user", loggedInUser);
            return "redirect:/home";
        } catch (Exception e) {
            if (!model.containsAttribute("user")) {
                model.addAttribute("user", user);
            }
            return "redirect:signin";
        }
    }

    @PostMapping("/signup")
    public String register(@Valid @ModelAttribute RegisterUserDTO user,
                           final BindingResult binding,
                           Model model,
                           RedirectAttributes redirectAttributes) {
        if (binding.hasErrors()) {
            log.error("Error registering user: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "user", binding);
            return "redirect:signup";
        }
        try {
            RegisterUserDTO registeredUser = registerDAO.fromEntityToDTO(userService.register(user).orElse(null));

            if (registeredUser == null) {
                String errors = "Invalid user registration data.";
                redirectAttributes.addFlashAttribute("errors", errors);

                if (!redirectAttributes.containsAttribute("user")) {
                    redirectAttributes.addFlashAttribute("user", user);
                }
                return "redirect:signup";
            }

            if (!redirectAttributes.containsAttribute("user")) {
                redirectAttributes.addFlashAttribute("user", user);
            }
            return "redirect:signin";
        } catch (Exception e) {
            if (!redirectAttributes.containsAttribute("user")) {
                redirectAttributes.addFlashAttribute("user", user);
            }
            return "redirect:signup";
        }
    }
}