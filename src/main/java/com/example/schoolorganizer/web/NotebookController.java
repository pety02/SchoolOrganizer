package com.example.schoolorganizer.web;

import com.example.schoolorganizer.dto.NotebookDTO;
import com.example.schoolorganizer.dto.NotebookSectionDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.security.UserLoggedInValidator;
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

import java.time.LocalDate;
import java.util.List;

import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

/**
 * This class describes a NotebookController. A class that manage with
 * users' notebooks.
 *
 * @author Petya Licheva
 */
@Controller
@Slf4j
public class NotebookController {
    private final NotebookService notebookService;
    private final NotebookSectionService notebookSectionService;

    /**
     * General purpose constructor of the NotebookController class.
     *
     * @param notebookService        the notebook service object.
     * @param notebookSectionService the notebook sections service object.
     */
    @Autowired
    public NotebookController(NotebookService notebookService,
                              NotebookSectionService notebookSectionService) {
        this.notebookService = notebookService;
        this.notebookSectionService = notebookSectionService;
    }

    /**
     * This method gets user's all notebooks.
     *
     * @param httpSession the http session object.
     * @param model       the model object.
     * @return a html page via the result of the http request.
     */
    @GetMapping("/notebooks")
    public String getAllUserNotebooks(HttpSession httpSession, Model model) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        List<NotebookDTO> userNotebookDTOs = notebookService.getAllNotebooksByUserId(loggedUser.getUserId());
        model.addAttribute("notebooks", userNotebookDTOs);
        httpSession.setAttribute("foundEvents", null);
        return "notebooks";
    }

    /**
     * This method view a definite notebook's details.
     *
     * @param httpSession the http session object.
     * @param model       the http model object.
     * @param id          the notebook's id.
     * @return a html page via the result of the http request.
     */
    @GetMapping("/notebooks/{id}")
    public String viewNotebook(HttpSession httpSession,
                               Model model,
                               @PathVariable Long id) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        try {
            List<NotebookSectionDTO> notebookSectionsDTOs = notebookSectionService.getAllNotebookSectionsByNotebookId(id);
            model.addAttribute("currentNotebookSections", notebookSectionsDTOs);
            return "notebook";
        } catch (Exception e) {
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/notebooks";
        }
    }

    /**
     * This method opens a new notebook form.
     *
     * @param httpSession the http session object.
     * @param model       the model object.
     * @return a html page via the result of the http request.
     */
    @GetMapping("/notebooks/create")
    public String getNewNotebookForm(HttpSession httpSession, Model model) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        model.addAttribute("createdNotebook", new NotebookDTO());
        return "create-notebook";
    }

    /**
     * This method creates a new notebook.
     *
     * @param notebookDTO        the notebook dto object.
     * @param binding            the binding result object.
     * @param model              the model object.
     * @param redirectAttributes the redirect attributes object.
     * @param httpSession        the http session object.
     * @return a html page via the result of the http request.
     */
    @PostMapping("/notebooks/create")
    public String creteNewNotebook(@Valid @ModelAttribute NotebookDTO notebookDTO,
                                   BindingResult binding,
                                   Model model,
                                   RedirectAttributes redirectAttributes,
                                   HttpSession httpSession) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        if (binding.hasErrors()) {
            log.error("Error creating new notebook: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("createdNotebook", notebookDTO);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "createdNotebook", binding);
            return "redirect:/notebooks/create";
        }
        try {
            notebookDTO.setCreatedBy(loggedUser);
            NotebookDTO createdNotebook = notebookService.createNewNotebook(notebookDTO).orElseThrow();
            if (!redirectAttributes.containsAttribute("createdNotebook")) {
                redirectAttributes.addFlashAttribute("createdNotebook", createdNotebook);
            }
            model.addAttribute("createdNotebook", createdNotebook);
            return "redirect:/notebooks";
        } catch (Exception e) {
            model.addAttribute("createdNotebook", new NotebookDTO());
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/notebooks/create";
        }
    }

    /**
     * This method opens update a definite notebook's form.
     *
     * @param id          the notebook's id.
     * @param httpSession the http session object.
     * @param model       the model object.
     * @return a html page via the result of the http request.
     */
    @GetMapping("/notebooks/update/{id}")
    public String getUpdateNotebookForm(@PathVariable Long id,
                                        HttpSession httpSession,
                                        Model model) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        try {
            NotebookDTO notebookDTO = notebookService
                    .getNotebookByNotebookId(loggedUser.getUserId(), id)
                    .orElseThrow();
            model.addAttribute("updatedNotebook", notebookDTO);
            return "update-notebook";
        } catch (Exception e) {
            model.addAttribute("updatedNotebook", new NotebookDTO());
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/notebooks";
        }
    }

    /**
     * This method updates a definite notebook.
     *
     * @param id                 the notebook's id.
     * @param notebookDTO        the notebook dto object.
     * @param binding            the binding result object.
     * @param model              the model object.
     * @param redirectAttributes the redirect attributes object.
     * @param httpSession        the http session object.
     * @return a html page via the result of the http request.
     */
    @PostMapping("/notebooks/update/{id}")
    public String updateNotebook(@PathVariable Long id,
                                 @Valid @ModelAttribute NotebookDTO notebookDTO,
                                 BindingResult binding,
                                 Model model,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession httpSession) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        if (binding.hasErrors()) {
            log.error("Error updating notebook: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute("updatedNotebook", notebookDTO);
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "updatedNotebook", binding);
            model.addAttribute("errors", "");
            model.addAttribute("updatedNotebook", new NotebookDTO());
            return "redirect:/notebooks/update/{id}";
        }
        try {
            notebookDTO.setNotebookId(id);
            notebookDTO.setCreatedBy(loggedUser);
            NotebookDTO updatedNotebook = notebookService
                    .updateNotebookById(id, notebookDTO)
                    .orElseThrow();
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
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/notebooks/update/{id}";
        }
    }

    /**
     * This method deletes a definite notebook via its id.
     *
     * @param id          the notebook's id.
     * @param httpSession the http session object.
     * @return a html page via the result of the http request.
     */
    @GetMapping("/notebooks/delete/{id}")
    public String deleteNotebook(@PathVariable Long id,
                                 HttpSession httpSession) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");

        notebookService.deleteNotebookById(id);
        return "redirect:/notebooks";
    }
}