package com.example.schoolorganizer.adapter.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.model.User;
import org.springframework.stereotype.Component;

/**
 * This class describes a LoginUserAdapterImpl. A class that transforms
 * a User to LoginUserDTO and vice versa.
 *
 * @author Petya Licheva
 */
@Component
public class LoginUserAdapterImpl implements IAdapter<User, LoginUserDTO> {

    /**
     * Method that transforms User entity to LoginUserDTO object.
     *
     * @param entity User entity object.
     * @return LoginUserDTO object.
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
     * Method that transforms LoginUserDTO object to User entity object.
     *
     * @param loginUserDTO LoginUserDTO object.
     * @return User entity object.
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