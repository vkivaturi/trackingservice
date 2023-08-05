package org.digit.tripmonitoring.service.producer;

public class Event {

    private String eventType;
    private String eventData;

    public Event() {
    }

    public Event(String eventType, String eventData) {
        this.eventType = eventType;
        this.eventData = eventData;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventData() {
        return eventData;
    }

    public void setEventData(String eventData) {
        this.eventData = eventData;
    }
}
