package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.util.ArrayList;
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
    public Task(final Task that) {
        this(that.getTitle(), that.getStartDate(), that.getFinishDate(),
                that.getDescription(), that.getFinished(), that.getFiles(),
                that.getCreatedBy());
        setTaskId(that.getTaskId());
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(final Long taskId) {
        if(taskId <= 0) {
            throw new IllegalArgumentException("Invalid taskId!");
        }

        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        if(title.isBlank()
                || title.charAt(0) < 'A' || 'Z' < title.charAt(0)
                || title.charAt(0) < 'А' || title.charAt(0) < 'Я') {
            throw new IllegalArgumentException("Invalid title!");
        }
        this.title = title;
    }

    public LocalDate getStartDate() {
        return LocalDate.of(startDate.getYear(), startDate.getMonth(), startDate. getDayOfMonth());
    }

    public void setStartDate(final LocalDate startDate) {
        if(startDate == null || startDate.isBefore(LocalDate.of(1970, 1, 1))) {
            throw new IllegalArgumentException("Invalid startDate!");
        }
        this.startDate = LocalDate.of(startDate.getYear(), startDate.getMonth(), startDate.getDayOfMonth());
    }

    public LocalDate getFinishDate() {
        return LocalDate.of(finishDate.getYear(), finishDate.getMonth(), finishDate.getDayOfMonth());
    }

    public void setFinishDate(final LocalDate finishDate) {
        if(finishDate == null || finishDate.isBefore(this.startDate)) {
            throw new IllegalArgumentException("Invalid finishDate!");
        }
        this.finishDate = LocalDate.of(finishDate.getYear(), finishDate.getMonth(), finishDate.getDayOfMonth());
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public Boolean getFinished() {
        return isFinished;
    }

    public void setFinished(final Boolean finished) {
        isFinished = finished;
    }

    public List<File> getFiles() {
        return new ArrayList<>(files);
    }

    public void setFiles(final List<File> files) {
        this.files = new ArrayList<>(files);
    }

    public User getCreatedBy() {
        return new User(createdBy);
    }

    public void setCreatedBy(final User createdBy) {
        this.createdBy = new User(createdBy);
    }
}
