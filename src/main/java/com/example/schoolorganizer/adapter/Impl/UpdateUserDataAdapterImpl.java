package com.example.schoolorganizer.adapter.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.UpdateUserDataDTO;
import com.example.schoolorganizer.model.User;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

/**
 * This class describes UpdateUserDataAdapterImpl. A class that
 * transforms a User to a UpdateUserDataDTO and vice versa.
 *
 * @author Petya Licheva
 */
@Component
public class UpdateUserDataAdapterImpl implements IAdapter<User, UpdateUserDataDTO> {

    /**
     * This method transforms a User entity object to a UpdateUserDataDTO object.
     *
     * @param entity a User entity object.
     * @return a UpdateUserDataDTO object.
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
     * This method transforms a UpdateUserDataDTO object to a User entity object.
     *
     * @param updateUserDataDTO a UpdateUserDataDTO object.
     * @return a User entity object.
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