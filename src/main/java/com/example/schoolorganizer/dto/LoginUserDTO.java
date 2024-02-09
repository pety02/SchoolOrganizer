package com.example.schoolorganizer.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LoginUserDTO {
    @EqualsAndHashCode.Include
    @Positive(message = "Id should be positive!")
    private Long userId;
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
}