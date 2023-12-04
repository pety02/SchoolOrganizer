package com.example.schoolorganizer.web;

import com.example.schoolorganizer.dao.Implementation.LoginUserDAOImpl;
import com.example.schoolorganizer.dao.Implementation.RegisterUserDAOImpl;
import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.dto.RegisterUserDTO;
import com.example.schoolorganizer.model.Password;
import com.example.schoolorganizer.model.User;
import com.example.schoolorganizer.model.UserRole;
import com.example.schoolorganizer.repository.PasswordRepository;
import com.example.schoolorganizer.repository.RoleRepository;
import com.example.schoolorganizer.repository.UserRepository;
import com.example.schoolorganizer.security.PasswordHasher;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.NoSuchAlgorithmException;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthRESTController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordRepository passwordRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final RegisterUserDAOImpl registeredUserDAOImpl = new RegisterUserDAOImpl();
    private final LoginUserDAOImpl loginUserDAOImpl = new LoginUserDAOImpl();

    @PostMapping("/signin")
    public ModelAndView authenticateUser(@RequestBody @ModelAttribute("userDTO") @Valid LoginUserDTO loginDTO,
                                         BindingResult result, Model userModel) {
        try {
            String hashedPass = PasswordHasher.hash(loginDTO.getPassword());
            loginDTO.setPassword(hashedPass);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        if (result.hasErrors()
                || !userRepository.existsByUsername(loginDTO.getUsername())
                || !userRepository.exists(Example.of(loginUserDAOImpl.transformFromDTOToEntity(loginDTO)))) {
            ModelAndView m = new ModelAndView();
            m.setViewName("signin");
            m.setStatus(HttpStatus.BAD_REQUEST);
            return m;
        }

        ModelAndView m = new ModelAndView();
        m.setViewName("home");
        m.setStatus(HttpStatus.OK);
        User loggedIn = userRepository.findByUsernameOrEmail(loginDTO.getUsername(), null).orElseThrow();
        userModel.addAttribute("userId", loggedIn.getUserId());
        return m;
    }

    @PostMapping("/signup")
    public ModelAndView registerUser(@RequestBody @ModelAttribute("userDTO") @Valid RegisterUserDTO signUpDTO, BindingResult result) {
        if (result.hasErrors()
                || userRepository.existsByUsername(signUpDTO.getUsername())
                || userRepository.existsByEmail(signUpDTO.getEmail())) {
            ModelAndView m = new ModelAndView();
            m.setViewName("signup");
            m.setStatus(HttpStatus.BAD_REQUEST);
            return m;
        }

        String userPass = signUpDTO.getPassword();
        try {
            String hashedPassword = PasswordHasher.hash(userPass);
            signUpDTO.setPassword(hashedPassword);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        Password password;
        try {
            String hashedPass = PasswordHasher.hash(signUpDTO.getPassword());
            signUpDTO.setPassword(hashedPass);
            password = new Password(hashedPass);
            passwordRepository.save(password);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        User user = registeredUserDAOImpl.transformFromDTOToEntity(signUpDTO);
        user.setPassword(password);
        UserRole role = roleRepository.findByName("CUSTOM_USER").orElseThrow();
        user.setRoles(Collections.singleton(role));
        userRepository.save(user);

        ModelAndView m = new ModelAndView();
        m.setViewName("signin");
        m.setStatus(HttpStatus.OK);
        return m;
    }
}