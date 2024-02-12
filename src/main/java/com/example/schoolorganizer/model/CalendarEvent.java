package com.example.schoolorganizer.model;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * This class describes a calendar event.
 *
 * @author Petya Licheva
 */
@Entity
@Table
public class CalendarEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long calendarEventId;
    @Column(nullable = false, length = 500)
    private String title;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column
    private LocalDate endDate;
    @Column(length = 200)
    private String color;
    @ManyToOne
    private User createdBy;

    /**
     * Default constructor of CalendarEvent class.
     */
    public CalendarEvent() {
    }

    /**
     * General purpose constructor of CalendarEvent class.
     *
     * @param title     the title of the event.
     * @param startDate the start date of the event.
     * @param endDate   the end date of the event.
     * @param color     the color of the calendar badge for the event.
     * @param createdBy the creator of the event.
     */
    public CalendarEvent(String title, LocalDate startDate, LocalDate endDate, String color, User createdBy) {
        setTitle(title);
        setStartDate(startDate);
        setEndDate(endDate);
        setColor(color);
        setCreatedBy(createdBy);
    }

    /**
     * Get method for the CalendarEvent id.
     *
     * @return the id of the event in the database.
     */
    public Long getCalendarEventId() {
        return calendarEventId;
    }

    /**
     * Set method for the CalendarEvent id. Used to set the id of the
     * CalendarEvent when Hibernate ORM generate it.
     *
     * @param calendarEventId the value of the CalendarEvent id.
     */
    public void setCalendarEventId(Long calendarEventId) {
        this.calendarEventId = calendarEventId;
    }

    /**
     * Get method for the CalendarEvent title.
     *
     * @return the title of the event.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set method for the CalendarEvent title.
     *
     * @param title the CalendarEvent title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get method for the CalendarEvent start date.
     *
     * @return the start date of the event.
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Set method for the CalendarEvent start date.
     *
     * @param startDate the start date of the CalendarEvent.
     */
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    /**
     * Get method for the CalendarEvent end date.
     *
     * @return the end date of the CalendarEvent.
     */
    public LocalDate getEndDate() {
        return endDate;
    }

    /**
     * Set method for the CalendarEvent end Date.
     *
     * @param endDate the CalendarEvent end date.
     */
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /**
     * Get method for the CalendarEvent badge's color.
     *
     * @return the CalendarEvent badge's color.
     */
    public String getColor() {
        return color;
    }

    /**
     * Set method for the CalendarEvent badge's color.
     *
     * @param color the CalendarEvent badge's color value.
     */
    public void setColor(String color) {
        this.color = color;
    }

    /**
     * Get method for CalendarEvent creator.
     *
     * @return the CalendarEvent creator.
     */
    public User getCreatedBy() {
        return createdBy;
    }

    /**
     * Set method for the CalendarEvent creator.
     *
     * @param createdBy the CalendarEvent creator.
     */
    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }
}