package com.example.schoolorganizer.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NotebookDTO {
    @EqualsAndHashCode.Include
    private Long notebookId;
    @NonNull
    private LocalDate date;
    private String title;
    private String subject;
    private List<NotebookSectionDTO> sections;
    private UserDTO createdBy;
}
