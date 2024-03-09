package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.FileDTO;
import com.example.schoolorganizer.model.File;
import com.example.schoolorganizer.model.Task;
import com.example.schoolorganizer.repository.FileRepository;
import com.example.schoolorganizer.repository.TaskRepository;
import com.example.schoolorganizer.service.FileService;
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

/**
 *
 */
@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepo;
    private final IAdapter<File, FileDTO> fileAdapter;
    private final TaskRepository taskRepo;

    private static final String UPLOAD_DIR = "C:\\Users\\User\\OneDrive\\Documents\\University\\2kurs_3semestur\\Spring Boot\\SchoolOrganizer\\uploads\\";

    @Autowired
    public FileServiceImpl(FileRepository fileRepo, IAdapter<File, FileDTO> fileAdapter, TaskRepository taskRepo) {
        this.fileRepo = fileRepo;
        this.fileAdapter = fileAdapter;
        this.taskRepo = taskRepo;
    }

    @Override
    public FileDTO uploadFile(MultipartFile file, Long taskId, String fileArtificialName) throws IOException, NoSuchElementException {
        Task taskParent = taskRepo.findById(taskId).orElseThrow();

        File saved = fileRepo.save(File.builder()
                .fileId(null)
                .date(LocalDate.now())
                .name(file.getOriginalFilename())
                .artificialName(fileArtificialName)
                .extension(file.getContentType())
                .path(UPLOAD_DIR + file.getOriginalFilename())
                .addedInTask(new ArrayList<>())
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
        FileDTO f = fileAdapter.fromEntityToDTO(saved);
        taskParent.getFiles().add(saved);
        saved.getAddedInTask().add(taskParent);
        saved.setPath(filePath);
        fileRepo.save(saved);

        taskRepo.save(taskParent);
        fileRepo.save(saved);

        return fileAdapter.fromEntityToDTO(saved);
    }
}