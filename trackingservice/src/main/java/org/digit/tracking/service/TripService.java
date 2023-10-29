package org.digit.tracking.service;

import org.digit.tracking.data.dao.TripDao;
import org.digit.tracking.data.dao.TripProgressDao;
import org.digit.tracking.data.model.FsmApplication;
import org.digit.tracking.data.model.FsmVehicleTrip;
import org.digit.tracking.data.sao.TripSao;
import org.digit.tracking.monitoring.RuleEngine;
import org.digit.tracking.service.helper.TripServiceHelper;
import org.digit.tracking.util.Constants;
import org.digit.tracking.util.JsonUtil;
import org.openapitools.model.Trip;
import org.openapitools.model.TripProgress;
import org.openapitools.model.TripProgressProgressDataInner;
import org.openapitools.model.TripProgressResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.digit.tracking.util.Constants.RULE_LOAD_METHOD;

@Service
public class TripService {
    @Autowired
    TripDao tripDao;

    @Autowired
    TripProgressDao tripProgressDao;

    @Autowired
    TripSao tripSao;

    @Autowired
    TripServiceHelper tripServiceHelper;

    @Autowired
    RuleEngine ruleEngine;

    //Fetch trips from FSM application
    public List<Trip> getFsmTripsForDriver(String driverId, String authToken, String tenantId) {
        //Step 1 - Fetch from FSM the list of applications based on driver id
        List<FsmApplication> fsmApplicationList = tripSao.searchFsmApplicationsForDriver(driverId, tenantId, authToken, Constants.FMS_APPLICATION_URL);

        //Step 2 - Fetch from FSM list of trips mapped to each application
        //TODO Switch to passing multiple application nos to target API
        for (FsmApplication fsmApplication : fsmApplicationList) {
            //Fetch the trip list for the application and add list back to the main applications list

            String tripResponseJson = tripSao.fetchFsmTrips(
                    fsmApplication.getApplicationNo(), null, tenantId, authToken, Constants.FMS_VEHICLE_TRIP_URL);
            fsmApplication.setFsmVehicleTripList(JsonUtil.getFSMVehicleTripObjectFromJson(tripResponseJson));
//            fsmApplication.setFsmVehicleTripList(
//                    tripSao.fetchFsmTripsForApplication(
//                            fsmApplication.getApplicationNo(), tenantId, authToken, Constants.FMS_VEHICLE_TRIP_URL));
        }

        //TODO - Can use a common Trip entity in future
        //Step 4 - Map to vehicle tracking Trip entity
        List<Trip> tripList = new ArrayList<>();
        for (FsmApplication fsmApplication : fsmApplicationList) {
            for (FsmVehicleTrip fsmVehicleTrip : fsmApplication.getFsmVehicleTripList()) {
                Trip trip = new Trip();
                String tripId = fsmVehicleTrip.getTripApplicationNo();
                trip.setId(tripId);
                trip.setReferenceNo(fsmApplication.getApplicationNo());
                trip.setServiceCode(fsmVehicleTrip.getBusinessService());

                //Add each trip to the main list
                tripList.add(trip);

                //Check if trip exists in VTS database. Insert it if it is not available locally
                if (tripDao.fetchTripbyId(tripId) == null){
                    tripServiceHelper.createTripWithFsmData(tripId, tenantId, trip.getReferenceNo(),
                            Float.valueOf(fsmApplication.getPickupLocationLatitude()), Float.valueOf(fsmApplication.getPickupLocationLongitude()),
                            trip.getServiceCode());
                }
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

    //Update function to manage both VTS and FSM trip status updates
    public String updateTrip(Trip trip, String authToken) {
        //Step 1 - Update trip status in vehicle tracking application
        String tripId =  tripDao.updateTrip(trip);

        if (trip.getStatus() == Trip.StatusEnum.COMPLETED) {
            //Step 2 - Update trip status in FSM vehicle trip application
            //Step 2.1 - Fetch trip details from FSM
            String tripResponseJson = tripSao.fetchFsmTrips(
                    null, trip.getId(), trip.getTenantId(), authToken, Constants.FMS_VEHICLE_TRIP_URL);

            //Step 2.2 - Update FSM vehicle trip map entity
            Map<String, Object> updatedVehicleTrip = JsonUtil.updateFsmTripEndActionJson(tripResponseJson);

            //Step 2.3 - Call FSM vehicle trip API and update trip status
            tripSao.updateFsmEndTripForApplication(updatedVehicleTrip, authToken, Constants.FMS_VEHICLE_TRIP_URL);
        }
        //Final - Return trip id of the updated trip to the client
        return tripId;
    }

    public String createdTripProgress(TripProgress tripProgress) {

        //Usually the API receives only one location update. But in case the client app comes back from offline more, there could be bulk updates in a single json
        //progressTime is the time when the actual geo position was recorded
        //progressReportedTime is when the device reported this data to the tracking service (especially useful in case of bulk updates)
        for (TripProgressProgressDataInner tripProgressProgressDataInner : tripProgress.getProgressData()) {
            String progressId = tripProgressDao.createTripProgress(tripProgressProgressDataInner.getLocation(), tripProgress.getProgressReportedTime(),
                    tripProgressProgressDataInner.getProgressTime(), tripProgress.getTripId(), tripProgress.getUserId());
            //TODO - Remove this call once monitoring service is live
            ruleEngine.executeAllRules(progressId);
        }
        //return tripDao.createTripProgress(tripProgress);
        return tripProgress.getTripId();
    }

    public String updateTripProgress(String tripProgressId, String userId, String matchedPoiId) {
        return tripProgressDao.updateTripProgress(tripProgressId, userId, matchedPoiId);
    }

    public List<TripProgressResponse> getTripProgressById(String progressId, String tripId) {

        List<TripProgressResponse> tripProgressResponseList = tripProgressDao.getTripProgress(progressId, tripId);

        //Trip progress id alone is passed in this case
        return tripProgressResponseList;
    }
}

