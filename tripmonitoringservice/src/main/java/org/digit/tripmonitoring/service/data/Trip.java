package org.digit.tripmonitoring.service.data;

public class Trip {
    private String tripId;
    private String operatorId;
    private String routeId;
    private String startTime;
    private String endTime;
    private String currentStatus;

    public Trip() {
    }

    public Trip(String tripId, String operatorId, String routeId, String startTime, String endTime, String currentStatus) {
        this.tripId = tripId;
        this.operatorId = operatorId;
        this.routeId = routeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.currentStatus = currentStatus;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
}
