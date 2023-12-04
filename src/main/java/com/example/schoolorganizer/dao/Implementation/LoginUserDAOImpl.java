package com.example.schoolorganizer.dao.Implementation;

import com.example.schoolorganizer.dao.IDAO;
import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.model.Password;
import com.example.schoolorganizer.model.User;

import java.util.Set;

public class LoginUserDAOImpl implements IDAO<LoginUserDTO, User> {
    @Override
    public User transformFromDTOToEntity(LoginUserDTO loginUserDTO) {
        User entity = new User();
        entity.setUsername(loginUserDTO.getUsername());
        Password hashedPassword = new Password(loginUserDTO.getPassword());
        entity.setPassword(hashedPassword);

        return entity;
    }

    @Override
    public LoginUserDTO transformFromEntityToDTO(User user) {
        LoginUserDTO dto = new LoginUserDTO();
        dto.setUsername(user.getUsername());
        dto.setPassword(user.getPassword().getPasswordHash());

        return dto;
    }
}
