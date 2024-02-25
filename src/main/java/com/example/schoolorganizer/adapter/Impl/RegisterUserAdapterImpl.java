package com.example.schoolorganizer.adapter.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.RegisterUserDTO;
import com.example.schoolorganizer.model.User;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class RegisterUserAdapterImpl implements IAdapter<User, RegisterUserDTO> {

    /**
     * @param entity
     * @return
     */
    @Override
    public RegisterUserDTO fromEntityToDTO(User entity) {
        if (entity == null) {
            return null;
        }
        RegisterUserDTO dto = new RegisterUserDTO();
        dto.setUserId(entity.getUserId());
        dto.setName(entity.getName());
        dto.setSurname(entity.getSurname());
        dto.setEmail(entity.getEmail());
        dto.setUsername(entity.getUsername());

        return dto;
    }

    /**
     * @param registerUserDTO
     * @return
     */
    @Override
    public User fromDTOToEntity(RegisterUserDTO registerUserDTO) {
        if (registerUserDTO == null) {
            return null;
        }
        User entity = new User();
        entity.setUserId(registerUserDTO.getUserId());
        entity.setName(registerUserDTO.getName());
        entity.setSurname(registerUserDTO.getSurname());
        entity.setEmail(registerUserDTO.getEmail());
        entity.setUsername(registerUserDTO.getUsername());

        return entity;
    }
}