package com.AbdoFahmi.event.controller;

import com.AbdoFahmi.event.model.Event;
import com.AbdoFahmi.event.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class EventController {
    // Declare the interface not the implementation
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    // CREATE endpoint
    @PutMapping(path="/events/{id}")
    public ResponseEntity<Event> createEvent(@PathVariable final int id, @RequestBody final Event event) {
        event.setId(id);
        final Event savedEvent = eventService.save(event);

        if(eventService.isEventExists(event)) {
           return new ResponseEntity<Event>(savedEvent, HttpStatus.OK);
        } else {
            return new ResponseEntity<Event>(savedEvent, HttpStatus.CREATED);
        }
    }

    // READ endpoint
    @GetMapping(path="/events/{id}")
    public ResponseEntity<Event> retrieveEvent(@PathVariable final int id) {
        final Optional<Event> foundEvent = eventService.findById(id);
        return foundEvent.map(event -> new ResponseEntity<Event>(event, HttpStatus.OK))
                .orElse(new ResponseEntity<Event>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(path="/events")
    public ResponseEntity<List<Event>> listEvents() {
        return new ResponseEntity<List<Event>>(eventService.listEvents(), HttpStatus.OK);
    }

    // DELETE endpoint
    @DeleteMapping(path="/events/{id}")
    public ResponseEntity deleteEvent(@PathVariable final int id) {
        eventService.deleteEventById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
