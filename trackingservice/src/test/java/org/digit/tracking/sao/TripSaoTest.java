package org.digit.tracking.sao;

import org.digit.tracking.data.sao.TripSao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TripSaoTest {
    Logger logger = LoggerFactory.getLogger(TripSaoTest.class);

    String driverId = "ccf4998c-e7af-4e4d-a97f-59c51bbf45e5";
    String tenantId = "pb.amritsar";
    String authToken = "f75a2e54-a07a-447b-b5c6-d038a6f69a23";
    String fsmUrl = "https://unified-dev.digit.org/fsm/v1/";
    @Test
    public void testDriverSearch(){
        logger.info("##");
        TripSao tripSao = new TripSao();
        tripSao.searchApplicationsForDriver(driverId, tenantId, authToken, fsmUrl);
    }
}
