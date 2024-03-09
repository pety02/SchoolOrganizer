package com.example.schoolorganizer.repository;

import java.util.Optional;

import com.example.schoolorganizer.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * This interface describes FileRepository with its functionality.
 *
 * @author Petya Licheva
 */
public interface FileRepository extends JpaRepository<File, Long> {
    Optional<File> findByName(String name);
}