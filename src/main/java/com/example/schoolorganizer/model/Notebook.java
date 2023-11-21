package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "NOTEBOOKS", schema = "SCHOOL_ORGANIZER")
public class Notebook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column
    private String subject;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private List<NotebookSection> sections;
    @ManyToOne
    private User createdBy;
}
