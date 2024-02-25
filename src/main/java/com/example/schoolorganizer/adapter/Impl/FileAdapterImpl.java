package com.example.schoolorganizer.adapter.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.FileDTO;
import com.example.schoolorganizer.model.File;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class FileAdapterImpl implements IAdapter<File, FileDTO> {

    /**
     * @param entity
     * @return
     */
    @Override
    public FileDTO fromEntityToDTO(File entity) {
        if (entity == null) {
            return null;
        }
        FileDTO dto = new FileDTO();
        dto.setFileId(entity.getFileId());
        dto.setDate(entity.getDate());
        dto.setName(entity.getName());
        dto.setPath(entity.getPath());
        dto.setArtificialName(entity.getArtificialName());
        dto.setExtension(entity.getExtension());
        return dto;
    }

    /**
     * @param fileDTO
     * @return
     */
    @Override
    public File fromDTOToEntity(FileDTO fileDTO) {
        if (fileDTO == null) {
            return null;
        }
        File entity = new File();
        entity.setFileId(fileDTO.getFileId());
        entity.setDate(fileDTO.getDate());
        entity.setName(fileDTO.getName());
        entity.setPath(fileDTO.getPath());
        entity.setArtificialName(fileDTO.getArtificialName());
        entity.setExtension(fileDTO.getExtension());
        return entity;
    }
}