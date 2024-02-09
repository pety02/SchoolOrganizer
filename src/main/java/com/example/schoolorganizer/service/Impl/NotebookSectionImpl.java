package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.NotebookDTO;
import com.example.schoolorganizer.dto.NotebookSectionDTO;
import com.example.schoolorganizer.model.Notebook;
import com.example.schoolorganizer.model.NotebookSection;
import com.example.schoolorganizer.repository.NotebookRepository;
import com.example.schoolorganizer.repository.NotebookSectionRepository;
import com.example.schoolorganizer.service.NotebookSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class NotebookSectionImpl implements NotebookSectionService {
    private final NotebookSectionRepository notebookSectionRepo;
    private final IAdapter<NotebookSection, NotebookSectionDTO> notebookSectionAdapter;
    private final NotebookRepository notebookRepo;
    private final IAdapter<Notebook, NotebookDTO> notebookAdapter;

    @Autowired
    public NotebookSectionImpl(NotebookSectionRepository notebookSectionRepo, IAdapter<NotebookSection, NotebookSectionDTO> notebookSectionAdapter, NotebookRepository notebookRepo, IAdapter<Notebook, NotebookDTO> notebookAdapter) {
        this.notebookSectionRepo = notebookSectionRepo;
        this.notebookSectionAdapter = notebookSectionAdapter;
        this.notebookRepo = notebookRepo;
        this.notebookAdapter = notebookAdapter;
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
            NotebookSection notebookSection = notebookSectionRepo.save(notebookSectionAdapter.fromDTOToEntity(notebookSectionDTO));
            notebook.getSections().add(notebookSection);
            notebookRepo.save(notebook);
            return Optional.of(notebookSection);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<NotebookSection> updateNotebookSectionById(Long id, NotebookSectionDTO notebookSectionDTO) {
        try {
            NotebookSection notebookSection = notebookSectionRepo.findById(id).orElseThrow();
            Notebook notebook = notebookSection.getAddedInNotebook();
            NotebookSection updatedSection = notebookSectionAdapter.fromDTOToEntity(notebookSectionDTO);
            for (var currNotebookSection : notebook.getSections()) {
                if (currNotebookSection.getNotebookSectionId().equals(updatedSection.getNotebookSectionId())) {
                    currNotebookSection = updatedSection;
                    notebookSectionRepo.save(currNotebookSection);
                    break;
                }
            }
            notebookRepo.save(notebook);
            return Optional.of(updatedSection);
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }

    @Override
    public void deleteNotebookSectionById(Long id) {
        notebookSectionRepo.deleteById(id);
    }
}
