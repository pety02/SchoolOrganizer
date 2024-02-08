package com.example.schoolorganizer.dto;

import jakarta.validation.constraints.*;
import jdk.jfr.BooleanFlag;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskDTO {
    @EqualsAndHashCode.Include
    private Long taskId;
    @Pattern(regexp = "^[A-Z]([a-z]+\\s?)+$", message = "The title should starts with capital letter.")
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
    private LoginUserDTO createdBy;
}