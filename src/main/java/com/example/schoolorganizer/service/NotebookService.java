package com.example.schoolorganizer.service;

import java.util.*;

import com.example.schoolorganizer.dto.NotebookDTO;
import com.example.schoolorganizer.model.Notebook;
import jakarta.transaction.Transactional;

public interface NotebookService {
    List<Notebook> getAllNotebooksByUserId(Long id);

    Optional<Notebook> getNotebookByNotebookId(Long userId, Long notebookId);

    Optional<Notebook> createNewNotebook(NotebookDTO notebookDTO);

    Optional<Notebook> updateNotebookById(Long id, NotebookDTO notebookDTO);

    void deleteNotebookById(Long id);
}