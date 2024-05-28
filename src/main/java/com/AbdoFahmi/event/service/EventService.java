package com.AbdoFahmi.event.service;

import com.AbdoFahmi.event.model.Event;

import java.util.List;
import java.util.Optional;

public interface EventService {
    boolean isEventExists(Event event);
    void deleteEventById(int id);
    Event save(Event event);
    Optional<Event> findById(int id);
    List<Event> listEvents();
}
