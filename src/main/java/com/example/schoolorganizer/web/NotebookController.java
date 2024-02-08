package com.example.schoolorganizer.web;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.NotebookDTO;
import com.example.schoolorganizer.dto.NotebookSectionDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.model.Notebook;
import com.example.schoolorganizer.model.NotebookSection;
import com.example.schoolorganizer.model.User;
import com.example.schoolorganizer.service.NotebookSectionService;
import com.example.schoolorganizer.service.NotebookService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

@Controller
@Slf4j
public class NotebookController {
    private final NotebookService notebookService;
    private final NotebookSectionService notebookSectionService;
    private final IAdapter<Notebook, NotebookDTO> notebookAdapter;
    private final IAdapter<NotebookSection, NotebookSectionDTO> notebookSectionAdapter;
    private final IAdapter<User, UserDTO> userAdapter;

    @Autowired
    public NotebookController(NotebookService notebookService, NotebookSectionService notebookSectionService, IAdapter<Notebook, NotebookDTO> notebookAdapter, IAdapter<NotebookSection, NotebookSectionDTO> notebookSectionAdapter, IAdapter<User, UserDTO> userAdapter) {
        this.notebookService = notebookService;
        this.notebookSectionService = notebookSectionService;
        this.notebookAdapter = notebookAdapter;
        this.notebookSectionAdapter = notebookSectionAdapter;
        this.userAdapter = userAdapter;
    }

