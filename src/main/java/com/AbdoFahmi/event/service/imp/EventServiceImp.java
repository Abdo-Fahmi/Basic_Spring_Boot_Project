package com.AbdoFahmi.event.service.imp;

import com.AbdoFahmi.event.model.Event;
import com.AbdoFahmi.event.model.EventEntity;
import com.AbdoFahmi.event.repository.EventRepository;
import com.AbdoFahmi.event.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EventServiceImp implements EventService {

    private final EventRepository eventRepository;

    @Autowired
    public EventServiceImp(final EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public boolean isEventExists(Event event) {
       return eventRepository.existsById(event.getId());
    }

    @Override
    public void deleteEventById(int id) {
        try {
            eventRepository.deleteById(id);
        } catch (final EmptyResultDataAccessException e) {
            log.debug("Attempted to delete non-existent event", e);
        }
    }

    @Override
    public Event save(final Event event) {
        return eventEntityToEvent(eventRepository.save(eventToEventEntity(event)));
    }

    @Override
    public Optional<Event> findById(int id) {
        final Optional<EventEntity> foundEvent = eventRepository.findById(id);
        return foundEvent.map(this::eventEntityToEvent);
    }

    @Override
    public List<Event> listEvents() {
        final List<EventEntity> foundEvents = eventRepository.findAll();
        return foundEvents.stream().map(this::eventEntityToEvent).collect(Collectors.toList());
    }

    private EventEntity eventToEventEntity(Event event) {
        return EventEntity.builder()
                .id(event.getId())
                .name(event.getName())
                .location(event.getLocation())
                .build();
    }

    private Event eventEntityToEvent(EventEntity eventEntity) {
        return Event.builder()
                .id(eventEntity.getId())
                .name(eventEntity.getName())
                .location(eventEntity.getLocation())
                .build();
    }
}