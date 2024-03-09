package com.example.schoolorganizer.web;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.FileDTO;
import com.example.schoolorganizer.model.File;
import com.example.schoolorganizer.security.UserLoggedInValidator;
import com.example.schoolorganizer.service.FileService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

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

    @GetMapping("/tasks/{id}/delete/{fileId}")
    public String deleteFile(@PathVariable("id") Long id, @PathVariable("fileId") Long fileId, HttpSession httpSession) {
        if (!UserLoggedInValidator.hasUserLoggedIn(httpSession)) {
            return "redirect:/signin";
        }

        fileService.deleteFile(id, fileId);
        return "redirect:/tasks/{id}";
    }
}