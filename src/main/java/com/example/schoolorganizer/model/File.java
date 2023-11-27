package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "FILES", schema = "SCHOOL_ORGANIZER")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;
    @Column(nullable = false)
    private LocalDate date;
    @Column
    private String name;
    @Column(nullable = false)
    private String extension;
    @Column(nullable = false)
    private String artificialName;
    @Column(nullable = false)
    private String path;
    @ManyToOne
    private Notebook addedInNotebook;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Task> addedInTask;
}
