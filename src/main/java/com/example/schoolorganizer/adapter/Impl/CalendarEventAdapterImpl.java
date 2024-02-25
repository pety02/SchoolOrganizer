package com.example.schoolorganizer.adapter.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.CalendarEventDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.model.CalendarEvent;
import com.example.schoolorganizer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class CalendarEventAdapterImpl implements IAdapter<CalendarEvent, CalendarEventDTO> {
    private final IAdapter<User, UserDTO> userAdapter;

    /**
     * @param userAdapter
     */
    @Autowired
    public CalendarEventAdapterImpl(IAdapter<User, UserDTO> userAdapter) {
        this.userAdapter = userAdapter;
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public CalendarEventDTO fromEntityToDTO(CalendarEvent entity) {
        if (entity == null) {
            return null;
        }
        CalendarEventDTO dto = new CalendarEventDTO();
        dto.setCalendarEventId(entity.getCalendarEventId());
        dto.setTitle(entity.getTitle());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setColor(entity.getColor());
        dto.setCreatedBy(userAdapter.fromEntityToDTO(entity.getCreatedBy()));
        return dto;
    }

    /**
     * @param calendarEventDTO
     * @return
     */
    @Override
    public CalendarEvent fromDTOToEntity(CalendarEventDTO calendarEventDTO) {
        if (calendarEventDTO == null) {
            return null;
        }
        CalendarEvent entity = new CalendarEvent();
        entity.setCalendarEventId(calendarEventDTO.getCalendarEventId());
        entity.setTitle(calendarEventDTO.getTitle());
        entity.setStartDate(calendarEventDTO.getStartDate());
        entity.setEndDate(calendarEventDTO.getEndDate());
        entity.setColor(calendarEventDTO.getColor());
        entity.setCreatedBy(userAdapter.fromDTOToEntity(calendarEventDTO.getCreatedBy()));
        return entity;
    }
}