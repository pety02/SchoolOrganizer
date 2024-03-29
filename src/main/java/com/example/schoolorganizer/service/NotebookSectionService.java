package com.example.schoolorganizer.service;

import java.util.*;

import com.example.schoolorganizer.dto.NotebookSectionDTO;
import com.example.schoolorganizer.model.NotebookSection;
import jakarta.transaction.Transactional;

public interface NotebookSectionService {
    List<NotebookSectionDTO> getAllNotebookSectionsByNotebookId(Long id);

    Optional<NotebookSectionDTO> getNotebookSectionByNotebookIdAndSectionId(Long notebookId, Long sectionId)
            throws NoSuchElementException;

    Optional<NotebookSectionDTO> createNewNotebookSectionByNotebookId(Long id, NotebookSectionDTO notebookSectionDTO)
            throws NoSuchElementException, IllegalArgumentException;

    Optional<NotebookSectionDTO> updateNotebookSectionById(Long id, NotebookSectionDTO notebookSectionDTO)
            throws NoSuchElementException, IllegalArgumentException;

    void deleteNotebookSectionById(Long id);
}