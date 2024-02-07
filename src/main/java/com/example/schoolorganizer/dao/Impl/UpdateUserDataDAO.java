package com.example.schoolorganizer.dao.Impl;

import com.example.schoolorganizer.dao.IDAO;
import com.example.schoolorganizer.dto.UpdateUserDataDTO;
import com.example.schoolorganizer.model.User;
import com.example.schoolorganizer.repository.PasswordRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class UpdateUserDataDAO implements IDAO<User, UpdateUserDataDTO> {

    @Override
    public UpdateUserDataDTO fromEntityToDTO(User entity) {
        if (entity == null) {
            return null;
        }
        try {
            UpdateUserDataDTO updateUserDTO = new UpdateUserDataDTO();
            updateUserDTO.setUserId(entity.getUserId());
            updateUserDTO.setOldEmail(entity.getEmail());
            updateUserDTO.setNewEmail("");
            updateUserDTO.setOldUsername(entity.getUsername());
            updateUserDTO.setNewUsername("");
            updateUserDTO.setOldPassword("");
            updateUserDTO.setNewPassword("");
            updateUserDTO.setReEnteredNewPassword("");
            return updateUserDTO;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    @Override
    public User fromDTOToEntity(UpdateUserDataDTO updateUserDataDTO) {
        if (updateUserDataDTO == null) {
            return null;
        }
        User entity = new User();
        entity.setUserId(updateUserDataDTO.getUserId());
        entity.setUsername(updateUserDataDTO.getNewUsername());
        entity.setEmail(updateUserDataDTO.getNewEmail());
        return entity;
    }
}