    @GetMapping("/notebooks")
    public String getAllUserNotebooks(HttpSession httpSession, Model model) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser != null) {
            List<Notebook> userNotebookEntities = notebookService.getAllNotebooksByUserId(loggedUser.getUserId());
            List<NotebookDTO> userNotebookDTOs = new ArrayList<>();
            for (var n : userNotebookEntities) {
                userNotebookDTOs.add(notebookAdapter.fromEntityToDTO(n));
            }
            model.addAttribute("notebooks", userNotebookDTOs);
            return "notebooks";
        }

        return "redirect:/signin";
    }

    @GetMapping("/notebooks/{id}")
    public String viewNotebook(HttpSession httpSession,
                               Model model,
                               @PathVariable Long id) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser != null) {
            try {
                List<NotebookSection> notebookSections = notebookSectionService.getAllNotebookSectionsByNotebookId(id);
                List<NotebookSectionDTO> notebookSectionsDTOs = new ArrayList<>();
                for (var section : notebookSections) {
                    notebookSectionsDTOs.add(notebookSectionAdapter.fromEntityToDTO(section));
                }
                model.addAttribute("currentNotebookSections", notebookSectionsDTOs);
                return "notebook";
            } catch (NoSuchElementException e) {
                System.out.println(e.getMessage());
                return "redirect:/notebooks";
            }
        }

        return "redirect:/signin";
    }

    @GetMapping("/notebooks/create")
    public String getNewNotebookForm(HttpSession httpSession, Model model) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser != null) {
            model.addAttribute("createdNotebook", new NotebookDTO());
            return "create-notebook";
        }

        return "redirect:/signin";
    }

    @PostMapping("/notebooks/create")
    public String creteNewNotebook(@Valid @ModelAttribute NotebookDTO notebookDTO,
                                   BindingResult binding,
                                   Model model,
                                   RedirectAttributes redirectAttributes,
                                   HttpSession httpSession) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser == null) {
            return "redirect:/signin";
        }
        if (binding.hasErrors()) {
            log.error("Error creating new notebook: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("createdNotebook", notebookDTO);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "createdNotebook", binding);
            return "redirect:/notebooks";
        }
        try {
            notebookDTO.setCreatedBy(userAdapter.fromEntityToDTO(loggedUser));
            NotebookDTO createdNotebook = notebookAdapter.fromEntityToDTO(notebookService.createNewNotebook(notebookDTO).orElseThrow());
            if (createdNotebook == null) {
                String errors = "Invalid new notebook data.";
                redirectAttributes.addFlashAttribute("errors", errors);

                if (!redirectAttributes.containsAttribute("createdNotebook")) {
                    redirectAttributes.addFlashAttribute("createdNotebook", notebookDTO);
                }
                return "redirect:/notebooks";
            }
            if (!redirectAttributes.containsAttribute("createdNotebook")) {
                redirectAttributes.addFlashAttribute("createdNotebook", createdNotebook);
            }
            model.addAttribute("createdNotebook", createdNotebook);
            return "redirect:/notebooks";
        } catch (Exception e) {
            if (!redirectAttributes.containsAttribute("createdNotebook")) {
                redirectAttributes.addFlashAttribute("createdNotebook", notebookDTO);
            }
            return "redirect:/notebooks";
        }
    }

    @GetMapping("/notebooks/update/{id}")
    public String getUpdateNotebookForm(@PathVariable Long id,
                                        HttpSession httpSession,
                                        Model model) {
        User loggedUser = (User) httpSession.getAttribute("user");
        try {
            if (loggedUser != null) {
                Notebook notebookEntity = notebookService.getNotebookByNotebookId(loggedUser.getUserId(), id).orElseThrow();
                NotebookDTO notebookDTO = notebookAdapter.fromEntityToDTO(notebookEntity);

                model.addAttribute("updatedNotebook", notebookDTO);
                return "update-notebook";
            }
            model.addAttribute("updatedNotebook", new NotebookDTO());
            return "redirect:/notebooks";
        } catch (NoSuchElementException e) {
            model.addAttribute("updatedNotebook", new NotebookDTO());
            return "redirect:/notebooks";
        }
    }

    @PostMapping("/notebooks/update/{id}")
    public String updateNotebook(@PathVariable Long id,
                                 @Valid @ModelAttribute NotebookDTO notebookDTO,
                                 BindingResult binding,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession httpSession) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser == null) {
            model.addAttribute("updatedNotebook", new NotebookDTO());
            return "redirect:/signin";
        }
        if (binding.hasErrors()) {
            log.error("Error updating notebook: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("updatedNotebook", notebookDTO);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "updatedNotebook", binding);
            model.addAttribute("updatedNotebook", new NotebookDTO());
            return "redirect:/notebooks";
        }
        try {
            notebookDTO.setNotebookId(id);
            notebookDTO.setCreatedBy(userAdapter.fromEntityToDTO(loggedUser));
            NotebookDTO updatedNotebook = notebookAdapter.fromEntityToDTO(notebookService.updateNotebookById(id, notebookDTO).orElseThrow());
            if (updatedNotebook == null) {
                String errors = "Invalid updating notebook data.";
                redirectAttributes.addFlashAttribute("errors", errors);

                if (!redirectAttributes.containsAttribute("updatedNotebook")) {
                    redirectAttributes.addFlashAttribute("updatedNotebook", notebookDTO);
                }
                model.addAttribute("updatedNotebook", new NotebookDTO());
                return "redirect:/notebooks";
            }
            if (!redirectAttributes.containsAttribute("updatedNotebook")) {
                redirectAttributes.addFlashAttribute("updatedNotebook", updatedNotebook);
            }
            model.addAttribute("updatedNotebook", updatedNotebook);
            return "redirect:/notebooks";
        } catch (Exception e) {
            if (!redirectAttributes.containsAttribute("updatedNotebook")) {
                redirectAttributes.addFlashAttribute("updatedNotebook", notebookDTO);
            }
            model.addAttribute("updatedNotebook", new NotebookDTO());
            return "redirect:/notebooks";
        }
    }

    @GetMapping("/notebooks/delete/{id}")
    public String deleteNotebook(@PathVariable Long id,
                                 HttpSession httpSession) {
        User loggedUser = (User) httpSession.getAttribute("user");
        if (loggedUser == null) {
            return "redirect:/signin";
        }

        notebookService.deleteNotebookById(id);
        return "redirect:/notebooks";
    }
}