package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
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
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements RegisterUserService, LoginUserService, UserService {

    private final UserRepository userRepo;
    private final PasswordRepository passwordRepo;
    private final IAdapter<User, RegisterUserDTO> registerDAO;
    private final IAdapter<User, UpdateUserDataDTO> updateUserDAO;

    @Autowired
    public UserServiceImpl(UserRepository userRepo,
                           PasswordRepository passwordRepo,
                           IAdapter<User, RegisterUserDTO> registerDAO,
                           IAdapter<User, UpdateUserDataDTO> updateUserDAO) {
        this.userRepo = userRepo;
        this.passwordRepo = passwordRepo;
        this.registerDAO = registerDAO;
        this.updateUserDAO = updateUserDAO;
    }

    @Override
    public Optional<User> updateData(Long id, UpdateUserDataDTO userDTO) {
        if (!userRepo.existsById(id)) {
            return Optional.empty();
        }
        try {
            boolean isEmailChanged = userDTO.getNewEmail() != null;
            boolean isUsernameChanged = userDTO.getNewUsername() != null;
            User oldUser = userRepo
                    .findByUserId(id)
                    .orElseThrow();
            Password oldPassword = passwordRepo.findByOwner(oldUser)
                    .orElseThrow();
            String oldPassHash = oldPassword
                    .getPasswordHash();
            boolean isPasswordChanged = userDTO.getOldPassword().equals(oldPassHash)
                    && userDTO.getNewPassword().equals(userDTO.getReEnteredNewPassword());
            if (isEmailChanged) {
                oldUser.setEmail(userDTO.getNewEmail());
            }
            if (isUsernameChanged) {
                oldUser.setUsername(userDTO.getNewUsername());
            }
            if (isPasswordChanged) {
                oldPassword.setPasswordHash(PasswordHasher.hash(userDTO.getNewPassword()));
                oldPassword.setOwner(oldUser);
                passwordRepo.save(oldPassword);
            }
            return Optional.of(userRepo.save(oldUser));
        } catch (NoSuchElementException | NoSuchAlgorithmException e) {
            return Optional.empty();
        }
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