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
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * This class describes NotebookServiceImpl.
 *
 * @author Petya Licheva
 */
@Service
@Slf4j
public class NotebookServiceImpl implements NotebookService {
    private final NotebookRepository notebookRepo;
    private final IAdapter<Notebook, NotebookDTO> notebookAdapter;

    /**
     * General purpose constructor of NotebookServiceImpl class.
     *
     * @param notebookRepo    the notebook repository object.
     * @param notebookAdapter the notebook adapter object.
     */
    @Autowired
    public NotebookServiceImpl(NotebookRepository notebookRepo, IAdapter<Notebook, NotebookDTO> notebookAdapter) {
        this.notebookRepo = notebookRepo;
        this.notebookAdapter = notebookAdapter;
    }

    /**
     * This method gets all notebooks by its owner id.
     *
     * @param id the notebook's owner id.
     * @return a list of NotebookDTO objects that represents
     * the notebooks of definite user.
     */
    @Override
    public List<NotebookDTO> getAllNotebooksByUserId(Long id) {

        List<Notebook> notebooks = notebookRepo.findAllByCreatedByUserId(id);
        List<NotebookDTO> notebookDTOs = new ArrayList<>();
        for (var currentNotebook : notebooks) {
            NotebookDTO currentNotebookDTO = notebookAdapter.fromEntityToDTO(currentNotebook);
            notebookDTOs.add(currentNotebookDTO);
        }

        return notebookDTOs;
    }

    /**
     * This method gets optional type of NotebookDTO if a notebook
     * with this id exist in the database or else throws an exception.
     *
     * @param userId     the owner's id.
     * @param notebookId the notebook's id.
     * @return an optional type of NotebookDTO.
     * @throws NoSuchElementException if there is no notebook with this
     *                                notebook's id in the database the method throws a NoSuchElementException
     *                                instead of returning an empty object or null pointer.
     */
    @Override
    public Optional<NotebookDTO> getNotebookByNotebookId(Long userId, Long notebookId)
            throws NoSuchElementException {
        return Optional.of(notebookAdapter
                .fromEntityToDTO(notebookRepo
                        .findByCreatedByUserIdAndNotebookId(userId,
                                notebookId).orElseThrow()));
    }

    /**
     * This method creates a notebook in the database with the same
     * data as the parameter dto object.
     *
     * @param notebookDTO the notebook dto object.
     * @return an optional type of NotebookDTO if the notebook is created
     * successfully in the database and Optional.empty() object in all other
     * situations.
     */
    @Transactional
    @Override
    public Optional<NotebookDTO> createNewNotebook(NotebookDTO notebookDTO) {
        if (notebookDTO == null) {
            return Optional.empty();
        }

        Notebook notebook = notebookAdapter.fromDTOToEntity(notebookDTO);
        return Optional.of(notebookAdapter
                .fromEntityToDTO(notebookRepo.save(notebook)));
    }

    /**
     * This method updates a notebook object in the database using the
     * data provided from the parameter NotebookDTO object.
     *
     * @param id          the id of the notebook that should be updated.
     * @param notebookDTO the notebook dto object.
     * @return an optional type of NotebookDTO if the notebook with this
     * id is updated successfully and Optional.empty() if not.
     */
    @Transactional
    @Override
    public Optional<NotebookDTO> updateNotebookById(Long id, NotebookDTO notebookDTO) {
        if (notebookDTO == null) {
            return Optional.empty();
        }
        Notebook notebook = notebookAdapter.fromDTOToEntity(notebookDTO);
        if (notebookRepo.existsById(id)) {
            try {
                return Optional.of(notebookAdapter
                        .fromEntityToDTO(notebookRepo.save(notebook)));
            } catch (NoSuchElementException e) {
                log.error(LocalDate.now() + ": " + e.getMessage());
                return Optional.empty();
            }
        }
        return Optional.empty();
    }

    /**
     * This method deletes a notebook with definite id.
     *
     * @param id the notebook's id.
     */
    @Transactional
    @Override
    public void deleteNotebookById(Long id) {
        notebookRepo.deleteById(id);
    }
}