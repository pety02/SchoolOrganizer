package com.example.schoolorganizer.service;

import com.example.schoolorganizer.dto.FileDTO;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface FileService {
    List<FileDTO> getAllFilesByTaskId(Long id);

    Optional<FileDTO> saveFileInTask(FileDTO fileDTO, Long taskId)
            throws NoSuchElementException;

    Optional<FileDTO> updateFileInTask(Long id, FileDTO fileDTO, Long taskId)
            throws NoSuchElementException;

    void deleteFile(Long id);
}