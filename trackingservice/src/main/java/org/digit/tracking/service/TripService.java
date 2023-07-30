package org.digit.tracking.service;

import org.openapitools.model.Operator;
import org.openapitools.model.Trip;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {
    public List<Trip> getTripsBySearch(Trip trip) {
        //TODO - Mock list of alerts. Replace with database call
        return fetchTripsFromDB(trip);
    }

    private List<Trip> fetchTripsFromDB(Trip searchTrip) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

        List<Trip> triplist = new ArrayList<Trip>();
        //Add first Trip
        Trip trip = new Trip();
        trip.setRouteId("ROUTE_ID_1");
        trip.setServiceCode("SANIT-FSCS");

        Operator operator = new Operator();
        operator.setId("driver11");
        operator.setEmail("test@yahoo.com");
        operator.setName("Driver kumar");
        operator.setContactNumber("9999999999");
        operator.setVehicleNumber("KA 11 2312");
        trip.setOperator(operator);

        trip.setStatus(Trip.StatusEnum.IN_PROGRESS);
        trip.setPlannedStartTime("2023-07-30T19:20:17.555Z");
        trip.setPlannedEndTime("2023-07-30T19:30:17.555Z");
        trip.setActualStartTime("2023-07-30T19:40:17.555Z");
        trip.setActualEndTime("2023-07-30T19:50:17.555Z");

        triplist.add(trip);

        //Add second trip
        trip = new Trip();
        trip.setRouteId("ROUTE_ID_2");
        trip.setServiceCode("SANIT-MOSQ");

        operator = new Operator();
        operator.setId("driver3345");
        operator.setEmail("nothing@yahoo.com");
        operator.setName("Driver rao");
        operator.setContactNumber("1111111111");
        operator.setVehicleNumber("TS 33 4312");
        trip.setOperator(operator);

        trip.setStatus(Trip.StatusEnum.IN_PROGRESS);
        trip.setPlannedStartTime("2023-07-30T19:20:17.555Z");
        trip.setPlannedEndTime("2023-07-30T20:20:17.555Z");
        trip.setActualStartTime("2023-07-30T20:20:17.555Z");
        trip.setActualEndTime("2023-07-30T21:20:17.555Z");

        triplist.add(trip);

        return triplist;
    }

}

