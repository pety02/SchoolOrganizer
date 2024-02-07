package com.example.schoolorganizer.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UpdateUserDataDTO {
    @EqualsAndHashCode.Include
    @Positive(message = "Id should be positive!")
    private Long userId;
    @NonNull
    @NotBlank(message = "The email is required.")
    @Email(message = "The email should be valid.")
    @Size(min = 10, max = 300, message = "The email length should be between 10 and 300.")
    private String oldEmail;
    @Email(message = "The email should be valid.")
    private String newEmail;
    @NonNull
    @NotBlank(message = "The username is required.")
    @Pattern(regexp = "^[a-z,A-Z,0-9]{8,150}$", message = "The username should contains lowercase, uppercase and digits.")
    @Size(min = 8, max = 150, message = "The username length should be between 8 and 150 characters.")
    private String oldUsername;
    @Pattern(regexp = "^[a-z,A-Z,0-9]{8,150}$", message = "The username should contains lowercase, uppercase and digits.")
    private String newUsername;
    @NonNull
    @NotBlank(message = "The password is required.")
    @Pattern(regexp = "^[a-z,A-Z,0-9]{8,150}$", message = "The password should contains lowercase and uppercase.")
    @Size(min = 8, max = 150, message = "The password length should be between 8 and 150 characters.")
    private String oldPassword;
    @Pattern(regexp = "^[a-z,A-Z,0-9]{8,150}$", message = "The re-entered password should contains lowercase and uppercase.")
    private String newPassword;
    @Pattern(regexp = "^[a-z,A-Z,0-9]{8,150}$", message = "The re-entered password should contains lowercase and uppercase.")
    private String reEnteredNewPassword;
}