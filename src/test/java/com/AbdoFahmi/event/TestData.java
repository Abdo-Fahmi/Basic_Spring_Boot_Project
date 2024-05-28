package com.AbdoFahmi.event;

import com.AbdoFahmi.event.model.Event;
import com.AbdoFahmi.event.model.EventEntity;

public final class TestData {
    private TestData() { }

    public static Event testEvent() {
        return Event.builder()
                .id(1)
                .name("birthday")
                .location("somewhere")
                .build();
    }

    public static EventEntity testEventEntity() {
       return EventEntity.builder()
               .id(1)
               .name("birthday")
               .location("somewhere")
               .build();
    }
}
