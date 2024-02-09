package com.example.schoolorganizer.dto;

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
public class NotebookSectionDTO {
    @EqualsAndHashCode.Include
    @Positive(message = "Id should be positive!")
    private Long notebookSectionId;
    @NonNull
    @DateTimeFormat
    private LocalDate date;
    @Pattern(regexp = "^[A-Z]([a-z]+\\s?)+$", message = "The title should starts with capital letter.")
    @Size(min = 1, max = 100, message = "The title length should be between 1 and 100 letters.")
    private String title;
    @Size(min = 1, max = 100000, message = "The content should be between 1 and 10000 letters.")
    private String content;
    private List<FileDTO> files;
}