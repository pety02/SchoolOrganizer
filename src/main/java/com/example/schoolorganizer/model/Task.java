package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

@Entity
@Table
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
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

    public Task() {
    }

    public Task(final String title, final LocalDate startDate, final LocalDate finishDate,
                final String description, final Boolean isFinished, final List<File> files,
                final User createdBy) {
        setTitle(title);
        setStartDate(startDate);
        setFinishDate(finishDate);
        setDescription(description);
        setFinished(isFinished);
        setFiles(files);
        setCreatedBy(createdBy);
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}
