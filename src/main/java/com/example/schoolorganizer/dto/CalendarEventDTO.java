package com.example.schoolorganizer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CalendarEventDTO {
    @EqualsAndHashCode.Include
    @Positive(message = "")
    private Long calendarEventId;
    @NonNull
    @NotBlank(message = "")
    @Size(min = 1, max = 500, message = "")
    private String title;
    @DateTimeFormat
    private LocalDate startDate;
    @DateTimeFormat
    private LocalDate endDate;
    @NonNull
    @NotBlank(message = "")
    @Size(min = 1, max = 200, message = "")
    private String color;
    private UserDTO createdBy;
}