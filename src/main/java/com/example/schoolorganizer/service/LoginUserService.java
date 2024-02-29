package com.example.schoolorganizer.service;

import com.example.schoolorganizer.dto.LoginUserDTO;

import java.util.NoSuchElementException;
import java.util.Optional;

public interface LoginUserService {
    Optional<LoginUserDTO> login(String username, String hashedPassword);
}