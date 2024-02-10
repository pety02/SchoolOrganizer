package com.example.schoolorganizer.service;

import java.util.*;

import com.example.schoolorganizer.dto.NotebookDTO;
import com.example.schoolorganizer.model.Notebook;
import jakarta.transaction.Transactional;

public interface NotebookService {
    List<NotebookDTO> getAllNotebooksByUserId(Long id);

    Optional<NotebookDTO> getNotebookByNotebookId(Long userId, Long notebookId);

    Optional<NotebookDTO> createNewNotebook(NotebookDTO notebookDTO);

    Optional<NotebookDTO> updateNotebookById(Long id, NotebookDTO notebookDTO);

    void deleteNotebookById(Long id);
}