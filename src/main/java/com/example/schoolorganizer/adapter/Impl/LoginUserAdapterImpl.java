package com.example.schoolorganizer.adapter.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.model.User;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class LoginUserAdapterImpl implements IAdapter<User, LoginUserDTO> {

    /**
     * @param entity
     * @return
     */
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

    /**
     * @param loginUserDTO
     * @return
     */
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