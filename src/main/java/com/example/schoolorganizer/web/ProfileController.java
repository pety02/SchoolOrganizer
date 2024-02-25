package com.example.schoolorganizer.web;

import com.example.schoolorganizer.dto.UpdateUserDataDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.security.UserLoggedInValidator;
import com.example.schoolorganizer.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

@Controller
@Slf4j
public class ProfileController {
    private final UserService userService;

    private UpdateUserDataDTO fromLoggedUserToUpdateUser(UserDTO loggedUser) {
        UpdateUserDataDTO updatingUser = new UpdateUserDataDTO();
        updatingUser.setUserId(loggedUser.getUserId());
        updatingUser.setOldEmail(loggedUser.getEmail());
        updatingUser.setOldUsername(loggedUser.getUsername());

        return updatingUser;
    }

    @Autowired
    public ProfileController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/profile")
    public String getProfileForm(HttpSession httpSession,
                                 Model model) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");

        UpdateUserDataDTO updatingUser = fromLoggedUserToUpdateUser(loggedUser);
        model.addAttribute("updateUser", updatingUser);
        httpSession.setAttribute("foundEvents", null);
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@Valid @ModelAttribute UpdateUserDataDTO updatedUser,
                                BindingResult binding,
                                Model model,
                                RedirectAttributes redirectAttributes,
                                HttpSession httpSession) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        if (binding.hasErrors()) {
            log.error("Error updating user profile: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("updateUser", updatedUser);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "updateUser", binding);
            model.addAttribute("updateUser", new UpdateUserDataDTO());
            return "redirect:/profile";
        }
        try {
            UpdateUserDataDTO updatedDTO = userService.updateData(loggedUser.getUserId(), updatedUser).orElseThrow();
            model.addAttribute("updateUser", updatedDTO);
            return "redirect:/signin";
        } catch (NoSuchElementException e) {
            model.addAttribute("updateUser", new UpdateUserDataDTO());
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/profile";
        }
    }
}