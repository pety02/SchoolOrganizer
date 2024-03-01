package com.example.schoolorganizer.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Arrays;

/**
 * This class describes a file DTO.
 *
 * @author Petya Licheva
 */
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FileDTO {
    @EqualsAndHashCode.Include
    @Positive(message = "Id should be positive!")
    private Long fileId;
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
    @Size(min = 1, max = 1000)
    private byte[] data;
    @NonNull
    @NotBlank(message = "The path is required.")
    @Pattern(regexp = "^.*[^\\w -.].*$", message = "The path contains words separated with \\.")
    @Size(min = 1, max = 1000, message = "The path length should be between 1 and 1000 characters.")
    private String path;

    /**
     * @return the file DTO object in JSON string format.
     */
    @Override
    public String toString() {
        return "{" +
                "\"fileId\": " + fileId +
                ", \"date\": " + date +
                ", \"name\": " + name +
                ", \"extension\": " + extension +
                ", \"data\": " + Arrays.toString(data) +
                ", \"path\": " + path +
                '}';
    }
}