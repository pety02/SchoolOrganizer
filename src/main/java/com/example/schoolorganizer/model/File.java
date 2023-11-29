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
        if(fileId <= 0) {
            throw new IllegalArgumentException("Invalid notebookId!");
        }
        this.fileId = fileId;
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
        if(extension == null || extension.isBlank() || extension.charAt(0) != '.'
            || (extension.charAt(0) == '.' && extension.substring(1, extension.length() - 1).matches("^[a-z,A-Z]$"))) {
            throw new IllegalArgumentException("Invalid extension!");
        }
        this.extension = extension;
    }

    public String getArtificialName() {
        return artificialName;
    }

    public void setArtificialName(final String artificialName) {
        if(artificialName == null || artificialName.isBlank()) {
            throw new IllegalArgumentException("Invalid artificialName!");
        }
        this.artificialName = artificialName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        if(path == null || path.isBlank()
                || !path.matches("^(?:[a-zA-Z]:\\\\\\\\|\\\\\\\\\\\\\\\\[^\\\\\\\\]+\\\\\\\\[^\\\\\\\\]+)\\\\\\\\(?:[^\\\\\\\\]+\\\\\\\\)*[^\\\\\\\\]+$")
                || !path.matches("^/[^/]+(/[^/]+)*$")) {
            throw new IllegalArgumentException("Invalid path!");
        }
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