package com.example.schoolorganizer.web;

import com.example.schoolorganizer.dao.IDAO;
import com.example.schoolorganizer.dto.UpdateUserDataDTO;
import com.example.schoolorganizer.model.User;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.NoSuchElementException;
import java.util.Objects;

import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

@Controller
@Slf4j
public class ProfileController {
    private final IDAO<User, UpdateUserDataDTO> userDAO;
    private final UserService userService;

    @Autowired
    public ProfileController(IDAO<User, UpdateUserDataDTO> userDAO, UserService userService) {
        this.userDAO = userDAO;
        this.userService = userService;
    }


    @GetMapping("/profile")
    public String getProfileForm(HttpSession httpSession,
                                 Model model) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser == null) {
            return "redirect:/signin";
        }

        model.addAttribute("updateUser", userDAO.fromEntityToDTO(loggedUser));
        return "profile";
    }

    @PostMapping("/profile/update")
    public String updateProfile(@Valid @ModelAttribute UpdateUserDataDTO updatedUser,
                                BindingResult binding,
                                Model model,
                                RedirectAttributes redirectAttributes,
                                HttpSession httpSession) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser == null) {
            model.addAttribute("updateUser", new UpdateUserDataDTO());
            return "redirect:/signin";
        }
        if (binding.hasErrors()) {
            log.error("Error updating user profile: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("updateUser", updatedUser);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "updateUser", binding);
            model.addAttribute("updateUser", new UpdateUserDataDTO());
            return "redirect:/profile";
        }
        try {
            User updatedEntity = userService.updateData(loggedUser.getUserId(), updatedUser).orElseThrow();
            UpdateUserDataDTO updatedDTO = userDAO.fromEntityToDTO(updatedEntity);
            model.addAttribute("updateUser", updatedDTO);
            return "redirect:/signin";
        } catch (NoSuchElementException e) {
            model.addAttribute("updateUser", new UpdateUserDataDTO());
            return "redirect:/profile";
        }
    }
}