package org.digit.tracking.monitoring;

import org.digit.tracking.data.dao.PoiDao;
import org.digit.tracking.data.dao.TripDao;
import org.openapitools.model.Location;
import org.openapitools.model.POI;
import org.openapitools.model.TripProgress;
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

        List<POI> matchingPoiList = new ArrayList<>();
        //matchingPoiList = poiService.searchNearby(userLocation, POI_MATCH_THRESHOLD_METERS);
        matchingPoiList = poiDao.searchNearby(userLocation, POI_MATCH_THRESHOLD_METERS);

        if (matchingPoiList == null || matchingPoiList.isEmpty()) {
            logger.info("## No POI found near the user location");
            return null;
        }

        //Step 3 - Load rule model entity for further usage in rule execution
        //Fetch the first record in matched POI list since that is the closest one to user location
        ruleModel.setMatchedPoi(matchingPoiList.get(0).getId());
        ruleModel.setDistanceFromPoiMeters(matchingPoiList.get(0).getDistanceMeters());
        ruleModel.setProgressId(progressId);
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
    }

    //Rule - If the moving asset is spotted at a POI which is the trip's end POI, update the trip status and send notification
    public void ruleUpdateTripAndNotifyTripEnd(RuleModel ruleModel) {
        logger.info("## ruleUpdateTripAndNotifyTripEnd method");
    }

}
