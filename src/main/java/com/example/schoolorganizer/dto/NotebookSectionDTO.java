package com.example.schoolorganizer.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NotebookSectionDTO {
    @EqualsAndHashCode.Include
    private Long notebookSectionId;
    @NonNull
    private LocalDate date;
    private String title;
    private String content;
    private List<FileDTO> files;
    private NotebookDTO addedInNotebook;
}
