package org.digit.tracking.service;

import org.digit.tracking.data.dao.TripDao;
import org.openapitools.model.Operator;
import org.openapitools.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class TripService {
    @Autowired
    TripDao tripDao;
    public List<Trip> getTripsBySearch(Trip trip) {
        return null;
    }
    public List<Trip> getTripById(String id){
        return tripDao.fetchTripbyId(id);
    }

    public String createdTrip(Trip trip){
        return tripDao.createTrip(trip);
    }

}

