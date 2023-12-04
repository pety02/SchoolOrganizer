package com.example.schoolorganizer.repository;

import com.example.schoolorganizer.model.Password;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordRepository extends JpaRepository<Password, Long> {
}