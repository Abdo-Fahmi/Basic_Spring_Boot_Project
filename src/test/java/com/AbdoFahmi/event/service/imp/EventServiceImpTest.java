package com.AbdoFahmi.event.service.imp;

import com.AbdoFahmi.event.model.Event;
import com.AbdoFahmi.event.model.EventEntity;
import com.AbdoFahmi.event.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.AbdoFahmi.event.TestData.testEvent;
import static com.AbdoFahmi.event.TestData.testEventEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EventServiceImpTest {
    @Mock
    private EventRepository eventRepo;

    @InjectMocks
    private EventServiceImp underTest;

    @Test
    public void eventIsSaved() {
        final Event event = testEvent();
        final EventEntity eventEntity = testEventEntity();
        when(eventRepo.save(eq(eventEntity))).thenReturn(eventEntity);

        final Event result = underTest.save(event);
        assertEquals(result, event);
    }

    @Test
    public void eventIsRetrieved() {
        final Event event = testEvent();
        final EventEntity eventEntity = testEventEntity();
        when(eventRepo.findById(eq(event.getId()))).thenReturn(Optional.of(eventEntity));

        final Optional<Event> result = underTest.findById(event.getId());
        assertEquals(Optional.of(event), result);
    }

    @Test
    public void isEventExistReturnsFalseWhenItDoesNot() {
        when(eventRepo.existsById(any())).thenReturn(false);
        final boolean result = underTest.isEventExists(testEvent());
        assertFalse(result);
    }

    @Test
    public void isEventExistReturnsTrueWhenItDoes() {
        when(eventRepo.existsById(any())).thenReturn(true);
        final boolean result = underTest.isEventExists(testEvent());
        assertTrue(result);
    }
    
    @Test
    public void findByIDReturnsEmptyWhenNoEvent() {
        int fakeID = 1982;
        when(eventRepo.findById(eq(fakeID))).thenReturn(Optional.empty());

        final Optional<Event> result = underTest.findById(fakeID);
        assertEquals(Optional.empty(), result);
    }

    @Test
    public void listEventsReturnsEmptyListWhenNonFound() {
        when(eventRepo.findAll()).thenReturn(new ArrayList<EventEntity>());
        final List<Event> foundEvents = underTest.listEvents();
        assertEquals(0,foundEvents.size());
    }

    @Test
    public void listEventsReturnsListWhenEventsFound() {
        final EventEntity eventEntity = testEventEntity();
        when(eventRepo.findAll()).thenReturn(List.of(eventEntity));
        final List<Event> foundList = underTest.listEvents();
        assertEquals(1, foundList.size());
    }

    @Test
    public void deleteEventCallsRepo() {
        final int id = 90;
        underTest.deleteEventById(id);
        verify(eventRepo).deleteById(eq(id));
    }
}
