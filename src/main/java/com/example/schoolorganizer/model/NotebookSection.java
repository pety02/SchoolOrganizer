package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
public class NotebookSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notebookSectionId;
    @Column(nullable = false)
    private LocalDate date;
    @Column(length = 100)
    private String title;
    @Column(length = 100000)
    private String content;
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<File> files;
    @ManyToOne
    private Notebook addedInNotebook;
}
