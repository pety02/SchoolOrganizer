package com.example.schoolorganizer.repository;

import com.example.schoolorganizer.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<UserRole, Long> {
    Optional<UserRole> findByName(String name);
}