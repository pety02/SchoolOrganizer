package com.example.schoolorganizer.adapter.Impl;

import java.util.*;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.FileDTO;
import com.example.schoolorganizer.dto.NotebookSectionDTO;
import com.example.schoolorganizer.model.File;
import com.example.schoolorganizer.model.NotebookSection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NotebookSectionAdapterImpl implements IAdapter<NotebookSection, NotebookSectionDTO> {
    private final FileAdapterImpl fileAdapter;

    @Autowired
    public NotebookSectionAdapterImpl(FileAdapterImpl fileAdapter) {
        this.fileAdapter = fileAdapter;
    }

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