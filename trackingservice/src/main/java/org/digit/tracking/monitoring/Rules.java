package org.digit.tracking.monitoring;

import org.digit.tracking.service.POIService;
import org.digit.tracking.service.TripService;
import org.openapitools.model.POI;
import org.openapitools.model.TripProgress;
import org.openapitools.model.TripProgressProgressDataInner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.digit.tracking.util.Constants.POI_MATCH_THRESHOLD_METERS;

public class Rules {
    Logger logger = LoggerFactory.getLogger(Rules.class);
    RuleModel ruleModel = new RuleModel();

    @Autowired
    TripService tripService;

    @Autowired
    POIService poiService;

    //Step 1 - Fetch data from relevant sources and populate the RuleMode. This model is then used by rest of rule action methods
    public void loadModel(String progressId) {
        logger.info("## loadModel method");

        //Step 1 - Fetch trip progress details based on progress id
        //tripService.ge
        //Step 2 - Search nearby POIs for the location belonging to the progress id

        //Step 3 - Load rule model entity for further usage in rule execution


        List<POI> matchingPoiList;

//        for (TripProgressProgressDataInner tripProgressProgressDataInner : tripProgress.getProgressData()) {
        matchingPoiList = new ArrayList<>();

            //Each location mentioned in trip progress can map to multiple POIs in tracking db
       // matchingPoiList = poiService.searchNearby(tripProgressProgressDataInner.getLocation(), POI_MATCH_THRESHOLD_METERS);

            //Pick the first element in the list as it is closest to the trip progress location
//            RuleModel ruleModel = new RuleModel();
//            ruleModel.setMatchedPoi(matchingPoiList.get(0).getId());
//            ruleModelList.add(ruleModel);
  //      }
    }

    //Step 2 - Implement rule action methods which have to be invoked

    //Rule - If a trip progress update (lat long of a moving asset) is matching an existing POI in the trip, update the progress entry with that POI
    public void ruleUpdateMatchedPoi() {
        logger.info("## ruleUpdatedMatchedPoi method");
    }

    //Rule - If the moving asset is spotted at a POI with alert mapped, update trip progress and send notification
    public void ruleUpdateTripProgressAndNotifyAlert() {
        logger.info("## ruleUpdateTripProgressAndNotifyAlert method");
    }

    //Rule - If the moving asset is spotted at a POI which is the trip's end POI, update the trip status and send notification
    public void ruleUpdateTripAndNotifyTripEnd() {
        logger.info("## ruleUpdateTripAndNotifyTripEnd method");
    }

}
