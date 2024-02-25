package com.example.schoolorganizer.adapter.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.TaskDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.model.Task;
import com.example.schoolorganizer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * This class describes TaskAdapterImpl. A class that
 * transforms a Task to a TaskDTO and vice versa.
 *
 * @author Petya Licheva
 */
@Component
public class TaskAdapterImpl implements IAdapter<Task, TaskDTO> {
    private final IAdapter<User, UserDTO> userAdapter;

    /**
     * General purpose constructor of TaskAdapterImpl class.
     *
     * @param userAdapter a user adapter object.
     */
    @Autowired
    public TaskAdapterImpl(IAdapter<User, UserDTO> userAdapter) {
        this.userAdapter = userAdapter;
    }

    /**
     * This method transforms a Task entity object to a TaskDTO object.
     *
     * @param entity a Task entity object.
     * @return a TaskDTO object.
     */
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
        dto.setCreatedBy(userAdapter.fromEntityToDTO(entity.getCreatedBy()));
        dto.setIsFinished(entity.getFinished());

        return dto;
    }

    /**
     * This method transforms a TaskDTO object to a Task entity object.
     *
     * @param taskDTO a TaskDTO object.
     * @return a Task entity object.
     */
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
        entity.setCreatedBy(userAdapter.fromDTOToEntity(taskDTO.getCreatedBy()));

        return entity;
    }
}