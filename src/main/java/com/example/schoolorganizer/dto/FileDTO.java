package com.example.schoolorganizer.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FileDTO {
    @EqualsAndHashCode.Include
    @Id
    @Min(value = 1, message = "The fileId should be bigger or equal to 1.")
    private Long fileId;
    @NonNull
    @DateTimeFormat
    private LocalDate date;
    @Pattern(regexp = "^[A-Z]+([ '-][a-zA-Z]+)*$", message = "The name should starts with capital letter.")
    @Size(min = 1, max = 100, message = "The name length should be between 1 and 100 letters.")
    private String name;
    @NonNull
    @NotBlank(message = "Extension is required.")
    @Pattern(regexp = "^.+[a-z,A-Z]$", message = "The extension must starts with . and continues with lowercase or uppercase.")
    @Size(min = 1, max = 20, message = "The extension length is between 1 and 20 characters.")
    private String extension;
    @NonNull
    @NotBlank(message = "The artificialName is required.")
    @Pattern(regexp = "^[A-Z]+([ '-][a-zA-Z]+)*$", message = "The artificialName contains lowercase, uppercase and special symbols.")
    @Size(min = 1, max = 100, message = "The artificialName length is between 1 and 100 characters.")
    private String artificialName;
    @NonNull
    @NotBlank(message = "The path is required.")
    @Pattern(regexp = "^.*[^\\w -.].*$", message = "The path contains words separated with \\.")
    @Size(min = 1, max = 1000, message = "The path lentght should be between 1 and 1000 characters.")
    private String path;
    private NotebookDTO addedInNotebook;
    private List<TaskDTO> addedInTask;
}