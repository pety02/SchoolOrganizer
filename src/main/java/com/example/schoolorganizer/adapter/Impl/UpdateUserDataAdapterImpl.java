package com.example.schoolorganizer.adapter.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.UpdateUserDataDTO;
import com.example.schoolorganizer.model.User;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

/**
 *
 */
@Component
public class UpdateUserDataAdapterImpl implements IAdapter<User, UpdateUserDataDTO> {

    /**
     * @param entity
     * @return
     */
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

    /**
     * @param updateUserDataDTO
     * @return
     */
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