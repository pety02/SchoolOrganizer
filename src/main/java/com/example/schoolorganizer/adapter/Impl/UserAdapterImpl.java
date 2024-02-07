package com.example.schoolorganizer.adapter.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.model.Password;
import com.example.schoolorganizer.model.User;
import com.example.schoolorganizer.repository.PasswordRepository;
import org.springframework.stereotype.Component;

import java.util.NoSuchElementException;

@Component
public class UserAdapterImpl implements IAdapter<User, UserDTO> {
    private final PasswordRepository passwordRepo;

    public UserAdapterImpl(PasswordRepository passwordRepo) {
        this.passwordRepo = passwordRepo;
    }

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