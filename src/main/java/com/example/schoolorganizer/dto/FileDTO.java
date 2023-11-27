package com.example.schoolorganizer.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FileDTO {
    @EqualsAndHashCode.Include
    private Long fileId;
    @NonNull
    private LocalDate date;
    private String name;
    @NonNull
    private String extension;
    @NonNull
    private String artificialName;
    @NonNull
    private String path;
    private NotebookDTO addedInNotebook;
    private List<TaskDTO> addedInTask;
}
