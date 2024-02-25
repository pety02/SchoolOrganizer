package com.example.schoolorganizer.dto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * This class describes a notebook DTO.
 *
 * @author Petya Licheva
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NotebookDTO {
    @EqualsAndHashCode.Include
    @Positive(message = "Id should be positive!")
    private Long notebookId;
    @NonNull
    @DateTimeFormat
    private LocalDate date;
    @Size(min = 1, max = 100, message = "The title length should be between 1 and 100 letters.")
    private String title;
    @Size(min = 1, max = 120, message = "The subject name length should be between 1 and 120 letters.")
    private String subject;
    private List<NotebookSectionDTO> sections;
    private UserDTO createdBy;

    /**
     * @return the NotebookDTO in JSON string format.
     */
    @Override
    public String toString() {
        return "{" +
                "\"notebookId\": " + notebookId +
                ", \"date\": " + date +
                ", \"title\": " + title +
                ", \"subject\": " + subject +
                ", \"sections\": " + Arrays.toString(sections.toArray()) +
                ", \"createdBy\": " + createdBy +
                '}';
    }
}