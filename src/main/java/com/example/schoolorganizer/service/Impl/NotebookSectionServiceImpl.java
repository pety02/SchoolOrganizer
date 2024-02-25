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
 * This class describes NotebookSectionServiceImpl class.
 *
 * @author Petya Licheva
 */
@Service
@Slf4j
public class NotebookSectionServiceImpl implements NotebookSectionService {
    private final NotebookSectionRepository notebookSectionRepo;
    private final IAdapter<NotebookSection, NotebookSectionDTO> notebookSectionAdapter;
    private final NotebookRepository notebookRepo;

    /**
     * General purpose constructor of NotebookSectionServiceImpl class.
     *
     * @param notebookSectionRepo    a notebook section repository object.
     * @param notebookSectionAdapter a notebook section adapter object.
     * @param notebookRepo           a notebook repository object.
     */
    @Autowired
    public NotebookSectionServiceImpl(NotebookSectionRepository notebookSectionRepo, IAdapter<NotebookSection, NotebookSectionDTO> notebookSectionAdapter, NotebookRepository notebookRepo) {
        this.notebookSectionRepo = notebookSectionRepo;
        this.notebookSectionAdapter = notebookSectionAdapter;
        this.notebookRepo = notebookRepo;
    }

    /**
     * This method gets all definite notebook's section by the notebook's id.
     *
     * @param id the notebook's id.
     * @return a list of notebook sections.
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
     * This method gets a definite notebook section by its id and its notebook's.
     *
     * @param notebookId a definite notebook's id.
     * @param sectionId  a definite notebook section's id.
     * @return an optional object of NotebookSectionDTO.
     * @throws NoSuchElementException if there is no such notebook section dto item
     *                                in the database the method throws NoSuchElementException.
     */
    @Override
    public Optional<NotebookSectionDTO> getNotebookSectionByNotebookIdAndSectionId(Long notebookId, Long sectionId)
            throws NoSuchElementException {
        return Optional.of(notebookSectionAdapter
                .fromEntityToDTO(notebookSectionRepo
                        .findByAddedInNotebook_NotebookIdAndNotebookSectionId(notebookId, sectionId)
                        .orElseThrow()));
    }

    /**
     * This method creates new notebook section by definite notebook's id.
     *
     * @param id                 a definite notebook's id.
     * @param notebookSectionDTO a notebook section dto object that represent
     *                           the notebook section content.
     * @return an optional object of NotebookSectionDTO.
     * @throws NoSuchElementException   if there is no notebook with this id in the
     *                                  database the method throws NoSuchElementException to indicate the user that
     *                                  there is a problem with creating new notebook sections in non-existing notebook.
     * @throws IllegalArgumentException if the date of the section is before the notebook's
     *                                  creating date the method throw IllegalArgumentException with an appropriate message to
     *                                  the user in order to explain that he/she cannot create sections in a notebook before this
     *                                  notebook creation.
     */
    @Transactional
    @Override
    public Optional<NotebookSectionDTO> createNewNotebookSectionByNotebookId(Long id, NotebookSectionDTO notebookSectionDTO)
            throws NoSuchElementException, IllegalArgumentException {
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
     * This method updates a definite notebook section by its id.
     *
     * @param id                 the definite notebook's section id.
     * @param notebookSectionDTO the data that should be updated
     *                           for this definite notebook's section.
     * @return an optional object of NotebookSectionDTO.
     * @throws NoSuchElementException   if there is no notebook with this id the method
     *                                  throws NoSuchElementException to indicates a problem to the user.
     * @throws IllegalArgumentException if the date of the section is before the notebook's
     *                                  creating date the method throw IllegalArgumentException with an appropriate message to
     *                                  the user in order to explain that he/she cannot create sections in a notebook before this
     *                                  notebook creation.
     */
    @Transactional
    @Override
    public Optional<NotebookSectionDTO> updateNotebookSectionById(Long id, NotebookSectionDTO notebookSectionDTO)
            throws NoSuchElementException, IllegalArgumentException {
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
     * This method deletes a notebook section by its id if it exists,
     * if not the method do nothing.
     *
     * @param id the notebook section id.
     */
    @Transactional
    @Override
    public void deleteNotebookSectionById(Long id) {
        notebookSectionRepo.deleteById(id);
    }
}