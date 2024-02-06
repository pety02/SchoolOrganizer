package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    public Long getNotebookSectionId() {
        return notebookSectionId;
    }

    public void setNotebookSectionId(Long notebookSectionId) {
        this.notebookSectionId = notebookSectionId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public Notebook getAddedInNotebook() {
        return addedInNotebook;
    }

    public void setAddedInNotebook(Notebook addedInNotebook) {
        this.addedInNotebook = addedInNotebook;
    }
}
