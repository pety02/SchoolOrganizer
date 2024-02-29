package com.example.schoolorganizer.web;

import com.example.schoolorganizer.dto.FileDTO;
import com.example.schoolorganizer.security.UserLoggedInValidator;
import com.example.schoolorganizer.service.FileService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 *
 */
@Controller
public class FileInTaskController {
    private final FileService fileService;

    /**
     * @param fileService
     */
    @Autowired
    public FileInTaskController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * @param taskId
     * @param model
     * @param httpSession
     * @return
     */
    @GetMapping("/tasks/{id}/files")
    public String getAllUserTaskFiles(@PathVariable("id") Long taskId,
                                      Model model,
                                      HttpSession httpSession) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }

        List<FileDTO> files = fileService.getAllFilesByTaskId(taskId);
        model.addAttribute("taskFiles", files);
        // TODO: to think what exactly to return.
        return "";
    }

    /**
     * @return
     */
    @GetMapping("/tasks/{id}/files/create")
    public String getNewTaskFile() {
        // TODO: to implement it.
        return "";
    }

    /**
     * @return
     */
    @PostMapping("/tasks/{id}/files/create")
    public String createNewTaskFile() {
        // TODO: to implement it.
        return "";
    }

    /**
     * @return
     */
    @GetMapping("/tasks/{id}/files/{fileId}/update")
    public String getUpdateTaskFile() {
        // TODO: to implement it.
        return "";
    }

    /**
     * @return
     */
    @PostMapping("/tasks/{id}/files/{fileId}/update")
    public String updateTaskFile() {
        // TODO: to implement it.
        return "";
    }

    /**
     * @return
     */
    @GetMapping("tasks/{id}/files/{fileId}/delete")
    public String deleteTaskFileByid() {
        // TODO: to implement it.
        return "";
    }
}