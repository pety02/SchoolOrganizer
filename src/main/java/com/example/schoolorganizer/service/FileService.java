package com.example.schoolorganizer.service;

import com.example.schoolorganizer.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface FileService {
    FileDTO uploadFile(MultipartFile file, Long taskId) throws IOException, NoSuchElementException;
}