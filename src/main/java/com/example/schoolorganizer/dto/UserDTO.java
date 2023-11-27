package com.example.schoolorganizer.dto;

import lombok.*;

import java.util.List;
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class UserDTO {
    @EqualsAndHashCode.Include
    private Long userId;
    private String name;
    private String surname;
    @NonNull
    private String email;
    @NonNull
    private String username;
    @NonNull
    private String password;
    private List<TaskDTO> tasks;
    private List<NotebookDTO> notebooks;
    private List<UserDTO> friendsList;
}
