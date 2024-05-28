package com.AbdoFahmi.event.controller;

import com.AbdoFahmi.event.model.Event;
import com.AbdoFahmi.event.service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.AbdoFahmi.event.TestData.testEvent;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class EventControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EventService eventService;

    @Test
    public void eventIsCreated() throws Exception{
        final Event testEvent = testEvent();

        final ObjectMapper oMapper = new ObjectMapper();
        final String eventJSON = oMapper.writeValueAsString(testEvent);

        mockMvc.perform(MockMvcRequestBuilders.put("/events/" + testEvent.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .content(eventJSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testEvent.getId()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testEvent.getName()))
            .andExpect(MockMvcResultMatchers.jsonPath("$.location").value(testEvent.getLocation()));
    }

    @Test
    public void retrieveEventReturns404WhenEventNotFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/events/2123"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
    
    @Test
    public void retrieveEventReturns200WhenFound() throws Exception {
        final Event event = testEvent();
        eventService.save(event);

        mockMvc.perform(MockMvcRequestBuilders.get("/events/" + event.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void retrieveEventReturnsEventWhenFound() throws Exception {
        final Event testEvent = testEvent();
        eventService.save(testEvent);

        mockMvc.perform(MockMvcRequestBuilders.get("/events/" + testEvent.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(testEvent.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(testEvent.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value(testEvent.getLocation()));
    }

    @Test
    public void retrieveListReturns200AndEmptyListWhenNonFound() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/events"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    public void retrieveListReturns200AndEventsWhenFound() throws Exception {
        final Event testEvent = testEvent();
        eventService.save(testEvent);

        mockMvc.perform(MockMvcRequestBuilders.get("/events"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].id").value(testEvent.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].name").value(testEvent.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].location").value(testEvent.getLocation()));
    }
}




