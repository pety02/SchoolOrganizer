package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @OneToOne
    private User createdBy;

    public Notebook() {
    }

    public Notebook(final LocalDate date, final String title, final String subject,
                    final List<NotebookSection> sections, final User createdBy) {
        setDate(date);
        setTitle(title);
        setSubject(subject);
        setSections(sections);
        setCreatedBy(createdBy);
    }
    public Notebook(final Notebook that) {
        this(that.getDate(), that.getTitle(), that.getSubject(),
                that.getSections(), that.getCreatedBy());
        setNotebookId(that.getNotebookId());
    }

    public Long getNotebookId() {
        return notebookId;
    }

    public void setNotebookId(final Long notebookId) {
        this.notebookId = notebookId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(final LocalDate date) {
        this.date = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public List<NotebookSection> getSections() {
        return new ArrayList<>(sections);
    }

    public void setSections(final List<NotebookSection> sections) {
        this.sections = new ArrayList<>(sections);
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(final User createdBy) {
        this.createdBy = new User(createdBy);
    }
}
