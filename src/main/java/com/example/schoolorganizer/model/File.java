package com.example.schoolorganizer.model;

import jakarta.persistence.*;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

/**
 * This class describes a file.
 *
 * @author Petya Licheva
 */
@Entity
@Table
@Builder
public class File {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fileId;
    @Column(nullable = false)
    private LocalDate date;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 100)
    private String artificialName;
    @Column(nullable = false, length = 20)
    private String extension;
    @Column(nullable = false, length = 1000)
    private String path;
    @ManyToMany
    private List<NotebookSection> addedInNotebookSections;
    @ManyToMany(fetch = FetchType.LAZY)
    private List<Task> addedInTask;

    /**
     * Default constructor of the File class.
     */
    public File() {
    }

    /**
     * General purpose constructor of the File class.
     *
     * @param date                    the creation or update date file.
     * @param name                    the original name of the file.
     * @param extension               the file's extension.
     * @param path                    the full path of the file.
     * @param addedInNotebookSections the list of notebook sections
     *                                where this file is added.
     * @param addedInTask             the lis of tasks where this file is added.
     */
    public File(LocalDate date, String name, String artificialName, String extension,
                String path, List<NotebookSection> addedInNotebookSections,
                List<Task> addedInTask) {
        this(null, date, name, artificialName, extension, path, addedInNotebookSections, addedInTask);
    }

    /**
     * @param fileId
     * @param date
     * @param name
     * @param extension
     * @param path
     * @param addedInNotebookSections
     * @param addedInTask
     */
    public File(Long fileId, LocalDate date, String name, String artificialName, String extension, String path, List<NotebookSection> addedInNotebookSections, List<Task> addedInTask) {
        setFileId(fileId);
        setDate(date);
        setName(name);
        setArtificialName(artificialName);
        setExtension(extension);
        setPath(path);
        setAddedInNotebookSections(addedInNotebookSections);
        setAddedInTask(addedInTask);
    }

    /**
     * Get method for File id.
     *
     * @return the id of the file from the database.
     */
    public Long getFileId() {
        return fileId;
    }

    /**
     * Set method for the File id. Used to set the id of the file
     * when Hibernate ORM generate it.
     *
     * @param fileId the value of the File id.
     */
    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    /**
     * Get method for the File creation date.
     *
     * @return the File creation date.
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * Set method for the File creation date.
     *
     * @param date the File creation date.
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * Get method for the File original name.
     *
     * @return the file original name.
     */
    public String getName() {
        return name;
    }

    /**
     * Set method for the File original name.
     *
     * @param name the File original name.
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getArtificialName() {
        return artificialName;
    }

    public void setArtificialName(String artificialName) {
        this.artificialName = artificialName;
    }

    /**
     * Get method for the File extension.
     *
     * @return the File extension.
     */
    public String getExtension() {
        return extension;
    }

    /**
     * Set method for the File extension.
     *
     * @param extension the File extension.
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * Get method for the File full path.
     *
     * @return the File full path.
     */
    public String getPath() {
        return path;
    }

    /**
     * Set method for the File full path.
     *
     * @param path the File full path.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Get method for the notebooks sections in which
     * the File is included.
     *
     * @return the lis of the notebooks sections in which
     * the File is included.
     */
    public List<NotebookSection> getAddedInNotebookSections() {
        return addedInNotebookSections;
    }

    /**
     * Set method for the list of notebooks sections in which the File
     * is included.
     *
     * @param addedInNotebookSections the list of notebook sections in
     *                                which the File is included.
     */
    public void setAddedInNotebookSections(List<NotebookSection> addedInNotebookSections) {
        this.addedInNotebookSections = addedInNotebookSections;
    }

    /**
     * Get method for the list of tasks in which the File is included.
     *
     * @return the list of the tasks in which the File is included.
     */
    public List<Task> getAddedInTask() {
        return addedInTask;
    }

    /**
     * Set method for the list of tasks in which the File is included.
     *
     * @param addedInTask the list of tasks in which the File is included.
     */
    public void setAddedInTask(List<Task> addedInTask) {
        this.addedInTask = addedInTask;
    }
}