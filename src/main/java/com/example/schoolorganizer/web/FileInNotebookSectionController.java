package com.example.schoolorganizer.web;

import com.example.schoolorganizer.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 */
@Controller
public class FileInNotebookSectionController {
    private final FileService fileService;

    /**
     * @param fileService
     */
    @Autowired
    public FileInNotebookSectionController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * @return
     */
    @GetMapping("/notebooks/{id}/sections/{sectionId}/files")
    public String getAllUserTaskFiles() {
        // TODO: to implement it.
        return "";
    }

    /**
     * @return
     */
    @GetMapping("/notebooks/{id}/sections/{sectionId}/files/create")
    public String getNewTaskFile() {
        // TODO: to implement it.
        return "";
    }

    /**
     * @return
     */
    @PostMapping("/notebooks/{id}/sections/{sectionId}/files/create")
    public String createNewTaskFile() {
        // TODO: to implement it.
        return "";
    }

    /**
     * @return
     */
    @GetMapping("/notebooks/{id}/sections/{sectionId}/files/{fileId}/update")
    public String getUpdateTaskFile() {
        // TODO: to implement it.
        return "";
    }

    /**
     * @return
     */
    @PostMapping("/notebooks/{id}/sections/{sectionId}/files/{fileId}/update")
    public String updateTaskFile() {
        // TODO: to implement it.
        return "";
    }

    /**
     * @return
     */
    @GetMapping("/notebooks/{id}/sections/{sectionId}/files/{fileId}/delete")
    public String deleteTaskFileByid() {
        // TODO: to implement it.
        return "";
    }
}