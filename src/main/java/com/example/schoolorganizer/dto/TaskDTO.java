package com.example.schoolorganizer.dto;

import jakarta.validation.constraints.*;
import jdk.jfr.BooleanFlag;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * This class describes a task DTO.
 *
 * @author Petya Licheva
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskDTO {
    @EqualsAndHashCode.Include
    private Long taskId;
    @Size(min = 1, max = 200, message = "The title length should be between 1 and 200 letters.")
    private String title;
    @DateTimeFormat
    private LocalDate startDate;
    @DateTimeFormat
    private LocalDate finishDate;
    @Size(min = 1, max = 1000, message = "The description length should be between 1 and 1000 characters.")
    private String description;
    @BooleanFlag
    private Boolean isFinished;
    private List<FileDTO> files;
    private UserDTO createdBy;

    /**
     * @return TaskDTO object in JSON string format.
     */
    @Override
    public String toString() {
        return "{" +
                "\"taskId\": " + taskId +
                ", \"title\": " + title +
                ", \"startDate\": " + startDate +
                ", \"finishDate\": " + finishDate +
                ", \"description\": " + description +
                ", \"isFinished\": " + isFinished +
                ", \"files\": " + Arrays.toString(files.toArray()) +
                ", \"createdBy\": " + createdBy +
                '}';
    }
}