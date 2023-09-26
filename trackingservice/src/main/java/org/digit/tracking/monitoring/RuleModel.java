package org.digit.tracking.monitoring;

import java.util.List;
//RuleModel holds the data related to an event. Rules are then executed using this data
public class RuleModel {
    private String matchedPoi;
    private int distanceFromPoiMeters;
    private boolean matchedPoiInTrip;
    private boolean matchedPoiIsTripStart;
    private boolean matchedPoiIsTripEnd;
    private String progressId;
    private String tripId;
    private String alerts;
    private String routeEndPoi;
    public String getMatchedPoi() {
        return matchedPoi;
    }

    public void setMatchedPoi(String matchedPoi) {
        this.matchedPoi = matchedPoi;
    }

    public int getDistanceFromPoiMeters() {
        return distanceFromPoiMeters;
    }

    public void setDistanceFromPoiMeters(int distanceFromPoiMeters) {
        this.distanceFromPoiMeters = distanceFromPoiMeters;
    }

    public boolean isMatchedPoiInTrip() {
        return matchedPoiInTrip;
    }

    public void setMatchedPoiInTrip(boolean matchedPoiInTrip) {
        this.matchedPoiInTrip = matchedPoiInTrip;
    }

    public boolean isMatchedPoiIsTripStart() {
        return matchedPoiIsTripStart;
    }

    public void setMatchedPoiIsTripStart(boolean matchedPoiIsTripStart) {
        this.matchedPoiIsTripStart = matchedPoiIsTripStart;
    }

    public boolean isMatchedPoiIsTripEnd() {
        return matchedPoiIsTripEnd;
    }

    public void setMatchedPoiIsTripEnd(boolean matchedPoiIsTripEnd) {
        this.matchedPoiIsTripEnd = matchedPoiIsTripEnd;
    }

    public String getProgressId() {
        return progressId;
    }
    public void setProgressId(String progressId) {
        this.progressId = progressId;
    }

    public String getTripId() {
        return tripId;
    }
    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
    public String getAlerts() {
        return alerts;
    }
    public void setAlerts(String alerts) {
        this.alerts = alerts;
    }
    public String getRouteEndPoi() {
        return routeEndPoi;
    }
    public void setRouteEndPoi(String routeEndPoi) {
        this.routeEndPoi = routeEndPoi;
    }

}
