package com.example.schoolorganizer.dto;

import jakarta.validation.constraints.*;
import lombok.*;

/**
 * This class describes registered user DTO.
 *
 * @author Petya Licheva
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class RegisterUserDTO {
    @EqualsAndHashCode.Include
    @Positive(message = "Id should be positive!")
    private Long userId;
    @Pattern(regexp = "^[A-Z,a-z]{1,60}$", message = "The name should starts with capital letter.")
    @Size(min = 1, max = 60, message = "The name length should be between 1 and 60 letters.")
    private String name;
    @Pattern(regexp = "^[A-Z,a-z]{1,100}$", message = "The surname should starts with capital letter.")
    @Size(min = 1, max = 100, message = "The surname length should be between 1 and 100 letters.")
    private String surname;
    @NonNull
    @NotBlank(message = "The email is required.")
    @Email(message = "The email should be valid.")
    @Size(min = 10, max = 300, message = "The email length should be between 10 and 300.")
    private String email;
    @NonNull
    @NotBlank(message = "The username is required.")
    @Pattern(regexp = "^[a-z,A-Z,0-9]{8,150}$", message = "The username should contains lowercase, uppercase and digits.")
    @Size(min = 8, max = 150, message = "The username length should be between 8 and 150 characters.")
    private String username;
    @NonNull
    @NotBlank(message = "The password is required.")
    @Pattern(regexp = "^[a-z,A-Z,0-9]{8,150}$", message = "The password should contains lowercase and uppercase.")
    @Size(min = 8, max = 150, message = "The password length should be between 8 and 150 characters.")
    private String password;
    @NonNull
    @NotBlank(message = "The re-entered password is required.")
    @Pattern(regexp = "^[a-z,A-Z,0-9]{8,150}$", message = "The re-entered password should contains lowercase and uppercase.")
    @Size(min = 8, max = 150, message = "The re-entered password length should be between 8 and 150 characters.")
    private String reEnteredPassword;

    /**
     * @return the RegisteredUserDTO object in JSON string format.
     */
    @Override
    public String toString() {
        return "{" +
                "\"userId\": " + userId +
                ", \"name\": " + name +
                ", \"surname\": " + surname +
                ", \"email\": " + email +
                ", \"username\": " + username +
                ", \"password\": " + password +
                ", \"reEnteredPassword\": " + reEnteredPassword +
                '}';
    }
}