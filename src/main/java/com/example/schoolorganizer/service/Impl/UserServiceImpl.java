package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.dao.IDAO;
import com.example.schoolorganizer.dto.RegisterUserDTO;
import com.example.schoolorganizer.dto.UpdateUserDataDTO;
import com.example.schoolorganizer.model.Password;
import com.example.schoolorganizer.model.User;
import com.example.schoolorganizer.model.UserRole;
import com.example.schoolorganizer.repository.PasswordRepository;
import com.example.schoolorganizer.repository.UserRepository;
import com.example.schoolorganizer.security.PasswordHasher;
import com.example.schoolorganizer.service.LoginUserService;
import com.example.schoolorganizer.service.RegisterUserService;
import com.example.schoolorganizer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Component
public class UserServiceImpl implements RegisterUserService, LoginUserService, UserService {

    private final UserRepository userRepo;
    private final PasswordRepository passwordRepo;
    private final IDAO<User, RegisterUserDTO> registerDAO;

    @Autowired
    public UserServiceImpl(UserRepository userRepo,
                           PasswordRepository passwordRepo,
                           IDAO<User, RegisterUserDTO> registerDAO) {
        this.userRepo = userRepo;
        this.passwordRepo = passwordRepo;
        this.registerDAO = registerDAO;
    }

    @Override
    public Optional<User> updateData(UpdateUserDataDTO userDTO) {
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepo.findByUserId(id);
    }

    @Override
    public Optional<User> login(String username, String hashedPassword) {
        String hash;
        try {
            hash = PasswordHasher.hash(hashedPassword);
            if (userRepo.existsByUsername(username)) {
                User loggedIn = userRepo.findByUsername(username).orElseThrow();
                Collection<Password> passwords = passwordRepo.findByPasswordHash(hash);
                Password pass = passwordRepo.findByOwner(loggedIn).orElse(null);
                for (var p : passwords) {
                    if (pass != null && p.getPasswordHash().equals(pass.getPasswordHash())) {
                        return Optional.of(loggedIn);
                    }
                }
            }
        } catch (NoSuchAlgorithmException | NoSuchElementException ex) {
            return Optional.empty();
        }

        return Optional.empty();
    }

    @Override
    public Optional<User> delete(Integer userID) {
        return Optional.empty();
    }

    @Override
    public Optional<User> register(RegisterUserDTO userDTO) {
        if (!userRepo.existsByUsername(userDTO.getUsername()) && !userRepo.existsByEmail(userDTO.getEmail())) {
            User u = registerDAO.fromDTOToEntity(userDTO);
            String hashedPassword;
            try {
                hashedPassword = PasswordHasher.hash(userDTO.getPassword());
            } catch (NoSuchAlgorithmException ex) {
                hashedPassword = userDTO.getPassword();
            }
            u.setRoles(Set.of(UserRole.STUDENT));
            userRepo.save(u);
            Password p = new Password(hashedPassword, u);

            passwordRepo.save(p);
            return Optional.of(u);
        }
        return Optional.empty();
    }
}