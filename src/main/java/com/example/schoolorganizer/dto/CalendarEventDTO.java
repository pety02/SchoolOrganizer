package com.example.schoolorganizer.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
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

    @Override
    public String toString() {
        return "{" +
                "\"calendarEventId\": " + calendarEventId +
                ", \"title\": \"" + title + '\"' +
                ", \"startDate\": \"" + startDate + '\"' +
                ", \"endDate\": \"" + endDate + '\"' +
                ", \"color\": \"" + color + '\"' +
                ", \"createdBy\": " + (createdBy != null ? "{}" : "null") +
                '}';
    }
}