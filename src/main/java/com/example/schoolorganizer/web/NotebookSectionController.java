package com.example.schoolorganizer.web;

import com.example.schoolorganizer.dto.NotebookDTO;
import com.example.schoolorganizer.dto.NotebookSectionDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.security.UserLoggedInValidator;
import com.example.schoolorganizer.service.NotebookSectionService;
import com.example.schoolorganizer.service.NotebookService;
import jakarta.servlet.http.HttpSession;
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

import java.time.LocalDate;
import java.util.NoSuchElementException;

import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

@Controller
@Slf4j
public class NotebookSectionController {
    private final NotebookSectionService notebookSectionService;
    private final NotebookService notebookService;

    @Autowired
    public NotebookSectionController(NotebookSectionService notebookSectionService,
                                     NotebookService notebookService) {
        this.notebookSectionService = notebookSectionService;
        this.notebookService = notebookService;
    }

    @GetMapping("/notebooks/{id}/create")
    public String getNewNotebookSectionForm(@PathVariable Long id,
                                            HttpSession httpSession,
                                            Model model) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        model.addAttribute("newNotebookSection", new NotebookSectionDTO());
        return "new-notebook-section";
    }

    @PostMapping("/notebooks/{id}/create")
    public String createNewNotebookSection(HttpSession httpSession,
                                           Model model,
                                           @ModelAttribute NotebookSectionDTO createdSectionDTO,
                                           RedirectAttributes redirectAttributes,
                                           BindingResult binding,
                                           @PathVariable Long id) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        if (binding.hasErrors()) {
            log.error("Error creating new notebooks section: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("createdNotebookSection", createdSectionDTO);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "createdNotebookSection", binding);
            return "redirect:/notebooks/{id}/create";
        }
        try {
            NotebookSectionDTO createdSection = notebookSectionService.createNewNotebookSectionByNotebookId(id, createdSectionDTO).orElseThrow();
            if (!redirectAttributes.containsAttribute("createdNotebookSection")) {
                redirectAttributes.addFlashAttribute("createdNotebookSection", createdSection);
            }
            model.addAttribute("createdNotebookSection", createdSection);
            return "redirect:/notebooks/{id}";
        } catch (Exception e) {
            if (!redirectAttributes.containsAttribute("createdNotebookSection")) {
                redirectAttributes.addFlashAttribute("createdNotebookSection", createdSectionDTO);
            }
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/notebooks/{id}/create";
        }
    }

    @GetMapping("/notebooks/{id}/update/{sectionId}")
    public String getUpdateNotebookSectionForm(HttpSession httpSession,
                                               Model model,
                                               @ModelAttribute NotebookSectionDTO notebookSectionDTO,
                                               @PathVariable Long id,
                                               @PathVariable Long sectionId) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        try {
            NotebookSectionDTO updatedNotebookSectionDTO = notebookSectionService
                    .getNotebookSectionByNotebookIdAndSectionId(id, sectionId)
                    .orElseThrow();
            model.addAttribute("updatedNotebookSection", updatedNotebookSectionDTO);
            return "update-notebook-section";
        } catch (NoSuchElementException e) {
            model.addAttribute("updatedNotebookSection", new NotebookSectionDTO());
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/notebooks/{id}";
        }
    }

    @PostMapping("/notebooks/{id}/update/{sectionId}")
    public String updateNotebookSection(HttpSession httpSession,
                                        Model model,
                                        RedirectAttributes redirectAttributes,
                                        @ModelAttribute NotebookSectionDTO updatedSectionDTO,
                                        BindingResult binding,
                                        @PathVariable Long id,
                                        @PathVariable Long sectionId) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        if (binding.hasErrors()) {
            log.error("Error updating notebook section: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("updatedNotebookSection", updatedSectionDTO);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "updatedNotebookSection", binding);
            model.addAttribute("updatedNotebookSection", new NotebookSectionDTO());
            return "redirect:/notebooks/{id}/update/{sectionId}";
        }
        try {
            updatedSectionDTO.setNotebookSectionId(sectionId);
            NotebookDTO notebook = notebookService.getNotebookByNotebookId(loggedUser.getUserId(), id).orElseThrow();
            notebook.getSections().add(updatedSectionDTO);
            notebookService.updateNotebookById(notebook.getNotebookId(), notebook);
            NotebookSectionDTO updatedNotebookSection = notebookSectionService.updateNotebookSectionById(sectionId, updatedSectionDTO).orElseThrow();
            if (!redirectAttributes.containsAttribute("updatedNotebookSection")) {
                redirectAttributes.addFlashAttribute("updatedNotebookSection", updatedSectionDTO);
            }
            model.addAttribute("updatedNotebookSection", updatedNotebookSection);
            return "redirect:/notebooks/{id}";
        } catch (Exception e) {
            if (!redirectAttributes.containsAttribute("updatedNotebookSection")) {
                redirectAttributes.addFlashAttribute("updatedNotebookSection", updatedSectionDTO);
            }
            model.addAttribute("updatedNotebookSection", new NotebookSectionDTO());
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/notebooks/{id}/update/{sectionId}";
        }
    }

    @GetMapping("/notebooks/{id}/delete/{sectionId}")
    public String deleteNotebookSection(HttpSession httpSession,
                                        @PathVariable Long id,
                                        @PathVariable Long sectionId) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        notebookSectionService.deleteNotebookSectionById(sectionId);
        return "redirect:/notebooks/{id}";
    }
}