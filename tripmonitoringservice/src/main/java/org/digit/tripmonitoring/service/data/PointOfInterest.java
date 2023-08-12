package org.digit.tripmonitoring.service.data;

import ch.qos.logback.core.joran.sanity.Pair;

public class PointOfInterest {
    String poiId;
    String poiStatus;
    String locationName;
    Pair<Float, Float> LocationDetails;
    Integer geoFenceRadius;
    Boolean illegalPoint;

    public PointOfInterest() {
    }

    public PointOfInterest(String poiId, String poiStatus, String locationName, Pair<Float, Float> locationDetails, Integer geoFenceRadius, Boolean illegalPoint) {
        this.poiId = poiId;
        this.poiStatus = poiStatus;
        this.locationName = locationName;
        LocationDetails = locationDetails;
        this.geoFenceRadius = geoFenceRadius;
        this.illegalPoint = illegalPoint;
    }

    public String getPoiId() {
        return poiId;
    }

    public void setPoiId(String poiId) {
        this.poiId = poiId;
    }

    public String getPoiStatus() {
        return poiStatus;
    }

    public void setPoiStatus(String poiStatus) {
        this.poiStatus = poiStatus;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Pair<Float, Float> getLocationDetails() {
        return LocationDetails;
    }

    public void setLocationDetails(Pair<Float, Float> locationDetails) {
        LocationDetails = locationDetails;
    }

    public Integer getGeoFenceRadius() {
        return geoFenceRadius;
    }

    public void setGeoFenceRadius(Integer geoFenceRadius) {
        this.geoFenceRadius = geoFenceRadius;
    }

    public Boolean getIllegalPoint() {
        return illegalPoint;
    }

    public void setIllegalPoint(Boolean illegalPoint) {
        this.illegalPoint = illegalPoint;
    }
}
