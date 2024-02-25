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
 *
 */
@Component
public class NotebookAdapterImpl implements IAdapter<Notebook, NotebookDTO> {
    private final UserAdapterImpl userAdapter;

    /**
     * @param userAdapter
     */
    @Autowired
    public NotebookAdapterImpl(UserAdapterImpl userAdapter) {
        this.userAdapter = userAdapter;
    }

    /**
     * @param entity
     * @return
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
     * @param notebookDTO
     * @return
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