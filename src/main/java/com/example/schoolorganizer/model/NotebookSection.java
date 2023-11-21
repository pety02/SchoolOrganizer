package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "NOTEBOOK_SECTIONS", schema = "SCHOOL_ORGANIZER")
public class NotebookSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate date;
    @Column
    private String title;
    @Column
    private String content;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private List<File> files;
    @ManyToOne
    private Notebook addedInNotebook;
}
