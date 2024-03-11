package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.FileDTO;
import com.example.schoolorganizer.model.File;
import com.example.schoolorganizer.model.NotebookSection;
import com.example.schoolorganizer.model.Task;
import com.example.schoolorganizer.repository.FileRepository;
import com.example.schoolorganizer.repository.NotebookSectionRepository;
import com.example.schoolorganizer.repository.TaskRepository;
import com.example.schoolorganizer.service.FileService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 *
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepo;
    private final IAdapter<File, FileDTO> fileAdapter;
    private final TaskRepository taskRepo;
    private final NotebookSectionRepository notebookSectionRepo;

    private static final String UPLOAD_DIR = "C:\\Users\\User\\OneDrive\\Documents\\University\\2kurs_3semestur\\Spring Boot\\SchoolOrganizer\\src\\main\\resources\\static\\uploads\\";

    @Autowired
    public FileServiceImpl(FileRepository fileRepo, IAdapter<File, FileDTO> fileAdapter, TaskRepository taskRepo, NotebookSectionRepository notebookSectionRepo) {
        this.fileRepo = fileRepo;
        this.fileAdapter = fileAdapter;
        this.taskRepo = taskRepo;
        this.notebookSectionRepo = notebookSectionRepo;
    }

    @Transactional
    @Override
    public FileDTO uploadFileInTask(MultipartFile file, Long taskId, String fileArtificialName) throws IOException, NoSuchElementException {
        Task taskParent = taskRepo.findById(taskId).orElseThrow();

        File saved = fileRepo.save(File.builder()
                .fileId(null)
                .date(LocalDate.now())
                .name(file.getOriginalFilename())
                .artificialName(fileArtificialName)
                .extension("")
                .path(UPLOAD_DIR + file.getOriginalFilename())
                .addedInTasks(new ArrayList<>())
                .addedInNotebookSections(new ArrayList<>())
                .build());
        String shortExtenstion = "";
        String ext = saved.getPath();
        for (int i = ext.length() - 1; i >= 0; --i) {
            char currentChar = ext.charAt(i);
            if (currentChar == '.') {
                shortExtenstion = ext.substring(i);
                break;
            }
        }
        String filePath = UPLOAD_DIR + saved.getArtificialName() + shortExtenstion;
        Path destPath = Paths.get(filePath);
        Files.copy(file.getInputStream(), destPath);
        List<File> files = new ArrayList<>();
        files.add(saved);
        taskParent.setFiles(files);
        saved.setExtension(shortExtenstion);
        saved.setPath(filePath);
        saved.getAddedInTasks().add(taskParent);

        fileRepo.save(saved);
        taskRepo.save(taskParent);

        return fileAdapter.fromEntityToDTO(saved);
    }

    @Override
    public FileDTO uploadFileInNotebookSection(MultipartFile file, Long notebookSectionId, String fileArtificialName) throws IOException, NoSuchElementException {
        NotebookSection notebookSectionParent = notebookSectionRepo.findById(notebookSectionId).orElseThrow();

        File saved = fileRepo.save(File.builder()
                .fileId(null)
                .date(LocalDate.now())
                .name(file.getOriginalFilename())
                .artificialName(fileArtificialName)
                .extension(file.getContentType())
                .path(UPLOAD_DIR + file.getOriginalFilename())
                .addedInTasks(new ArrayList<>())
                .addedInNotebookSections(new ArrayList<>())
                .build());
        String shortExtenstion = "";
        String ext = saved.getPath();
        for (int i = ext.length() - 1; i >= 0; --i) {
            char currentChar = ext.charAt(i);
            if (currentChar == '.') {
                shortExtenstion = ext.substring(i);
                break;
            }
        }
        String filePath = UPLOAD_DIR + saved.getArtificialName() + shortExtenstion;
        Path destPath = Paths.get(filePath);
        Files.copy(file.getInputStream(), destPath);
        List<File> files = new ArrayList<>();
        files.add(saved);
        notebookSectionParent.setFiles(files);
        saved.setPath(filePath);
        saved.getAddedInNotebookSections().add(notebookSectionParent);

        fileRepo.save(saved);
        notebookSectionRepo.save(notebookSectionParent);

        return fileAdapter.fromEntityToDTO(saved);
    }

    @Override
    public List<FileDTO> getAllTaskFiles(Long id) {
        List<File> files = fileRepo.findAllByAddedInTask(id);
        List<FileDTO> filesDTOs = new ArrayList<>();
        for (File f : files) {
            filesDTOs.add(fileAdapter.fromEntityToDTO(f));
        }

        return filesDTOs;
    }

    @Transactional
    @Override
    public void deleteFile(Long id, Long fileId) {
        File f = fileRepo.findById(fileId).orElseThrow();
        try {
            Task taskParent = taskRepo.findById(id).orElseThrow();
            taskParent.setFiles(null);
            java.io.File dirFile = new java.io.File(f.getPath());

            boolean isDeleted = dirFile.delete();
            if (!isDeleted) {
                throw new Exception("Problem with deleting a file from its absolute path.");
            }
            fileRepo.delete(f);
        } catch (Exception ex) {
            log.error(LocalDate.now() + ": //" + ex.getMessage());
        }
    }
}