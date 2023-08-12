package org.digit.tripmonitoring.service.utils;

import ch.qos.logback.core.joran.sanity.Pair;
import org.digit.tripmonitoring.service.data.PointOfInterest;
import org.digit.tripmonitoring.service.data.Route;
import org.digit.tripmonitoring.service.data.Trip;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TripMonitoringUtil {
    private Set<String> poiProcessedSet;
    private NetworkUtil networkUtil;

    public TripMonitoringUtil() {
        poiProcessedSet = new HashSet<>();
        networkUtil = new NetworkUtil();
    }

    public TripMonitoringUtil(Set<String> poiProcessedSet, NetworkUtil networkUtil) {
        this.poiProcessedSet = poiProcessedSet;
        this.networkUtil = networkUtil;
    }

    public boolean isAnomalyPresent(String tripId, Pair<Float, Float> currentLocation)
    {
        Trip trip = networkUtil.getTripById(tripId);
        Route route = networkUtil.getRouteById(trip.getRouteId());
        List<String> intermediatePOIs = route.getIntermediatePOIId();

        for (String poiId: intermediatePOIs) {
            String key = tripId + "_" + poiId;
            if (poiProcessedSet.contains(key)) {
                // Anomaly in Point of Interest is already notified hence return false
                // to prevent duplicate notification
                return false;
            }
            PointOfInterest poi = networkUtil.getPOIById(poiId);
            if (poi.getIllegalPoint()) {
                if (isNearby(poi, currentLocation)) {
                    poiProcessedSet.add(key);
                    return true;
                }
            }
        }
        return false;

    }

    public String updateTripStatus(Trip trip, Route route, Pair<Float, Float> currentLocation)
    {
        if (trip.getCurrentStatus().equals("COMPLETE")) {
            return "COMPLETE";
        }
        PointOfInterest poi = networkUtil.getPOIById(route.getStartPOIId());
        if (isNearby(poi, currentLocation)) {
            if (!trip.getCurrentStatus().equals("STARTED")) {
                trip.setCurrentStatus("STARTED");
            }
            return "STARTED";
        }
        poi = networkUtil.getPOIById(route.getEndPOIId());
        if (isNearby(poi, currentLocation)) {
            if (!trip.getCurrentStatus().equals("COMPLETE")) {
                clearProcessedPOISet(trip.getTripId());
                trip.setCurrentStatus("COMPLETE");
            }
            return "COMPLETE";
        }
        return "IN_PROGRESS";
    }
    private boolean isNearby(PointOfInterest poi, Pair<Float, Float> currentLocation)
    {
        // TODO: Identify if POI is nearby
        return false;
    }
    private void clearProcessedPOISet(String tripId) {
        poiProcessedSet.removeIf(item -> item.startsWith(tripId));
    }
}
