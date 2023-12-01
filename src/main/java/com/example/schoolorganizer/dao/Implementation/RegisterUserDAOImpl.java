package com.example.schoolorganizer.dao.Implementation;

import com.example.schoolorganizer.dao.IDAO;
import com.example.schoolorganizer.dto.RegisterUserDTO;
import com.example.schoolorganizer.model.User;

public class RegisterUserDAOImpl implements IDAO<RegisterUserDTO, User> {
    @Override
    public User transformFromDTOToEntity(RegisterUserDTO registerUserDTO) {
        User entity = new User();
        entity.setName(registerUserDTO.getName());
        entity.setSurname(registerUserDTO.getSurname());
        entity.setEmail(registerUserDTO.getEmail());
        entity.setUsername(registerUserDTO.getUsername());
        entity.setPassword(registerUserDTO.getPassword());
        return entity;
    }

    @Override
    public RegisterUserDTO transformFromEntityToDTO(User user) {
        RegisterUserDTO dto = new RegisterUserDTO(
                user.getEmail(),
                user.getUsername(),
                user.getPassword(),
                user.getPassword());
        return dto;
    }
}