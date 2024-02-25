package com.example.schoolorganizer.adapter.Impl;

import java.util.*;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.NotebookDTO;
import com.example.schoolorganizer.dto.NotebookSectionDTO;
import com.example.schoolorganizer.model.Notebook;
import com.example.schoolorganizer.model.NotebookSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class describes NotebookAdapterImpl.
 *
 * @author Petya Licheva
 */
@Component
public class NotebookAdapterImpl implements IAdapter<Notebook, NotebookDTO> {
    private final UserAdapterImpl userAdapter;

    /**
     * Genral purpose constructor for NotebookAdapterImpl class.
     *
     * @param userAdapter user adapter object.
     */
    @Autowired
    public NotebookAdapterImpl(UserAdapterImpl userAdapter) {
        this.userAdapter = userAdapter;
    }

    /**
     * This method transforms Notebook entity to NotebookDTO object.
     *
     * @param entity Notebook entity object.
     * @return NotebookDTO object.
     */
    @Override
    public NotebookDTO fromEntityToDTO(Notebook entity) {
        if (entity == null) {
            return null;
        }
        NotebookDTO dto = new NotebookDTO();
        dto.setNotebookId(entity.getNotebookId());
        dto.setCreatedBy(userAdapter.fromEntityToDTO(entity.getCreatedBy()));
        dto.setDate(entity.getDate());
        dto.setTitle(entity.getTitle());
        dto.setSubject(entity.getSubject());
        List<NotebookSectionDTO> sectionDTOS = new ArrayList<>();
        /*for (var section : entity.getSections()) {
            sectionDTOS.add(notebookSectionAdapter.fromEntityToDTO(section));
        }*/
        dto.setSections(sectionDTOS);
        return dto;
    }

    /**
     * This method transforms NotebookDTO object tot Notebook entity object.
     *
     * @param notebookDTO NotebookDTO object.
     * @return Notebook entity object.
     */
    @Override
    public Notebook fromDTOToEntity(NotebookDTO notebookDTO) {
        if (notebookDTO == null) {
            return null;
        }
        Notebook entity = new Notebook();
        entity.setNotebookId(notebookDTO.getNotebookId());
        entity.setDate(notebookDTO.getDate());
        entity.setTitle(notebookDTO.getTitle());
        entity.setCreatedBy(userAdapter.fromDTOToEntity(notebookDTO.getCreatedBy()));
        entity.setSubject(notebookDTO.getSubject());
        List<NotebookSection> sections = new ArrayList<>();
        /*for (var section : notebookDTO.getSections()) {
            sections.add(notebookSectionAdapter.fromDTOToEntity(section));
        }*/
        entity.setSections(sections);
        return entity;
    }
}