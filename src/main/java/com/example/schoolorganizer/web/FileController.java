package com.example.schoolorganizer.web;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.FileDTO;
import com.example.schoolorganizer.dto.TaskDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.model.File;
import com.example.schoolorganizer.security.UserLoggedInValidator;
import com.example.schoolorganizer.service.FileService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.validation.BindingResult.MODEL_KEY_PREFIX;

@Controller
@Slf4j
public class FileController {
    private final FileService fileService;
    private final IAdapter<File, FileDTO> fileAdapter;

    @Autowired
    public FileController(FileService fileService, IAdapter<File, FileDTO> fileAdapter) {
        this.fileService = fileService;
        this.fileAdapter = fileAdapter;
    }

    @PostMapping("/tasks/{id}/add-file")
    public String addFile(@RequestParam Long id,
                          @RequestParam("file") MultipartFile file,
                          @RequestParam("fileName") String fileArtificialName,
                          BindingResult binding,
                          Model model,
                          RedirectAttributes redirectAttributes,
                          HttpSession httpSession) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }
        UserDTO loggedUser = (UserDTO) httpSession.getAttribute("user");
        if (binding.hasErrors()) {
            log.error("Error creating new task: {}", binding.getAllErrors());
            redirectAttributes.addFlashAttribute(MODEL_KEY_PREFIX + "createdTask", binding);
            return "redirect:/tasks/create";
        }
        try {
            List<FileDTO> uploadeds = (List<FileDTO>) httpSession.getAttribute("uploadedFiles");
            FileDTO uploaded = fileService.uploadFile(file, id, fileArtificialName);
            if (uploaded != null) {
                uploadeds.add(uploaded);
            } else {
                uploadeds = new ArrayList<>();
                uploadeds.add(uploaded);
                httpSession.setAttribute("uploadedFiles", uploadeds);
            }
            return "redirect:/tasks/create";
        } catch (Exception e) {
            log.error(LocalDate.now() + ": " + e.getMessage());
            return "redirect:/tasks/create";
        }
    }

    @GetMapping("/tasks/{id}/delete/{fileId}")
    public String deleteFile(@PathVariable("id") Long id, @PathVariable("fileId") Long fileId, HttpSession httpSession) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }

        fileService.deleteFile(id, fileId);
        return "redirect:/tasks/{id}";
    }
}