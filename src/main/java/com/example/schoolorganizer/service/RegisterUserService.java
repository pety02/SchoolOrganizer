package com.example.schoolorganizer.service;


import com.example.schoolorganizer.dto.RegisterUserDTO;

import java.util.Optional;

public interface RegisterUserService {
    Optional<RegisterUserDTO> register(RegisterUserDTO userDTO);
}