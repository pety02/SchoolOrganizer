package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

/**
 * This class describes a notebook.
 */
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
    @ManyToOne
    private User createdBy;

    /**
     * Default constructor of the Notebook.
     */
    public Notebook() {
    }

    /**
     * @param date
     * @param title
     * @param subject
     * @param sections
     * @param createdBy
     */
    public Notebook(final LocalDate date, final String title, final String subject,
                    final List<NotebookSection> sections, final User createdBy) {
        setDate(date);
        setTitle(title);
        setSubject(subject);
        setSections(sections);
        setCreatedBy(createdBy);
    }

    /**
     * @return
     */
    public Long getNotebookId() {
        return notebookId;
    }

    /**
     * @param notebookId
     */
    public void setNotebookId(Long notebookId) {
        this.notebookId = notebookId;
    }

    /**
     * @return
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * @param date
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return
     */
    public String getSubject() {
        return subject;
    }

    /**
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * @return
     */
    public List<NotebookSection> getSections() {
        return sections;
    }

    /**
     * @param sections
     */
    public void setSections(List<NotebookSection> sections) {
        this.sections = sections;
    }

    /**
     * @return
     */
    public User getCreatedBy() {
        return createdBy;
    }

    /**
     * @param createdBy
     */
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}