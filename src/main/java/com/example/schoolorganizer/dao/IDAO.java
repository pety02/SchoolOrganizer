package com.example.schoolorganizer.dao;

public interface IDAO<DTO, Entity> {
    Entity transformFromDTOToEntity(DTO dto);
    DTO transformFromEntityToDTO(Entity entity);
}