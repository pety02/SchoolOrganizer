package com.example.schoolorganizer.dao.Implementation;

import com.example.schoolorganizer.dao.IDAO;
import com.example.schoolorganizer.dto.RegisterUserDTO;
import com.example.schoolorganizer.model.Password;
import com.example.schoolorganizer.model.User;

public class RegisterUserDAOImpl implements IDAO<RegisterUserDTO, User> {
    @Override
    public User transformFromDTOToEntity(RegisterUserDTO registerUserDTO) {
        User entity = new User();
        entity.setEmail(registerUserDTO.getEmail());
        entity.setUsername(registerUserDTO.getUsername());
        Password passwordHash = new Password(registerUserDTO.getPassword());
        entity.setPassword(passwordHash);
        return entity;
    }

    @Override
    public RegisterUserDTO transformFromEntityToDTO(User user) {
        RegisterUserDTO dto = new RegisterUserDTO(
                user.getEmail(),
                user.getUsername(),
                user.getPassword().getPasswordHash(),
                user.getPassword().getPasswordHash());
        return dto;
    }
}