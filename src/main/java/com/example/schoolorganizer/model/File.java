package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;
    @Column(nullable = false)
    private LocalDate date;
    @Column(length = 100)
    private String name;
    @Column(nullable = false, length = 20)
    private String extension;
    @Column(nullable = false, length = 100)
    private String artificialName;
    @Column(nullable = false, length = 1000)
    private String path;
    @ManyToOne
    private Notebook addedInNotebook;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Task> addedInTask;
}