package org.digit.tracking.service;

import org.digit.tracking.data.dao.TripDao;
import org.digit.tracking.data.model.FsmApplication;
import org.digit.tracking.data.model.FsmVehicleTrip;
import org.digit.tracking.data.sao.TripSao;
import org.digit.tracking.monitoring.RuleEngine;
import org.digit.tracking.util.Constants;
import org.openapitools.model.Trip;
import org.openapitools.model.TripProgress;
import org.openapitools.model.TripProgressProgressDataInner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.digit.tracking.util.Constants.RULE_LOAD_METHOD;

@Service
public class TripService {
    @Autowired
    TripDao tripDao;

    @Autowired
    TripSao tripSao;

    @Autowired
    RuleEngine ruleEngine;

//    public List<Trip> getTripsByFsmSearch(String operatorId, String tripName, String status, String userId) {
//        return tripDao.fetchTripbyFilters(operatorId, tripName, status, userId);
//    }

    //Fetch trips from FSM application
    public List<Trip> getFsmTripsForDriver(String driverId, String authToken, String tenantId) {
        //Step 1 - Fetch list of applications based on driver id
        List<FsmApplication> fsmApplicationList = tripSao.searchFsmApplicationsForDriver(driverId, tenantId, authToken, Constants.FMS_APPLICATION_URL);

        //Step 2 - Fetch list of trip mapped to each application
        //TODO Switch to passing multiple application nos to target API
        for (FsmApplication fsmApplication : fsmApplicationList) {
            //Fetch the trip list for the application and add list back to the main applications list
            fsmApplication.setFsmVehicleTripList(
                    tripSao.fetchFsmTripsForApplication(
                            fsmApplication.getApplicationNo(), tenantId, authToken, Constants.FMS_VEHICLE_TRIP_URL));
        }

        //TODO - Can use a common Trip entity in future
        //Step 3 - Map to vehicle tracking Trip entity
        List<Trip> tripList = new ArrayList<>();
        for (FsmApplication fsmApplication : fsmApplicationList) {
            for (FsmVehicleTrip fsmVehicleTrip : fsmApplication.getFsmVehicleTripList()) {
                Trip trip = new Trip();
                trip.setId(fsmVehicleTrip.getTripApplicationNo());
                //Add each trip to the main list
                tripList.add(trip);
            }
        }
        return tripList;
    }

    public List<Trip> getTripsByLocalSearch(String status, String userId, String operatorId, String tenantId, String businessService, String refernceNos) {
        return tripDao.fetchTripbyFilters(status, userId, operatorId, tenantId, businessService, refernceNos);
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
            String progressId = tripDao.createTripProgress(tripProgressProgressDataInner.getLocation(), tripProgress.getProgressReportedTime(),
                    tripProgressProgressDataInner.getProgressTime(), tripProgress.getTripId(), tripProgress.getUserId());
            //TODO - Remove this call once monitoring service is live
            ruleEngine.executeAllRules(progressId);
        }
        //return tripDao.createTripProgress(tripProgress);
        return tripProgress.getTripId();
    }

    public String updateTripProgress(String tripProgressId, String userId, String matchedPoiId) {
        return tripDao.updateTripProgress(tripProgressId, userId, matchedPoiId);
    }

    public List<TripProgress> getTripProgressById(String progressId, String tripId) {

        List<TripProgress> tripProgressList = tripDao.getTripProgress(progressId, tripId);

        //Trip progress id alone is passed in this case
        return tripProgressList;
    }

}

