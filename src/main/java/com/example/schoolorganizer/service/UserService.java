package com.example.schoolorganizer.service;

import com.example.schoolorganizer.dto.UpdateUserDataDTO;
import com.example.schoolorganizer.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> updateData(UpdateUserDataDTO userDTO);

    Optional<User> getUserById(Long id);

    Optional<User> delete(Integer userID);
}
