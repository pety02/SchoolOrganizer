package com.example.schoolorganizer.service;


import com.example.schoolorganizer.dto.RegisterUserDTO;
import com.example.schoolorganizer.dto.UpdateUserDataDTO;
import com.example.schoolorganizer.model.User;

import java.util.Optional;

public interface RegisterUserService {
    Optional<User> register(RegisterUserDTO userDTO);
}