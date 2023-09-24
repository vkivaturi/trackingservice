package org.digit.tracking.data.model;

//Entity to manage FSM Vehicle Trip information
public class FsmVehicleTrip {
    //In FSM context, this is the application number of vehicle trip
    private String tripApplicationNo;
    private String tripApplicationStatus;
    private String tripStartTime;
    private String tripEndTime;

    public String getTripApplicationNo() {
        return tripApplicationNo;
    }

    public void setTripApplicationNo(String tripApplicationNo) {
        this.tripApplicationNo = tripApplicationNo;
    }

    public String getTripApplicationStatus() {
        return tripApplicationStatus;
    }

    public void setTripApplicationStatus(String tripApplicationStatus) {
        this.tripApplicationStatus = tripApplicationStatus;
    }

    public String getTripStartTime() {
        return tripStartTime;
    }

    public void setTripStartTime(String tripStartTime) {
        this.tripStartTime = tripStartTime;
    }

    public String getTripEndTime() {
        return tripEndTime;
    }

    public void setTripEndTime(String tripEndTime) {
        this.tripEndTime = tripEndTime;
    }
}
