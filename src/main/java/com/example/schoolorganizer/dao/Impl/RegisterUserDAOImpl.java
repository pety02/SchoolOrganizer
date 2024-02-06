package com.example.schoolorganizer.dao.Impl;

import com.example.schoolorganizer.dao.IDAO;
import com.example.schoolorganizer.dto.RegisterUserDTO;
import com.example.schoolorganizer.model.Password;
import com.example.schoolorganizer.model.User;
import org.springframework.stereotype.Component;

@Component
public class RegisterUserDAOImpl implements IDAO<User, RegisterUserDTO> {

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
        Password p = new Password();
        p.setPasswordHash(registerUserDTO.getPassword());

        return entity;
    }
}
