package com.example.schoolorganizer.web;

import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.dto.RegisterUserDTO;
import com.example.schoolorganizer.security.UserLoggedInValidator;
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

/**
 * This class describes an AuthController. A controller class
 * that is used to manage with users' authentication and authorization.
 *
 * @author Petya Licheva
 */
@Controller
@Slf4j
public class AuthController {
    private final UserServiceImpl userService;

    /*private void authenticate(LoginUserDTO user, AuthenticationManager authManager) {
        AuthenticationManager authenticationManager = authManager;
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        authentication.setAuthenticated(true);
    }*/

    /**
     * General purpose constructor of AuthController class.
     *
     * @param userService the user's service object.
     */
    @Autowired
    public AuthController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /**
     * This method login a user in the application if its
     * credentials are in valid state and if in the database
     * there is a user with them.
     *
     * @param user               the user credentials object.
     * @param binding            the binding result object.
     * @param model              the model object.
     * @param redirectAttributes the redirect attributes object.
     * @param session            the http session object.
     * @return a html page via its title or redirect to other page
     * in case of login problems.
     */
    @PostMapping("/signin")
    public String login(@Valid @ModelAttribute LoginUserDTO user,
                        final BindingResult binding,
                        Model model,
                        RedirectAttributes redirectAttributes,
                        HttpSession session) {
        if (UserLoggedInValidator.hasUserLoggedIn(session)) {
            session.invalidate();
        }
        if (binding.hasErrors()) {
            log.error("Error logging user in: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "user", binding);
            return "redirect:signin";
        }
        try {
            String username = user.getUsername();
            String password = user.getPassword();
            user = userService.login(username, password).orElse(null);
            if (user == null) {
                String errors = "Invalid user credentials.";
                redirectAttributes.addAttribute("errors", errors);
                return "redirect:signin";
            }

            var loggedInUser = userService.getUserById(user.getUserId()).orElseThrow();
            session.setAttribute("user", loggedInUser);

            //authenticate(user);
            return "redirect:/home";
        } catch (Exception e) {
            if (!model.containsAttribute("user")) {
                model.addAttribute("user", user);
            }
            return "redirect:signin";
        }
    }

    /**
     * This method register a user in the application if its
     * credentials are in valid state and if in the database
     * there is not a user with them.
     *
     * @param user               the user credentials object.
     * @param binding            the binding result object.
     * @param model              the model object.
     * @param redirectAttributes the redirect attributes object.
     * @param session            the http session object.
     * @return a html page via its title or redirect to other page
     * in case of registration problems.
     */
    @PostMapping("/signup")
    public String register(@Valid @ModelAttribute RegisterUserDTO user,
                           final BindingResult binding,
                           Model model,
                           RedirectAttributes redirectAttributes,
                           HttpSession session) {
        if (UserLoggedInValidator.hasUserLoggedIn(session)) {
            session.invalidate();
        }
        if (binding.hasErrors()) {
            log.error("Error registering user: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("user", user);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "user", binding);
            return "redirect:signup";
        }
        try {
            RegisterUserDTO registeredUser = userService.register(user).orElse(null);

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