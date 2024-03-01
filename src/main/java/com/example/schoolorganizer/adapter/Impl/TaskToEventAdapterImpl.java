package com.example.schoolorganizer.adapter.Impl;

import com.example.schoolorganizer.dto.CalendarEventDTO;
import com.example.schoolorganizer.dto.TaskDTO;
import org.springframework.stereotype.Component;

/**
 * This class describes TaskToEventAdapter.
 *
 * @author Petya Licheva
 */
@Component
public class TaskToEventAdapterImpl {

    /**
     * This method converts a task to a calendar event.
     *
     * @param dto the task to be converted.
     * @return the converted task in calendar event object.
     */
    public CalendarEventDTO fromEntityToDTO(TaskDTO dto) {
        CalendarEventDTO taskEvent = new CalendarEventDTO();
        taskEvent.setCalendarEventId(dto.getTaskId());
        taskEvent.setTitle(dto.getTitle());
        taskEvent.setStartDate(dto.getStartDate());
        taskEvent.setEndDate(dto.getFinishDate());
        taskEvent.setColor("#B80F0A");
        taskEvent.setCreatedBy(dto.getCreatedBy());
        return taskEvent;
    }
}