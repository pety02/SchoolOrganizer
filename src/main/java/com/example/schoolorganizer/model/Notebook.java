package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table
public class Notebook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notebookId;
    @Column(nullable = false)
    private LocalDate date;
    @Column(length = 100)
    private String title;
    @Column(length = 120)
    private String subject;
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<NotebookSection> sections;
    @OneToOne
    private User createdBy;
}
