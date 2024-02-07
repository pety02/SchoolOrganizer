package com.example.schoolorganizer.dao.Impl;

import com.example.schoolorganizer.dao.IDAO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserDAOImpl implements IDAO<User, UserDTO> {

    @Override
    public UserDTO fromEntityToDTO(User entity) {
        // TODO: not implemented
        return null;
    }

    @Override
    public User fromDTOToEntity(UserDTO userDTO) {
        // TODO: not implemented
        return null;
    }
}