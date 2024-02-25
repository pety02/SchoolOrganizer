package com.example.schoolorganizer.adapter.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.FileDTO;
import com.example.schoolorganizer.model.File;
import org.springframework.stereotype.Component;

/**
 * This class describes a FileAdapterImpl. A class that transforms
 * a File to FileDTO and vice versa.
 *
 * @author Petya Licheva
 */
@Component
public class FileAdapterImpl implements IAdapter<File, FileDTO> {

    /**
     * Method that transforms from File entity to File DTO.
     *
     * @param entity the File entity object.
     * @return the File DTO object.
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
     * Method that transforms from File DTO to File entity.
     *
     * @param fileDTO the File DTO object.
     * @return the File entity object.
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