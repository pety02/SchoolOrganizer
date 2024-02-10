package com.example.schoolorganizer.adapter.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.LoginUserDTO;
import com.example.schoolorganizer.dto.TaskDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.model.Task;
import com.example.schoolorganizer.model.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TaskAdapterImpl implements IAdapter<Task, TaskDTO> {
    private final IAdapter<User, UserDTO> userDAO;

    public TaskAdapterImpl(IAdapter<User, UserDTO> userDAO) {
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
