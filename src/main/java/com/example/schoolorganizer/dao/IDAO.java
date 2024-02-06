package com.example.schoolorganizer.dao;

public interface IDAO <E, DTO> {
    DTO fromEntityToDTO(E entity);
    E fromDTOToEntity(DTO dto);
}