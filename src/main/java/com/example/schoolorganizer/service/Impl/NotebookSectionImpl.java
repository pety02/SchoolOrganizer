package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.model.NotebookSection;
import com.example.schoolorganizer.repository.NotebookSectionRepository;
import com.example.schoolorganizer.service.NotebookSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotebookSectionImpl implements NotebookSectionService {
    private final NotebookSectionRepository notebookSectionRepo;

    @Autowired
    public NotebookSectionImpl(NotebookSectionRepository notebookSectionRepo) {
        this.notebookSectionRepo = notebookSectionRepo;
    }

    @Override
    public List<NotebookSection> getAllNotebookSectionsByNotebookId(Long id) {
        return notebookSectionRepo.findAllByAddedInNotebookNotebookId(id);
    }
}
