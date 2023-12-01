package com.example.schoolorganizer.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RegisterUserDTO {
    @Pattern(regexp = "^[A-Z]+([ '-][a-zA-Z]+)*$", message = "The name should starts with capital letter.")
    @Size(min = 1, max = 60, message = "The name length should be between 1 and 60 letters.")
    private String name;
    @Pattern(regexp = "^[A-Z]+([ '-][a-zA-Z]+)*$", message = "The surname should starts with capital letter.")
    @Size(min = 1, max = 100, message = "The surname length should be between 1 and 100 letters.")
    private String surname;
    @NonNull
    @NotBlank(message = "The email is required.")
    @Email(message = "The email should be valid.")
    @Size(min = 10, max = 300, message = "The email length should be between 10 and 300.")
    private String email;
    @NonNull
    @NotBlank(message = "The username is required.")
    @Pattern(regexp = "^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){8,150}[a-zA-Z0-9]$", message = "The username should contains lowercase, uppercase and digits.")
    @Size(min = 8, max = 150, message = "The username length should be between 8 and 150 characters.")
    private String username;
    @NonNull
    @NotBlank(message = "The password is required.")
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,150}$", message = "The password should contains lowercase and uppercase.")
    @Size(min = 8, max = 150, message = "The password length should be between 8 and 150 characters.")
    private String password;
}