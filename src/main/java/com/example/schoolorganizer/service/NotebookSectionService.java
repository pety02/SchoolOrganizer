package com.example.schoolorganizer.service;

import java.util.*;

import com.example.schoolorganizer.dto.NotebookSectionDTO;
import com.example.schoolorganizer.model.NotebookSection;
import jakarta.transaction.Transactional;

public interface NotebookSectionService {
    List<NotebookSection> getAllNotebookSectionsByNotebookId(Long id);

    Optional<NotebookSection> getNotebookSectionByNotebookIdAndSectionId(Long notebookId, Long sectionId);

    Optional<NotebookSection> createNewNotebookSectionByNotebookId(Long id, NotebookSectionDTO notebookSectionDTO);

    Optional<NotebookSection> updateNotebookSectionById(Long id, NotebookSectionDTO notebookSectionDTO);

    void deleteNotebookSectionById(Long id);
}