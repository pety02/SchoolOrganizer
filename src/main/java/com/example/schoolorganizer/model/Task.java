package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.util.List;
import java.time.LocalDate;

@Entity
@Table(name = "TASKS", schema = "SCHOOL_ORGANIZER")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate finishDate;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Boolean isFinished;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name="id")
    private List<File> files;
    @ManyToOne
    private User createdBy;
}
