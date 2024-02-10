package com.example.schoolorganizer.service;

import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.model.User;

import java.util.Optional;

public interface LoginUserService {
    Optional<LoginUserDTO> login(String username, String hashedPassword);
}