package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

/**
 * This class describes a notebook.
 *
 * @author Petya Licheva
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
     * General purpose constructor of the Notebooks class.
     *
     * @param date      - the creation date of the Notebook.
     * @param title     - the title of the Notebook.
     * @param subject   - the subject of the Notebook.
     * @param sections  - the list of sections of the Notebook.
     * @param createdBy - the creator of the Notebook.
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
     * Get method for the Notebook id.
     *
     * @return the Notebook id from the database.
     */
    public Long getNotebookId() {
        return notebookId;
    }

    /**
     * Set method for the Notebook id
     *
     * @param notebookId - the Notebook id. Used to set the id of
     *                   the Notebook after the Hibernate ORM generates it.
     */
    public void setNotebookId(Long notebookId) {
        this.notebookId = notebookId;
    }

    /**
     * Get method for Notebook creation date.
     *
     * @return the Notebook creation date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Set method for the Notebook creation date.
     *
     * @param date - the Notebook creation date.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Get method for the Notebook title.
     *
     * @return the Notebook title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set method for the Notebook title.
     *
     * @param title - the Notebook title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get method for the Notebook subject.
     *
     * @return the Notebook subject.
     */
    public String getSubject() {
        return subject;
    }

    /**
     * Set method for the Notebook subject.
     *
     * @param subject - the Notebook subject.
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     * Get method for the Notebook list of sections.
     *
     * @return the Notebook list of sections.
     */
    public List<NotebookSection> getSections() {
        return sections;
    }

    /**
     * Set method for the Notebook list of sections.
     *
     * @param sections - the Notebook list of sections.
     */
    public void setSections(List<NotebookSection> sections) {
        this.sections = sections;
    }

    /**
     * Get method for the Notebook creator.
     *
     * @return the Notebook creator.
     */
    public User getCreatedBy() {
        return createdBy;
    }

    /**
     * Set method for the Notebook creator.
     *
     * @param createdBy - the Notebook creator.
     */
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}