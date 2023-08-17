package org.digit.tracking.service;

import org.digit.tracking.data.dao.TripDao;
import org.digit.tracking.monitoring.RuleEngine;
import org.openapitools.model.Trip;
import org.openapitools.model.TripProgress;
import org.openapitools.model.TripProgressProgressDataInner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.digit.tracking.util.Constants.RULE_LOAD_METHOD;

@Service
public class TripService {
    @Autowired
    TripDao tripDao;

    @Autowired
    RuleEngine ruleEngine;

    public List<Trip> getTripsBySearch(String operatorId, String tripName, String status, String userId) {
        return tripDao.fetchTripbyFilters(operatorId, tripName, status, userId);
    }

    public Trip getTripById(String id) {
        return tripDao.fetchTripbyId(id);
    }

    public String createdTrip(Trip trip) {
        return tripDao.createTrip(trip);
    }

    public String updateTrip(Trip trip) {
        return tripDao.updateTrip(trip);
    }

    public String createdTripProgress(TripProgress tripProgress) {

        //Usually the API receives only one location update. But in case the client app comes back from offline more, there could be bulk updates in a single json
        //progressTime is the time when the actual geo position was recorded
        //progressReportedTime is when the device reported this data to the tracking service (especially useful in case of bulk updates)
        for (TripProgressProgressDataInner tripProgressProgressDataInner : tripProgress.getProgressData()) {
            tripDao.createTripProgress(tripProgressProgressDataInner.getLocation(), tripProgress.getProgressReportedTime(),
                    tripProgressProgressDataInner.getProgressTime(), tripProgress.getTripId(), tripProgress.getUserId());
        }
        //return tripDao.createTripProgress(tripProgress);
        return tripProgress.getTripId();
    }

    public String updateTripProgress(String tripProgressId, String userId, String matchedPoiId) {
        return tripDao.updateTripProgress(tripProgressId, userId, matchedPoiId);
    }

    public List<TripProgress> getTripProgressById(String progressId, String tripId) {

        List<TripProgress> tripProgressList = tripDao.getTripProgress(progressId, tripId);

        //TODO - Test code... to be removed
        ruleEngine.executeAllRules(progressId);

        //Trip progress id alone is passed in this case
        return tripProgressList;
    }

}

