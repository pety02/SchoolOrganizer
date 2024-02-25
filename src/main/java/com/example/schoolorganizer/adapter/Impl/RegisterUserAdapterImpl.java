package com.example.schoolorganizer.adapter.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.RegisterUserDTO;
import com.example.schoolorganizer.model.User;
import org.springframework.stereotype.Component;

/**
 * This class describes RegisteredUserAdapterImpl. A class that
 * transforms a User to a RegisteredUserDTO and vice versa.
 *
 * @author Petya Licheva
 */
@Component
public class RegisterUserAdapterImpl implements IAdapter<User, RegisterUserDTO> {

    /**
     * This method transforms User entity object to RegisteredUserDTO object.
     *
     * @param entity a User entity object.
     * @return a RegisteredUserDTO object.
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
     * This method transforms a RegisteredUserDTO object to a User entity object.
     *
     * @param registerUserDTO a RegisteredUserDTO object.
     * @return a User entity object.
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