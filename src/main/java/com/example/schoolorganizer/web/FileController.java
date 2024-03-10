package com.example.schoolorganizer.web;

import com.example.schoolorganizer.security.UserLoggedInValidator;
import com.example.schoolorganizer.service.FileService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class FileController {
    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
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