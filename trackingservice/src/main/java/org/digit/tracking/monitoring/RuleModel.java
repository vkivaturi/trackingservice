package org.digit.tracking.monitoring;

public class RuleModel {
    private String matchedPoi;
    private int distanceFromPoiMeters;
    private boolean matchedPoiInTrip;
    private boolean matchedPoiIsTripStart;
    private boolean matchedPoiIsTripEnd;

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
}
