package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.NotebookSectionDTO;
import com.example.schoolorganizer.model.Notebook;
import com.example.schoolorganizer.model.NotebookSection;
import com.example.schoolorganizer.repository.NotebookRepository;
import com.example.schoolorganizer.repository.NotebookSectionRepository;
import com.example.schoolorganizer.service.NotebookSectionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Slf4j
public class NotebookSectionImpl implements NotebookSectionService {
    private final NotebookSectionRepository notebookSectionRepo;
    private final IAdapter<NotebookSection, NotebookSectionDTO> notebookSectionAdapter;
    private final NotebookRepository notebookRepo;

    @Autowired
    public NotebookSectionImpl(NotebookSectionRepository notebookSectionRepo, IAdapter<NotebookSection, NotebookSectionDTO> notebookSectionAdapter, NotebookRepository notebookRepo) {
        this.notebookSectionRepo = notebookSectionRepo;
        this.notebookSectionAdapter = notebookSectionAdapter;
        this.notebookRepo = notebookRepo;
    }

    @Override
    public List<NotebookSection> getAllNotebookSectionsByNotebookId(Long id) {
        return notebookSectionRepo.findAllByAddedInNotebookNotebookId(id);
    }

    @Override
    public Optional<NotebookSection> getNotebookSectionByNotebookIdAndSectionId(Long notebookId, Long sectionId) {
        return notebookSectionRepo.findByAddedInNotebook_NotebookIdAndNotebookSectionId(notebookId, sectionId);
    }

    @Override
    public Optional<NotebookSection> createNewNotebookSectionByNotebookId(Long id, NotebookSectionDTO notebookSectionDTO) {
        try {
            Notebook notebook = notebookRepo.findById(id).orElseThrow();
            System.out.println("notebook found");
            NotebookSection created = notebookSectionAdapter.fromDTOToEntity(notebookSectionDTO);
            created.setAddedInNotebook(notebook);
            return Optional.of(notebookSectionRepo.save(created));
        } catch (NoSuchElementException e) {
            log.error(LocalDate.now() + ": " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public Optional<NotebookSection> updateNotebookSectionById(Long id, NotebookSectionDTO notebookSectionDTO) {
        try {
            NotebookSection notebookSection = notebookSectionRepo.findById(id).orElseThrow();
            Notebook notebook = notebookSection.getAddedInNotebook();
            NotebookSection updatedSection = notebookSectionAdapter.fromDTOToEntity(notebookSectionDTO);
            updatedSection.setNotebookSectionId(notebookSection.getNotebookSectionId());
            updatedSection.setAddedInNotebook(notebook);
            return Optional.of(notebookSectionRepo.save(updatedSection));
        } catch (NoSuchElementException e) {
            log.error(LocalDate.now() + ": " + e.getMessage());
            return Optional.empty();
        }
    }

    @Override
    public void deleteNotebookSectionById(Long id) {
        notebookSectionRepo.deleteById(id);
    }
}