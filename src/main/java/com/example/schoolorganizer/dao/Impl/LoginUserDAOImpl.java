package com.example.schoolorganizer.dao.Impl;

import com.example.schoolorganizer.dao.IDAO;
import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.model.Password;
import com.example.schoolorganizer.model.User;
import org.springframework.stereotype.Component;

@Component
public class LoginUserDAOImpl implements IDAO<User, LoginUserDTO> {

    @Override
    public LoginUserDTO fromEntityToDTO(User entity) {
        if (entity == null) {
            return null;
        }
        LoginUserDTO dto = new LoginUserDTO();
        dto.setUserId(entity.getUserId());
        dto.setUsername(entity.getUsername());

        return dto;
    }

    @Override
    public User fromDTOToEntity(LoginUserDTO loginUserDTO) {
        if (loginUserDTO == null) {
            return null;
        }
        User entity = new User();
        entity.setUserId(loginUserDTO.getUserId());
        entity.setUsername(loginUserDTO.getUsername());

        return entity;
    }
}
