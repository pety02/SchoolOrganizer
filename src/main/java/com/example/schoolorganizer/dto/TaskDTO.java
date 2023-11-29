package com.example.schoolorganizer.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import jdk.jfr.BooleanFlag;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskDTO {
    @EqualsAndHashCode.Include
    @Id
    @Min(value = 1, message = "The taskId should be number bigger or equal to 1.")
    private Long taskId;
    @Pattern(regexp = "^[A-Z]+([ '-][a-zA-Z]+)*$", message = "The title should starts with capital letter.")
    @Size(min = 1, max = 200, message = "The title length should be between 1 and 200 letters.")
    private String title;
    @NonNull
    @DateTimeFormat
    private LocalDate startDate;
    @NonNull
    @DateTimeFormat
    private LocalDate finishDate;
    @Size(min = 0, max = 10000)
    private String description;
    @NonNull
    @BooleanFlag
    private Boolean isFinished;
    private List<FileDTO> files;
    private UserDTO createdBy;
}
