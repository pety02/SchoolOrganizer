package com.example.schoolorganizer.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaskDTO {
    @EqualsAndHashCode.Include
    private Long taskId;
    private String title;
    @NonNull
    private LocalDate startDate;
    @NonNull
    private LocalDate finishDate;
    private String description;
    @NonNull
    private Boolean isFinished;
    private List<FileDTO> files;
    private UserDTO createdBy;
}
