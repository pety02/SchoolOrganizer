package com.example.schoolorganizer.dto;

import com.example.schoolorganizer.model.UserRole;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * This class describes a User DTO.
 *
 * @author Petya Licheva
 */
@Getter
@Setter
@NoArgsConstructor
//@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDTO {
    @EqualsAndHashCode.Include
    @Positive(message = "Id should be positive!")
    private Long userId;
    @Pattern(regexp = "^[A-Z,a-z]{1,60}$", message = "The name should starts with capital letter.")
    private String name;
    @Pattern(regexp = "^[A-Z,a-z]{1,100}$", message = "The surname should starts with capital letter.")
    private String surname;
    @Email(message = "The email should be valid.")
    private String email;
    @Pattern(regexp = "^[a-zA-Z0-9]{8,150}$", message = "The username should contains lowercase, uppercase and digits.")
    private String username;
    @Pattern(regexp = "^[a-zA-Z0-9]{8,150}$", message = "The password should contains lowercase and uppercase.")
    private String password;
    private List<TaskDTO> tasks;
    private List<NotebookDTO> notebooks;
    private List<UserDTO> friends;
    private Set<UserRole> roles;

    /**
     * @return UserDTO in JSON string format.
     */
    @Override
    public String toString() {
        return "{" +
                "\"userId\": " + userId +
                ", \"name\": \"" + name + '\"' +
                ", \"surname\": \"" + surname + '\"' +
                ", \"email\": \"" + email + '\"' +
                ", \"username\": \"" + username + '\"' +
                ", \"password\": \"" + password + '\"' +
                ", \"tasks\": " + (tasks != null ? Arrays.toString(tasks.toArray()) : "null") +
                ", \"notebooks\": " + (notebooks != null ? Arrays.toString(notebooks.toArray()) : "null") +
                ", \"friends\": " + (friends != null ? Arrays.toString(friends.toArray()) : "null") +
                ", \"roles\": " + "null" +
                '}';
    }
}