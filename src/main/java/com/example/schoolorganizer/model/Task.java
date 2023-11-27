package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.util.List;
import java.time.LocalDate;

@Entity
@Table
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;
    @Column(length = 200)
    private String title;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate finishDate;
    @Column(length = 10000)
    private String description;
    @Column(nullable = false)
    private Boolean isFinished;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<File> files;
    @ManyToOne
    private User createdBy;
}
