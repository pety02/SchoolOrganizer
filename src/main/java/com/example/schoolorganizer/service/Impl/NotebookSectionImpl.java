package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.NotebookSectionDTO;
import com.example.schoolorganizer.model.Notebook;
import com.example.schoolorganizer.model.NotebookSection;
import com.example.schoolorganizer.repository.NotebookRepository;
import com.example.schoolorganizer.repository.NotebookSectionRepository;
import com.example.schoolorganizer.service.NotebookSectionService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 *
 */
@Service
@Slf4j
public class NotebookSectionImpl implements NotebookSectionService {
    private final NotebookSectionRepository notebookSectionRepo;
    private final IAdapter<NotebookSection, NotebookSectionDTO> notebookSectionAdapter;
    private final NotebookRepository notebookRepo;

    /**
     * @param notebookSectionRepo
     * @param notebookSectionAdapter
     * @param notebookRepo
     */
    @Autowired
    public NotebookSectionImpl(NotebookSectionRepository notebookSectionRepo, IAdapter<NotebookSection, NotebookSectionDTO> notebookSectionAdapter, NotebookRepository notebookRepo) {
        this.notebookSectionRepo = notebookSectionRepo;
        this.notebookSectionAdapter = notebookSectionAdapter;
        this.notebookRepo = notebookRepo;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public List<NotebookSectionDTO> getAllNotebookSectionsByNotebookId(Long id) {
        List<NotebookSection> notebookSections = notebookSectionRepo.findAllByAddedInNotebookNotebookId(id);
        List<NotebookSectionDTO> notebookSectionDTOs = new ArrayList<>();
        for (var currentSection : notebookSections) {
            NotebookSectionDTO currentSectionDTO = notebookSectionAdapter.fromEntityToDTO(currentSection);
            notebookSectionDTOs.add(currentSectionDTO);
        }

        return notebookSectionDTOs;
    }

    /**
     * @param notebookId
     * @param sectionId
     * @return
     */
    @Override
    public Optional<NotebookSectionDTO> getNotebookSectionByNotebookIdAndSectionId(Long notebookId, Long sectionId) {
        return Optional.of(notebookSectionAdapter
                .fromEntityToDTO(notebookSectionRepo
                        .findByAddedInNotebook_NotebookIdAndNotebookSectionId(notebookId, sectionId)
                        .orElseThrow()));
    }

    /**
     * @param id
     * @param notebookSectionDTO
     * @return
     */
    @Transactional
    @Override
    public Optional<NotebookSectionDTO> createNewNotebookSectionByNotebookId(Long id, NotebookSectionDTO notebookSectionDTO) {
        try {
            Notebook notebook = notebookRepo.findById(id).orElseThrow();
            if (notebookSectionDTO.getDate().isBefore(notebook.getDate())) {
                throw new IllegalArgumentException("Invalid section date. The section date should be after notebook creation date.");
            }
            NotebookSection created = notebookSectionAdapter.fromDTOToEntity(notebookSectionDTO);
            created.setAddedInNotebook(notebook);
            return Optional.of(notebookSectionAdapter.fromEntityToDTO(notebookSectionRepo.save(created)));
        } catch (NoSuchElementException e) {
            log.error(LocalDate.now() + ": " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * @param id
     * @param notebookSectionDTO
     * @return
     */
    @Transactional
    @Override
    public Optional<NotebookSectionDTO> updateNotebookSectionById(Long id, NotebookSectionDTO notebookSectionDTO) {
        try {
            NotebookSection notebookSection = notebookSectionRepo.findById(id).orElseThrow();
            Notebook notebook = notebookSection.getAddedInNotebook();
            if (notebookSectionDTO.getDate().isBefore(notebook.getDate())) {
                throw new IllegalArgumentException("Invalid section date. The section date should be after notebook creation date.");
            }
            NotebookSection updatedSection = notebookSectionAdapter.fromDTOToEntity(notebookSectionDTO);
            updatedSection.setNotebookSectionId(notebookSection.getNotebookSectionId());
            updatedSection.setAddedInNotebook(notebook);
            return Optional.of(notebookSectionAdapter.fromEntityToDTO(notebookSectionRepo.save(updatedSection)));
        } catch (NoSuchElementException e) {
            log.error(LocalDate.now() + ": " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * @param id
     */
    @Transactional
    @Override
    public void deleteNotebookSectionById(Long id) {
        notebookSectionRepo.deleteById(id);
    }
}