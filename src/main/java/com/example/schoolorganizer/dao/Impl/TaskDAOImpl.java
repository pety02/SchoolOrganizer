package com.example.schoolorganizer.dao.Impl;

import com.example.schoolorganizer.dao.IDAO;
import com.example.schoolorganizer.dto.TaskDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.model.Task;
import com.example.schoolorganizer.model.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TaskDAOImpl implements IDAO<Task, TaskDTO> {
    private final IDAO<User, UserDTO> userDAO;

    public TaskDAOImpl(IDAO<User, UserDTO> userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public TaskDTO fromEntityToDTO(Task entity) {
        if (entity == null) {
            return null;
        }
        TaskDTO dto = new TaskDTO();

        dto.setTaskId(entity.getTaskId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setFiles(new ArrayList<>());
        dto.setStartDate(entity.getStartDate());
        dto.setFinishDate(entity.getFinishDate());
        dto.setCreatedBy(userDAO.fromEntityToDTO(entity.getCreatedBy()));
        dto.setIsFinished(entity.getFinished());

        return dto;
    }

    @Override
    public Task fromDTOToEntity(TaskDTO taskDTO) {
        if (taskDTO == null) {
            return null;
        }
        Task entity = new Task();

        entity.setTaskId(taskDTO.getTaskId());
        entity.setTitle(taskDTO.getTitle());
        entity.setDescription(taskDTO.getDescription());
        entity.setStartDate(taskDTO.getStartDate());
        entity.setFinishDate(taskDTO.getFinishDate());
        entity.setFinished(taskDTO.getIsFinished());
        entity.setFiles(new ArrayList<>());
        entity.setCreatedBy(userDAO.fromDTOToEntity(taskDTO.getCreatedBy()));

        return entity;
    }
}
