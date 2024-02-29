package com.example.schoolorganizer.service;

import com.example.schoolorganizer.dto.UpdateUserDataDTO;
import com.example.schoolorganizer.dto.UserDTO;

import java.util.NoSuchElementException;
import java.util.Optional;

public interface UserService {
    Optional<UserDTO> getUserById(Long id)
            throws NoSuchElementException;

    Optional<UpdateUserDataDTO> updateData(Long id, UpdateUserDataDTO userDTO);
}