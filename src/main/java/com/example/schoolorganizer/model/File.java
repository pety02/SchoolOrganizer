package com.example.schoolorganizer.model;

import jakarta.persistence.*;

@Entity
@Table(name = "FILES", schema = "SCHOOL_ORGANIZER")
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
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
    @ManyToOne
    private Task addedInTask;
}
