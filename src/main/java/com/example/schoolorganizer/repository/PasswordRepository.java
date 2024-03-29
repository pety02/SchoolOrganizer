package com.example.schoolorganizer.repository;

import com.example.schoolorganizer.model.Password;
import com.example.schoolorganizer.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This interface describes PasswordRepository with its functionality.
 *
 * @author Petya Licheva
 */
@Repository
public interface PasswordRepository extends JpaRepository<Password, Long> {
    boolean existsByOwner(User owner);

    Optional<Password> findByOwner(User owner);

    boolean existsByPasswordHash(String hash);

    Password findAllByOwnerUserId(Long id);
}