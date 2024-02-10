package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.NotebookDTO;
import com.example.schoolorganizer.model.Notebook;
import com.example.schoolorganizer.repository.NotebookRepository;
import com.example.schoolorganizer.service.NotebookService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class NotebookServiceImpl implements NotebookService {
    private final NotebookRepository notebookRepo;
    private final IAdapter<Notebook, NotebookDTO> notebookAdapter;

    @Autowired
    public NotebookServiceImpl(NotebookRepository notebookRepo, IAdapter<Notebook, NotebookDTO> notebookAdapter) {
        this.notebookRepo = notebookRepo;
        this.notebookAdapter = notebookAdapter;
    }

    @Override
    public List<Notebook> getAllNotebooksByUserId(Long id) {
        return notebookRepo.findAllByCreatedByUserId(id);
    }

    @Override
    public Optional<Notebook> getNotebookByNotebookId(Long userId, Long notebookId) {
        return notebookRepo.findByCreatedByUserIdAndNotebookId(userId, notebookId);
    }

    @Transactional
    @Override
    public Optional<Notebook> createNewNotebook(NotebookDTO notebookDTO) {
        if (notebookDTO == null) {
            return Optional.empty();
        }

        Notebook notebook = notebookAdapter.fromDTOToEntity(notebookDTO);
        return Optional.of(notebookRepo.save(notebook));
    }

    @Transactional
    @Override
    public Optional<Notebook> updateNotebookById(Long id, NotebookDTO notebookDTO) {
        if (notebookDTO == null) {
            return Optional.empty();
        }
        Notebook notebook = notebookAdapter.fromDTOToEntity(notebookDTO);
        if (notebookRepo.existsById(id)) {
            try {
                return Optional.of(notebookRepo.save(notebook));
            } catch (NoSuchElementException e) {
                log.error(LocalDate.now() + ": " + e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    @Transactional
    @Override
    public void deleteNotebookById(Long id) {
        notebookRepo.deleteById(id);
    }
}