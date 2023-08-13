package com.example.EGovNotificationService.data;

public class Event {

    private EventType eventType;
    private EventData eventData;

    public Event() {
    }

    public Event(EventType eventType, EventData eventData) {
        this.eventType = eventType;
        this.eventData = eventData;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public EventData getEventData() {
        return eventData;
    }

    public void setEventData(EventData eventData) {
        this.eventData = eventData;
    }
}
