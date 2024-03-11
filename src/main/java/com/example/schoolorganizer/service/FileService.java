package com.example.schoolorganizer.service;

import com.example.schoolorganizer.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

public interface FileService {
    FileDTO uploadFileInTask(MultipartFile file, Long taskId, String fileArtificialName) throws IOException, NoSuchElementException;

    FileDTO uploadFileInNotebookSection(MultipartFile file, Long notebookSectionId, String fileArtificialName) throws IOException, NoSuchElementException;

    List<FileDTO> getAllTaskFiles(Long id);

    void deleteFile(Long id, Long fileId);
}