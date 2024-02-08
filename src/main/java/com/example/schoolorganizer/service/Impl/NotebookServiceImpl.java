package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.dto.NotebookDTO;
import com.example.schoolorganizer.model.Notebook;
import com.example.schoolorganizer.repository.NotebookRepository;
import com.example.schoolorganizer.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotebookServiceImpl implements NotebookService {
    private final NotebookRepository notebookRepo;

    @Autowired
    public NotebookServiceImpl(NotebookRepository notebookRepo) {
        this.notebookRepo = notebookRepo;
    }

    @Override
    public List<Notebook> getAllNotebooksByUserId(Long id) {
        return notebookRepo.findAllByCreatedByUserId(id);
    }

    @Override
    public Optional<Notebook> getNotebookByNotebookId(Long userId, Long notebookId) {
        return Optional.empty();
    }

    @Override
    public Optional<Notebook> createNewNotebook(NotebookDTO notebookDTO) {
        return Optional.empty();
    }

    @Override
    public Optional<Notebook> updateNotebookById(Long id, NotebookDTO notebookDTO) {
        return Optional.empty();
    }

    @Override
    public void deleteNotebookById(Long id) {

    }
}