package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

/**
 * This class describes notebook section.
 *
 * @author Petya Licheva
 */
@Entity
@Table
public class NotebookSection {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long notebookSectionId;
    @Column(nullable = false)
    private LocalDate date;
    @Column(length = 100)
    private String title;
    @Column(length = 100000)
    private String content;
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<File> files;
    @ManyToOne
    private Notebook addedInNotebook;

    /**
     * Default constructor of the NotebookSection class.
     */
    public NotebookSection() {
    }

    /**
     * General purpose constructor of the NotebookSection class.
     *
     * @param date            the creation date.
     * @param title           the title of the section.
     * @param content         the content of the section.
     * @param files           the list of Files of the section.
     * @param addedInNotebook the Notebooks in which the section is included.
     */
    public NotebookSection(final LocalDate date, final String title, final String content,
                           final List<File> files, final Notebook addedInNotebook) {
        setDate(date);
        setTitle(title);
        setContent(content);
        setFiles(files);
        setAddedInNotebook(addedInNotebook);
    }

    /**
     * Get method of the NotebookSection id.
     *
     * @return the NotebookSection id from the database.
     */
    public Long getNotebookSectionId() {
        return notebookSectionId;
    }

    /**
     * Set method for the NotebookSection id. Used to set the id when
     * Hibernates ORM generates it.
     *
     * @param notebookSectionId the NotebookSection id.
     */
    public void setNotebookSectionId(Long notebookSectionId) {
        this.notebookSectionId = notebookSectionId;
    }

    /**
     * Get method for the NotebookSection creation date.
     *
     * @return the NotebooksSection creation date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Set method for the NotebookSection creation date.
     *
     * @param date the NotebookSection creation date.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Get method for the NotebookSection title.
     *
     * @return the NotebookSection title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set method for the NotebookSection title.
     *
     * @param title the NotebookSection title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get method for the NotebookSection content.
     *
     * @return the NotebookSection content.
     */
    public String getContent() {
        return content;
    }

    /**
     * Set method for the NotebookSection content.
     *
     * @param content the NotebookSection content.
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * Get method for the NotebookSection list of files.
     *
     * @return the list of NotebookSection files.
     */
    public List<File> getFiles() {
        return files;
    }

    /**
     * Set method for the NotebookSection list of files.
     *
     * @param files the NotebookSection list of files.
     */
    public void setFiles(List<File> files) {
        this.files = files;
    }

    /**
     * Get method for the Notebook in which the section is included.
     *
     * @return the Notebook in which the section is included.
     */
    public Notebook getAddedInNotebook() {
        return addedInNotebook;
    }

    /**
     * Set method for the Notebook in which the section is included.
     *
     * @param addedInNotebook the Notebook in which the section is included.
     */
    public void setAddedInNotebook(Notebook addedInNotebook) {
        this.addedInNotebook = addedInNotebook;
    }
}