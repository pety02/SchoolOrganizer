package com.example.schoolorganizer.service.Impl;

import com.example.schoolorganizer.adapter.IAdapter;
import com.example.schoolorganizer.dto.CalendarEventDTO;
import com.example.schoolorganizer.model.CalendarEvent;
import com.example.schoolorganizer.model.User;
import com.example.schoolorganizer.repository.CalendarEventRepository;
import com.example.schoolorganizer.repository.UserRepository;
import com.example.schoolorganizer.service.CalendarEventService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * This class describes CalendarEventServiceImpl class
 *
 * @author Petya Licheva
 */
@Service
public class CalendarEventServiceImpl implements CalendarEventService {
    private final CalendarEventRepository eventRepository;
    private final UserRepository userRepo;
    private final IAdapter<CalendarEvent, CalendarEventDTO> calendarEventAdapter;

    /**
     * General purpose constructor of CalendarEventServiceImpl class.
     *
     * @param eventRepository      an event repository.
     * @param userRepo             a user repository.
     * @param calendarEventAdapter a calendar event adapter.
     */
    @Autowired
    public CalendarEventServiceImpl(CalendarEventRepository eventRepository,
                                    UserRepository userRepo,
                                    IAdapter<CalendarEvent, CalendarEventDTO>
                                            calendarEventAdapter) {
        this.eventRepository = eventRepository;
        this.userRepo = userRepo;
        this.calendarEventAdapter = calendarEventAdapter;
    }

    /**
     * This method gets all events by definite user's id.
     *
     * @param id a user id.
     * @return a list of calendar events dto objects.
     */
    @Override
    public List<CalendarEventDTO> getAllEventsByUserId(Long id) {
        List<CalendarEvent> events = eventRepository.findAllByCreatedByUserId(id);
        List<CalendarEventDTO> eventsDTOs = new ArrayList<>();
        for (var currentEvent : events) {
            CalendarEventDTO currentEventDTO = calendarEventAdapter.fromEntityToDTO(currentEvent);
            eventsDTOs.add(currentEventDTO);
        }

        return eventsDTOs;
    }

    /**
     * This method creates a calendar event by definite user's id.
     *
     * @param id       a definite user's id.
     * @param eventDTO a calendar event dto object.
     * @return an optional type of CalendarEventDTO object.
     * @throws IllegalArgumentException if the start date of the event is after
     *                                  the finish date of the event the method throws an IllegalArgumentException
     *                                  with a definite message.
     */
    @Transactional
    @Override
    public Optional<CalendarEventDTO> createEventByUserId(Long id, CalendarEventDTO eventDTO)
            throws IllegalArgumentException {
        if (eventDTO.getEndDate().isBefore(eventDTO.getStartDate())) {
            throw new IllegalArgumentException("The start date of the event should be before the end date.");
        }
        CalendarEvent event = calendarEventAdapter.fromDTOToEntity(eventDTO);
        User createdBy = userRepo.findByUserId(id).orElseThrow();
        event.setCreatedBy(createdBy);
        return Optional.of(calendarEventAdapter
                .fromEntityToDTO(eventRepository.save(event)));
    }

    /**
     * This method finds all calendar events with a same title and created by
     * same user.
     *
     * @param id    a definite user's id.
     * @param title a title to search for.
     * @return a list of CalendarEventsDTO matches these criteria.
     */
    @Override
    public List<CalendarEventDTO> searchByTitle(Long id, String title) {
        List<CalendarEvent> events = eventRepository.findAllByCreatedByUserIdAndTitle(id, title);
        List<CalendarEventDTO> eventsDTOs = new ArrayList<>();
        for (CalendarEvent currentEvent : events) {
            eventsDTOs.add(calendarEventAdapter.fromEntityToDTO(currentEvent));
            System.out.println(currentEvent.getTitle());
        }

        return eventsDTOs;
    }

    /**
     * This method deletes a calendar event by its id.
     *
     * @param id the calendar event's id.
     */
    @Override
    public void deleteByID(Long id) {
        eventRepository.deleteById(id);
    }
}