package com.example.schoolorganizer.adapter.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.CalendarEventDTO;
import com.example.schoolorganizer.dto.UserDTO;
import com.example.schoolorganizer.model.CalendarEvent;
import com.example.schoolorganizer.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * This class describes CalendarEventAdapterImpl. A class that
 * transforms a CalendarEvent to a CalendarEventDTO and vice versa.
 *
 * @author Petya Licheva
 */
@Component
public class CalendarEventAdapterImpl implements IAdapter<CalendarEvent, CalendarEventDTO> {
    private final IAdapter<User, UserDTO> userAdapter;

    /**
     * General purpose constructor for CalendarEventAdapterImpl class.
     *
     * @param userAdapter an autowired user adapter.
     */
    @Autowired
    public CalendarEventAdapterImpl(IAdapter<User, UserDTO> userAdapter) {
        this.userAdapter = userAdapter;
    }

    /**
     * Method that transforms calendar event entity to calendar event DTO.
     *
     * @param entity calendar event entity object.
     * @return the calendar event DTO object.
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
     * Method that transforms from calendar event DTO to entity.
     *
     * @param calendarEventDTO the calendar event DTO object.
     * @return the calendar event entity object.
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