package org.digit.tracking.monitoring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Rules {
    Logger logger = LoggerFactory.getLogger(Rules.class);
    RuleModel ruleModel = new RuleModel();
    //Step 1 - Fetch data from relevant sources and populate the RuleMode. This model is then used by rest of rule action methods
    public void loadModel() {
        logger.info("## loadModel method");
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
