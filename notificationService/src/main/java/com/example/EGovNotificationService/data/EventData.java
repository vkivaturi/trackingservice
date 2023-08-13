package com.example.EGovNotificationService.data;


import java.util.Optional;

public class EventData {

    private Optional<String> latitude;
    private Optional<String> longitude;
    private Optional<String> update_time;
    private Optional<String> trip_id;
    private Optional<String> anomaly_poi_id;
    private Optional<String> duration_at_poi;

    public EventData() {
    }

    public Optional<String> getLatitude() {
        return latitude;
    }

    public void setLatitude(Optional<String> latitude) {
        this.latitude = latitude;
    }

    public Optional<String> getLongitude() {
        return longitude;
    }

    public void setLongitude(Optional<String> longitude) {
        this.longitude = longitude;
    }

    public Optional<String> getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(Optional<String> update_time) {
        this.update_time = update_time;
    }

    public Optional<String> getTrip_id() {
        return trip_id;
    }

    public void setTrip_id(Optional<String> trip_id) {
        this.trip_id = trip_id;
    }

    public Optional<String> getAnomaly_poi_id() {
        return anomaly_poi_id;
    }

    public void setAnomaly_poi_id(Optional<String> anomaly_poi_id) {
        this.anomaly_poi_id = anomaly_poi_id;
    }

    public Optional<String> getDuration_at_poi() {
        return duration_at_poi;
    }

    public void setDuration_at_poi(Optional<String> duration_at_poi) {
        this.duration_at_poi = duration_at_poi;
    }

    public Optional<String> getCompletion_time() {
        return completion_time;
    }

    public void setCompletion_time(Optional<String> completion_time) {
        this.completion_time = completion_time;
    }

    private Optional<String> completion_time;

}
