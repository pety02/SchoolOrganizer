package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;
    @Column(nullable = false)
    private LocalDate date;
    @Column(length = 100)
    private String name;
    @Column(nullable = false, length = 20)
    private String extension;
    @Column(nullable = false, length = 100)
    private String artificialName;
    @Column(nullable = false, length = 1000)
    private String path;
    @ManyToMany
    private List<NotebookSection> addedInNotebookSections;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Task> addedInTask;

    public File() {
    }

    public File(final LocalDate date, final String name, final String extension,
                final String artificialName, final String path,
                final List<NotebookSection> addedInNotebookSections, final List<Task> addedInTask) {
        setDate(date);
        setName(name);
        setExtension(extension);
        setArtificialName(artificialName);
        setPath(path);
        setAddedInNotebookSections(addedInNotebookSections);
        setAddedInTask(addedInTask);
    }
    public File(final File that) {
        this(that.getDate(), that.getName(), that.getExtension(),
                that.getArtificialName(), that.getPath(), that.getAddedInNotebookSections(),
                that.getAddedInTask());
    }

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(final Long fileId) {
        this.fileId = fileId;
    }

    public LocalDate getDate() {
        return LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
    }

    public void setDate(final LocalDate date) {
        this.date = LocalDate.of(date.getYear(), date.getMonth(), date.getDayOfMonth());
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(final String extension) {
        this.extension = extension;
    }

    public String getArtificialName() {
        return artificialName;
    }

    public void setArtificialName(final String artificialName) {
        this.artificialName = artificialName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public List<NotebookSection> getAddedInNotebookSections() {
        return new ArrayList<NotebookSection>(addedInNotebookSections);
    }

    public void setAddedInNotebookSections(final List<NotebookSection> addedInNotebookSections) {
        this.addedInNotebookSections = new ArrayList<NotebookSection>(addedInNotebookSections);
    }

    public List<Task> getAddedInTask() {
        return addedInTask;
    }

    public void setAddedInTask(final List<Task> addedInTask) {
        this.addedInTask = addedInTask;
    }
}