package com.example.schoolorganizer.service;

import com.example.schoolorganizer.model.Password;
import com.example.schoolorganizer.model.User;

import java.util.Optional;

public interface LoginUserService {
    Optional<User> login(String username, String hashedPassword);
}