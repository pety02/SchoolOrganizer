package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class NotebookSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public NotebookSection() {
    }
    public NotebookSection(final LocalDate date, final String title, final String content,
                           final List<File> files, final Notebook addedInNotebook) {
        setDate(date);
        setTitle(title);
        setContent(content);
        setFiles(files);
        setAddedInNotebook(addedInNotebook);
    }
    public NotebookSection(final NotebookSection that) {
        this(that.getDate(), that.getTitle(), that.getContent(),
                that.getFiles(), that.getAddedInNotebook());
        setNotebookSectionId(that.getNotebookSectionId());
    }

    public Long getNotebookSectionId() {
        return notebookSectionId;
    }

    public void setNotebookSectionId(final Long notebookSectionId) {
        if(notebookSectionId <= 0 ) {
            throw new IllegalArgumentException("Invalid notebookSectionId!");
        }

        this.notebookSectionId = notebookSectionId;
    }

    public LocalDate getDate() {
        return LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
    }

    public void setDate(final LocalDate date) {
        if(date == null ||
                date.isBefore(LocalDate.of(1970, 1, 1))) {
            throw new IllegalArgumentException("Invalid date!");
        }
        this.date = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
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

    public String getContent() {
        return content;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public List<File> getFiles() {
        return new ArrayList<>(files);
    }

    public void setFiles(final List<File> files) {
        this.files = new ArrayList<>(files);
    }

    public Notebook getAddedInNotebook() {
        return addedInNotebook;
    }

    public void setAddedInNotebook(final Notebook addedInNotebook) {
        this.addedInNotebook = new Notebook(addedInNotebook);
    }
}
