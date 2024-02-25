package com.example.schoolorganizer.adapter.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.model.Password;
import com.example.schoolorganizer.model.User;
import com.example.schoolorganizer.repository.PasswordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

/**
 * This class describes UserAdapterImpl. A class that
 * transforms a User to a UserDTO and vice versa.
 *
 * @author Petya Licheva
 */
@Component
public class UserAdapterImpl implements IAdapter<User, UserDTO> {
    private final PasswordRepository passwordRepo;

    /**
     * General purpose constructor of UserAdapterImpl class.
     *
     * @param passwordRepo a password repository.
     */
    @Autowired
    public UserAdapterImpl(PasswordRepository passwordRepo) {
        this.passwordRepo = passwordRepo;
    }

    /**
     * This class transforms a User entity object to a UserDTO object.
     *
     * @param entity a User entity object.
     * @return a UserDTO object.
     */
    @Override
    public UserDTO fromEntityToDTO(User entity) {
        if (entity == null) {
            return null;
        }
        try {
            UserDTO dto = new UserDTO();
            dto.setUserId(entity.getUserId());
            dto.setName(entity.getName());
            dto.setSurname(entity.getSurname());
            dto.setUsername(entity.getUsername());
            dto.setEmail(entity.getEmail());
            Password password = passwordRepo.findByOwner(entity).orElseThrow();
            dto.setPassword(password.getPasswordHash());
            dto.setRoles(entity.getRoles());
            return dto;
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    /**
     * This method transforms a UserDTO object to a User entity object.
     *
     * @param userDTO a UserDTO object.
     * @return a User entity object.
     */
    @Override
    public User fromDTOToEntity(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }
        User entity = new User();
        entity.setUserId(userDTO.getUserId());
        entity.setName(userDTO.getName());
        entity.setSurname(userDTO.getSurname());
        entity.setUsername(userDTO.getUsername());
        entity.setEmail(userDTO.getEmail());
        entity.setRoles(userDTO.getRoles());
        return entity;
    }
}