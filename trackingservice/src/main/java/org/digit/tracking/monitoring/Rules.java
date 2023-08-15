package org.digit.tracking.monitoring;

import org.digit.tracking.data.dao.PoiDao;
import org.digit.tracking.data.dao.RouteDao;
import org.digit.tracking.data.dao.TripDao;
import org.openapitools.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.digit.tracking.util.Constants.MONITORING_USER_ID;
import static org.digit.tracking.util.Constants.POI_MATCH_THRESHOLD_METERS;
@Service
public class Rules {
    Logger logger = LoggerFactory.getLogger(Rules.class);
    //RuleModel ruleModel = new RuleModel();

    @Autowired
    TripDao tripDao;

    @Autowired
    PoiDao poiDao;

    @Autowired
    RouteDao routeDao;

    //Stage 1 - Fetch data from relevant sources and populate the RuleMode. This model is then used by rest of rule action methods
    public RuleModel loadModel(String progressId, RuleModel ruleModel) {
        logger.info("## loadModel method");

        //Step 1 - Fetch trip progress details based on progress id. Since a progress id is input, max 1 record is fetched
        List<TripProgress> tripProgressList = tripDao.getTripProgress(progressId, null);

        if (tripProgressList == null || tripProgressList.size() != 1){
            logger.info("## No match found for progress id in db : " + progressId);
            return null;
        }
        //Step 2 - Search nearby POIs for the location belonging to the progress id
        //Since this is a single ping from user, there is only one record in tripProgressList.
        //Since this is a spatial POINT, there is only one element in getProgressData.
        Location userLocation = tripProgressList.get(0).getProgressData().get(0).getLocation();
        String tripId = tripProgressList.get(0).getTripId();

        List<POI> matchingPoiList = new ArrayList<>();
        //matchingPoiList = poiService.searchNearby(userLocation, POI_MATCH_THRESHOLD_METERS);
        matchingPoiList = poiDao.searchNearby(userLocation, POI_MATCH_THRESHOLD_METERS);

        if (matchingPoiList == null || matchingPoiList.isEmpty()) {
            logger.info("## No POI found near the user location");
            return null;
        }

        //Step 3 - Fetch corresponding trip data
        Trip trip = tripDao.fetchTripbyId(tripId);

        //Step 4 - Fetch route info since start poi and end poi are required
        Route route = routeDao.fetchRoutebyId(trip.getRouteId());

        //Step Final - Load rule model entity for further usage in rule execution
        //Fetch the first record in matched POI list since that is the closest one to user location
        ruleModel.setMatchedPoi(matchingPoiList.get(0).getId());
        ruleModel.setDistanceFromPoiMeters(matchingPoiList.get(0).getDistanceMeters());
        ruleModel.setProgressId(progressId);
        ruleModel.setTripId(tripId);
        ruleModel.setLocationAlerts(trip.getLocationAlerts());
        ruleModel.setRouteEndPoi(route.getEndPoi());

        logger.info("## Matched POI, Distance from POI : " + ruleModel.getMatchedPoi() + " " + ruleModel.getDistanceFromPoiMeters());

        return ruleModel;
    }

    //Stage 2 - Implement rule action methods which have to be invoked

    //Rule - If a trip progress update (lat long of a moving asset) is matching an existing POI in the trip, update the progress entry with that POI
    public void ruleUpdateMatchedPoi(RuleModel ruleModel) {
        logger.info("## ruleUpdatedMatchedPoi method");
        if (ruleModel.getMatchedPoi() != null){
            tripDao.updateTripProgress(ruleModel.getProgressId(), MONITORING_USER_ID, ruleModel.getMatchedPoi());
        }
    }

    //Rule - If the moving asset is spotted at a POI with alert mapped, update trip progress and send notification
    public void ruleUpdateTripProgressAndNotifyAlert(RuleModel ruleModel) {
        logger.info("## ruleUpdateTripProgressAndNotifyAlert method");
        if (ruleModel.getLocationAlerts() != null ){
            //Create a trip entity with alerts info and update the db
            Trip trip = new Trip();
            trip.setId(ruleModel.getTripId());
            trip.setLocationAlerts(ruleModel.getLocationAlerts());
            tripDao.updateTrip(trip);
        }
    }

    //Rule - If the moving asset is spotted at a POI which is the trip's end POI, update the trip status and send notification
    public void ruleUpdateTripAndNotifyTripEnd(RuleModel ruleModel) {
        logger.info("## ruleUpdateTripAndNotifyTripEnd method");
        if (ruleModel.getMatchedPoi() != null && ruleModel.getMatchedPoi().equals(ruleModel.getRouteEndPoi())){
            //POI at the progress location is matching with the route end POI. Update the trip status
            Trip trip = new Trip();
            trip.setId(ruleModel.getTripId());
            trip.setStatus(Trip.StatusEnum.COMPLETED);
            tripDao.updateTrip(trip);
        }
    }

}
