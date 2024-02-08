package com.example.schoolorganizer.service;

import java.util.*;

import com.example.schoolorganizer.model.NotebookSection;

public interface NotebookSectionService {
    List<NotebookSection> getAllNotebookSectionsByNotebookId(Long id);
}