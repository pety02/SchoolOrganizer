package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "NOTEBOOKS", schema = "SCHOOL_ORGANIZER")
public class Notebook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notebookId;
    @Column(nullable = false)
    private LocalDate date;
    @Column
    private String title;
    @Column
    private String subject;
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST, mappedBy = "userId")
    @JoinColumn(name="userId")
    private List<NotebookSection> sections;
    @OneToOne
    private User createdBy;
}
