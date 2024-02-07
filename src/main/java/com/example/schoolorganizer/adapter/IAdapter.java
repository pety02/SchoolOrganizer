package com.example.schoolorganizer.adapter;

public interface IAdapter<E, DTO> {
    DTO fromEntityToDTO(E entity);

    E fromDTOToEntity(DTO dto);
}