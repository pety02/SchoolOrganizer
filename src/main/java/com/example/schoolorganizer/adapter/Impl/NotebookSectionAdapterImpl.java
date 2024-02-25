package com.example.schoolorganizer.adapter.Impl;

import java.util.*;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.FileDTO;
import com.example.schoolorganizer.dto.NotebookSectionDTO;
import com.example.schoolorganizer.model.File;
import com.example.schoolorganizer.model.NotebookSection;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class NotebookSectionAdapterImpl implements IAdapter<NotebookSection, NotebookSectionDTO> {

    /**
     * @param entity
     * @return
     */
    @Override
    public NotebookSectionDTO fromEntityToDTO(NotebookSection entity) {
        if (entity == null) {
            return null;
        }
        NotebookSectionDTO dto = new NotebookSectionDTO();
        dto.setNotebookSectionId(entity.getNotebookSectionId());
        dto.setDate(entity.getDate());
        dto.setTitle(entity.getTitle());
        List<FileDTO> fileDTOS = new ArrayList<>();
        /*for (var file : entity.getFiles()) {
            fileDTOS.add(fileAdapter.fromEntityToDTO(file));
        }*/
        dto.setFiles(fileDTOS);
        dto.setContent(entity.getContent());
        return dto;
    }

    /**
     * @param notebookSectionDTO
     * @return
     */
    @Override
    public NotebookSection fromDTOToEntity(NotebookSectionDTO notebookSectionDTO) {
        if (notebookSectionDTO == null) {
            return null;
        }
        NotebookSection entity = new NotebookSection();
        entity.setNotebookSectionId(notebookSectionDTO.getNotebookSectionId());
        entity.setDate(notebookSectionDTO.getDate());
        entity.setTitle(notebookSectionDTO.getTitle());
        List<File> files = new ArrayList<>();
        /*for (var file : notebookSectionDTO.getFiles()) {
            files.add(fileAdapter.fromDTOToEntity(file));
        }*/
        entity.setFiles(files);
        entity.setContent(notebookSectionDTO.getContent());
        return entity;
    }
}