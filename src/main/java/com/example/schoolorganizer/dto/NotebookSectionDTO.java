package com.example.schoolorganizer.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * This class describes notebook section DTO.
 *
 * @author Petya Licheva
 */
@Getter
@Setter
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
    @Size(min = 1, max = 100, message = "The title length should be between 1 and 100 letters.")
    private String title;
    @Size(min = 1, max = 100000, message = "The content should be between 1 and 10000 letters.")
    private String content;
    private List<FileDTO> files;

    /**
     * @return NotebookSectionDTO object in JSON string format.
     */
    @Override
    public String toString() {
        return "{" +
                "\"notebookSectionId\": " + notebookSectionId +
                ", \"date\": " + date +
                ", \"title\": " + title +
                ", \"content\": " + content +
                ", \"files\": " + Arrays.toString(files.toArray()) +
                '}';
    }
}