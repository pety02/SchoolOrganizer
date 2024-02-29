package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.dto.RegisterUserDTO;
import com.example.schoolorganizer.dto.UpdateUserDataDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.model.Password;
import com.example.schoolorganizer.model.User;
import com.example.schoolorganizer.model.UserRole;
import com.example.schoolorganizer.repository.PasswordRepository;
import com.example.schoolorganizer.repository.UserRepository;
import com.example.schoolorganizer.security.PasswordHasher;
import com.example.schoolorganizer.service.EmailService;
import com.example.schoolorganizer.service.LoginUserService;
import com.example.schoolorganizer.service.RegisterUserService;
import com.example.schoolorganizer.service.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

/**
 * This class describes an UserServiceImpl.
 *
 * @author Petya Licheva
 */
@Service
@Slf4j
public class UserServiceImpl implements RegisterUserService, LoginUserService, UserService {

    private final UserRepository userRepo;
    private final PasswordRepository passwordRepo;
    private final IAdapter<User, LoginUserDTO> loginAdapter;
    private final IAdapter<User, RegisterUserDTO> registerAdapter;
    private final IAdapter<User, UserDTO> userAdapter;
    private final IAdapter<User, UpdateUserDataDTO> updatedUserAdapter;
    private final EmailService emailService;

    /**
     * This method send email messages from the application administrative
     * email to a user's email with definite subject and text message on
     * user's profile data update.
     *
     * @param oldUser the old user data before the update.
     * @param updated the updated user data after update.
     */

    /*
       TODO: To think if you want to send messages via SMTP server. To find
        free and secure way of sending emails on user data update.
     */
    private void sendEmailMessage(User oldUser, UpdateUserDataDTO updated) {
        if (oldUser == null || updated == null) {
            return;
        }
        String to = updated.getNewEmail();
        String subject = "User data updated:";
        String message = "Dear, " + oldUser.getName() + " " + oldUser.getSurname()
                + "\nYour personal data has been updated recently! If this was not you, " +
                "do not hesitate to contact our support team at the following email: " +
                "school_organizer@abv.bg!\n" + "\n" + "Best Regards,\n" +
                "Petya Licheva - School Organizer support";

        emailService.sendEmail(to, subject, message);
    }

    /**
     * General purpose constructor of UserServiceImpl class.
     *
     * @param userRepo           the user repository.
     * @param passwordRepo       the password repository.
     * @param loginAdapter       the login adapter.
     * @param registerAdapter    the register adapter.
     * @param userAdapter        the user adapter.
     * @param updatedUserAdapter the update adapter.
     * @param emailService       the email service.
     */
    @Autowired
    public UserServiceImpl(UserRepository userRepo, PasswordRepository passwordRepo,
                           IAdapter<User, LoginUserDTO> loginAdapter,
                           IAdapter<User, RegisterUserDTO> registerAdapter,
                           IAdapter<User, UserDTO> userAdapter,
                           IAdapter<User, UpdateUserDataDTO> updatedUserAdapter,
                           EmailService emailService) {
        this.userRepo = userRepo;
        this.passwordRepo = passwordRepo;
        this.loginAdapter = loginAdapter;
        this.registerAdapter = registerAdapter;
        this.userAdapter = userAdapter;
        this.updatedUserAdapter = updatedUserAdapter;
        this.emailService = emailService;
    }

    /**
     * This method gets a user by its id.
     *
     * @param id the user's id.
     * @return an optional type of UserDTO if
     * a user with this id exists in the database.
     * @throws NoSuchElementException if a user with
     *                                this id does not exist in the database the method
     *                                throws a NoSuchElementException.
     */
    @Override
    public Optional<UserDTO> getUserById(Long id)
            throws NoSuchElementException {
        return Optional.of(userAdapter
                .fromEntityToDTO(userRepo
                        .findByUserId(id)
                        .orElseThrow()));
    }

    /**
     * This method logging in a user with definite
     * credentials if these credentials are correct
     * and there is a user with them in the database.
     *
     * @param username the username of the user.
     * @param password the password of the user.
     * @return an optional type of LoginUserDTO if logging
     * in the application is successful and Optional.empty()
     * in all other situations.
     */
    @Override
    public Optional<LoginUserDTO> login(String username, String password) {
        try {
            if (userRepo.existsByUsername(username)) {
                User loggedIn = userRepo.findByUsername(username).orElseThrow();
                Password currUserPassword = passwordRepo.findAllByOwnerUserId(loggedIn.getUserId());
                if (PasswordHasher.verifyPassword(password, currUserPassword.getPasswordHash())) {
                    return Optional.of(loginAdapter.fromEntityToDTO(loggedIn));
                }
            } else {
                throw new IllegalArgumentException("The username is incorrect.");
            }
        } catch (NoSuchElementException | IllegalArgumentException e) {
            log.error(LocalDate.now() + ": " + e.getMessage());
            return Optional.empty();
        }

        return Optional.empty();
    }

    /**
     * This method updates a definite user data by its own id and
     * sends email messages to the updated user email about this
     * activity in its personal profile.
     *
     * @param id      the definite user id.
     * @param userDTO the data needed for the update.
     * @return an optional of type UpdateUserDTO if the update is
     * successful and Optional.empty() in all other situations.
     */
    @Transactional
    @Override
    public Optional<UpdateUserDataDTO> updateData(Long id, UpdateUserDataDTO userDTO) {
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
                oldPassword.setPasswordHash(PasswordHasher.hashPassword(userDTO
                        .getNewPassword()));
                oldPassword.setOwner(oldUser);
                passwordRepo.save(oldPassword);
            }
            UpdateUserDataDTO updated = updatedUserAdapter
                    .fromEntityToDTO(userRepo.save(oldUser));

            // sendEmailMessage(oldUser, updated);
            return Optional.of(updated);
        } catch (Exception e) {
            log.error(LocalDate.now() + ": " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * This method registers a new user in the application after
     * all needed personal data checks for validity.
     *
     * @param userDTO the definite user data.
     * @return an optional type of RegisterUserDTO if the registration
     * is successful and Optional.empty() in all other situations.
     */
    @Transactional
    @Override
    public Optional<RegisterUserDTO> register(RegisterUserDTO userDTO) {
        if (!userRepo.existsByUsername(userDTO.getUsername())
                && !userRepo.existsByEmail(userDTO.getEmail())) {
            User registered = registerAdapter.fromDTOToEntity(userDTO);
            String hashedPassword;
            try {
                hashedPassword = PasswordHasher.hashPassword(userDTO.getPassword());
            } catch (Exception ex) {
                hashedPassword = userDTO.getPassword();
            }
            registered.setRoles(Set.of(UserRole.STUDENT));
            userRepo.save(registered);
            Password p = new Password(hashedPassword, registered);

            passwordRepo.save(p);
            return Optional.of(registerAdapter.fromEntityToDTO(registered));
        }
        return Optional.empty();
    }
}