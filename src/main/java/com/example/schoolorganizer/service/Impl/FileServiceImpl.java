package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.FileDTO;
import com.example.schoolorganizer.dto.TaskDTO;
import com.example.schoolorganizer.model.File;
import com.example.schoolorganizer.model.Task;
import com.example.schoolorganizer.repository.FileRepository;
import com.example.schoolorganizer.repository.TaskRepository;
import com.example.schoolorganizer.service.FileService;
import com.example.schoolorganizer.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.List;

/**
 *
 */
@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepo;
    private final IAdapter<File, FileDTO> fileAdapter;
    private final TaskRepository taskRepo;
    private final IAdapter<Task, TaskDTO> taskAdapter;

    private static final String UPLOAD_DIR = "C:\\Users\\User\\OneDrive\\Documents\\University\\2kurs_3semestur\\Spring Boot\\SchoolOrganizer\\uploads\\";

    @Autowired
    public FileServiceImpl(FileRepository fileRepo, IAdapter<File, FileDTO> fileAdapter, TaskRepository taskRepo, IAdapter<Task, TaskDTO> taskAdapter) {
        this.fileRepo = fileRepo;
        this.fileAdapter = fileAdapter;
        this.taskRepo = taskRepo;
        this.taskAdapter = taskAdapter;
    }

    @Override
    public FileDTO uploadFile(MultipartFile file, Long taskId) throws IOException, NoSuchElementException {
        Task taskParent = taskRepo.findById(taskId).orElseThrow();
        List<Task> tasks = new ArrayList<>();
        tasks.add(taskParent);

        File saved = fileRepo.save(File.builder()
                .fileId(null)
                .date(LocalDate.now())
                .name(file.getOriginalFilename())
                .extension(file.getContentType())
                .path(UPLOAD_DIR + file.getOriginalFilename())
                .addedInTask(new ArrayList<>())
                .addedInNotebookSections(new ArrayList<>())
                .build());
        String filePath = UPLOAD_DIR + file.getOriginalFilename();
        Path destPath = Paths.get(filePath);
        Files.copy(file.getInputStream(), destPath);
        FileDTO f = fileAdapter.fromEntityToDTO(saved);
        taskParent.getFiles().add(saved);
        saved.getAddedInTask().add(taskParent);

        taskRepo.save(taskParent);
        fileRepo.save(saved);

        return fileAdapter.fromEntityToDTO(saved);
    }
}