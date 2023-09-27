package org.digit.tracking.service.helper;

import org.digit.tracking.data.dao.PoiDao;
import org.digit.tracking.data.dao.RouteDao;
import org.digit.tracking.data.dao.TripDao;
import org.openapitools.model.Location;
import org.openapitools.model.POI;
import org.openapitools.model.Route;
import org.openapitools.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TripServiceHelper {
    @Autowired
    PoiDao poiDao;

    @Autowired
    RouteDao routeDao;

    @Autowired
    TripDao tripDao;

    public void createTripWithFsmData(String tripId, String tenantId, String referenceNo,
                                      String userId, Float longitude, Float latitude, String authToken) {
        //Start POI id is provided as input in FSM response
        String startPoiId = createPOI(latitude, longitude, userId);
        //End POI should be fetched from another service of FSM

        //String routeIt = createRoute(userId, )

    }

    private String createPOI(Float latitude, Float longitude, String userId) {
        POI poi = new POI();
        List<Location> locationList = new ArrayList<>();
        Location location = new Location();
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        locationList.add(location);
        poi.setLocationDetails(locationList);
        poi.setType(POI.TypeEnum.POINT);
        poi.setStatus(POI.StatusEnum.ACTIVE);
        poi.setUserId(userId);

        return poiDao.createPOI(poi);
    }

    private String createRoute(String userId, String startPoi, String endPoi) {
        Route route = new Route();
        route.setStatus(Route.StatusEnum.ACTIVE);
        route.setUserId(userId);
        route.setStartPoi(startPoi);
        route.endPoi(endPoi);
        return routeDao.createRoute(route);
    }

    private void createTrip(String tripId, String referenceNo, String tenantId, String serviceCode, String userId, String routeId) {
        Trip trip = new Trip();
        trip.setId(tripId);
        trip.setReferenceNo(referenceNo);
        trip.setServiceCode(serviceCode);
        trip.setTenantId(tenantId);
        trip.setUserId(userId);
        trip.setRouteId(routeId);
        trip.setStatus(Trip.StatusEnum.NOTSTARTED);

        tripDao.createTrip(trip);
    }
}
