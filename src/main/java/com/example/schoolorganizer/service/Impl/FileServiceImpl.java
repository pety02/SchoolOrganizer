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

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 *
 */
@Service
public class FileServiceImpl implements FileService {
    private final FileRepository filesRepo;
    private final TaskRepository tasksRepo;
    private final IAdapter<File, FileDTO> filesAdapter;

    /**
     * @param filesRepo
     * @param tasksRepo
     * @param filesAdapter
     */
    @Autowired
    public FileServiceImpl(FileRepository filesRepo,
                           TaskRepository tasksRepo,
                           IAdapter<File, FileDTO> filesAdapter) {
        this.filesRepo = filesRepo;
        this.tasksRepo = tasksRepo;
        this.filesAdapter = filesAdapter;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public List<FileDTO> getAllFilesByTaskId(Long id) {
        List<File> files = filesRepo.getAllByAddedInTask(id);
        List<FileDTO> fileDTOS = new ArrayList<>();
        for (File currentFile : files) {
            fileDTOS.add(filesAdapter.fromEntityToDTO(currentFile));
        }

        return fileDTOS;
    }

    /**
     * @param fileDTO
     * @param taskId
     * @return
     * @throws NoSuchElementException
     */
    @Override
    public Optional<FileDTO> saveFileInTask(FileDTO fileDTO, Long taskId)
            throws NoSuchElementException {
        Task parentTask = tasksRepo.findById(taskId).orElseThrow();
        var f = Optional.of(filesRepo.save(filesAdapter.fromDTOToEntity(fileDTO))).get();
        f.getAddedInTask().add(parentTask);
        parentTask.getFiles().add(f);
        filesRepo.saveAll(parentTask.getFiles());
        tasksRepo.saveAll(f.getAddedInTask());

        return Optional.of(filesAdapter.fromEntityToDTO(f));
    }

    /**
     * @param id
     * @param fileDTO
     * @param taskId
     * @return
     * @throws NoSuchElementException
     */
    @Override
    public Optional<FileDTO> updateFileInTask(Long id, FileDTO fileDTO, Long taskId)
            throws NoSuchElementException {
        FileDTO toBeUpdated = filesAdapter.fromEntityToDTO(filesRepo.findById(id).orElseThrow());
        return saveFileInTask(toBeUpdated, taskId);
    }

    /**
     * @param id
     */
    @Override
    public void deleteFile(Long id) {
        filesRepo.deleteById(id);
    }
}