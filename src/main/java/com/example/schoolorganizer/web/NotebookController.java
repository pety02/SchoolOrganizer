package com.example.schoolorganizer.web;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.NotebookDTO;
import com.example.schoolorganizer.dto.TaskDTO;
import com.example.schoolorganizer.model.Notebook;
import com.example.schoolorganizer.model.Task;
import com.example.schoolorganizer.model.User;
import com.example.schoolorganizer.service.NotebookService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class NotebookController {
    private final NotebookService notebookService;
    private final IAdapter<Notebook, NotebookDTO> notebookAdapter;

    @Autowired
    public NotebookController(NotebookService notebookService, IAdapter<Notebook, NotebookDTO> notebookAdapter) {
        this.notebookService = notebookService;
        this.notebookAdapter = notebookAdapter;
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
    public String viewNotebook(@PathVariable Long id) {
        return "";
    }

    @GetMapping("/notebooks/create")
    public String getNewNotebookForm() {
        return "";
    }

    @PostMapping("/notebooks/create")
    public String creteNewNotebook() {
        return "";
    }

    @GetMapping("/notebooks/update/{id}")
    public String getUpdateNotebookForm(@PathVariable Long id) {
        return "";
    }

    @PostMapping("/notebook/update/{id}")
    public String updateNotebook(@PathVariable Long id) {
        return "";
    }

    @GetMapping("/notebooks/delete/{id}")
    public String deleteNotebook(@PathVariable Long id) {
        return "";
    }
}