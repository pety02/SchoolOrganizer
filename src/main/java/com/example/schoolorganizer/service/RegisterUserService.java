package com.example.schoolorganizer.service;


import com.example.schoolorganizer.dto.RegisterUserDTO;
import com.example.schoolorganizer.model.User;
import jakarta.transaction.Transactional;

import java.util.Optional;

public interface RegisterUserService {

    Optional<RegisterUserDTO> register(RegisterUserDTO userDTO);
}