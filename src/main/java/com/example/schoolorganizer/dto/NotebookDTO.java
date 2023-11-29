package com.example.schoolorganizer.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NotebookDTO {
    @EqualsAndHashCode.Include
    @Id
    @Min(value = 1, message = "The notebookId should be bigger or equal to 1.")
    private Long notebookId;
    @NonNull
    @DateTimeFormat
    private LocalDate date;
    @Pattern(regexp = "^[A-Z]+([ '-][a-zA-Z]+)*$", message = "The title should starts with capital letter.")
    @Size(min = 1, max = 100, message = "The title length should be between 1 and 100 letters.")
    private String title;
    @Pattern(regexp = "^[A-Z]+([ '-][a-zA-Z]+)*$", message = "The subject name should starts with capital letter.")
    @Size(min = 1, max = 120, message = "The subject name length should be between 1 and 120 letters.")
    private String subject;
    private List<NotebookSectionDTO> sections;
    private UserDTO createdBy;
}