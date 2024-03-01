package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.util.List;
import java.time.LocalDate;

/**
 * This class describes the tasks.
 *
 * @author Petya Licheva
 */
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
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<File> files;
    @ManyToOne
    private User createdBy;

    /**
     * Default constructor of the Task class.
     */
    public Task() {
    }

    /**
     * General purpose of the Task class.
     *
     * @param title       the title of the Task.
     * @param startDate   the start date of the Task.
     * @param finishDate  the finish date of the Task.
     * @param description the description of the Task.
     * @param isFinished  the status of the Task.
     * @param files       the list of Files connected with the Task.
     * @param createdBy   the creator of the Task.
     */
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

    /**
     * Get method of the Task id.
     *
     * @return the id of the Task.
     */
    public Long getTaskId() {
        return taskId;
    }

    /**
     * Set method of the Task id. It is used for setting
     * the Task id when the Hibernate ORM generates it.
     *
     * @param taskId the id of the Task.
     */
    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    /**
     * Get method for the Task title.
     *
     * @return the Task title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set method for the Task title.
     *
     * @param title the title of the Task.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get method for the Task start date.
     *
     * @return the start date of the Task.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Set method for the start date of the Task.
     *
     * @param startDate the start date of the Task.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Get method for the Task finish date.
     *
     * @return the finish date of the Task.
     */
    public LocalDate getFinishDate() {
        return finishDate;
    }

    /**
     * Set method for the Task finsih date.
     *
     * @param finishDate the finish date of the Task.
     */
    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    /**
     * Get method for the Task description.
     *
     * @return the Task description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Set method for the Task description.
     *
     * @param description the Task description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Get method for the Task status.
     *
     * @return the status of the Task.
     */
    public Boolean getFinished() {
        return isFinished;
    }

    /**
     * Set method for the Task status.
     *
     * @param finished the status of the Task.
     */
    public void setFinished(Boolean finished) {
        isFinished = finished;
    }

    /**
     * Get method for the list of files connected with the Task.
     *
     * @return the list of files connected with the Task.
     */
    public List<File> getFiles() {
        return files;
    }

    /**
     * Set method for the list of files connected with the Task.
     *
     * @param files the list of files connected with the Task.
     */
    public void setFiles(List<File> files) {
        this.files = files;
    }

    /**
     * Get method for the Task creator.
     *
     * @return the Task creator.
     */
    public User getCreatedBy() {
        return createdBy;
    }

    /**
     * Set method for the Task creator.
     *
     * @param createdBy the Task creator.
     */
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}