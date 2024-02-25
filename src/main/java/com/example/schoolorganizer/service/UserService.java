package com.example.schoolorganizer.service;

import com.example.schoolorganizer.dto.UpdateUserDataDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.model.User;
import jakarta.transaction.Transactional;

import java.util.Optional;

public interface UserService {
    Optional<UserDTO> getUserById(Long id);

    Optional<UpdateUserDataDTO> updateData(Long id, UpdateUserDataDTO userDTO);
}