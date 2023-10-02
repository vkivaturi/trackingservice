package org.digit.tracking.data.sao;

import org.digit.tracking.data.model.FsmApplication;
import org.digit.tracking.data.model.FsmVehicleTrip;
import org.digit.tracking.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TripSao is the service access object to manage trip data operations (search, update). This SAO interacts with DIGIT FSM APIs
@Service
public class TripSao {
    Logger logger = LoggerFactory.getLogger(TripSao.class);
    RestTemplate restTemplate = new RestTemplate();
    public List<FsmApplication> searchFsmApplicationsForDriver(String driverId, String tenantId, String authToken, String fsmUrl) {
        HttpEntity<Map<String, Object>> entity = getMapHttpEntity(authToken);
        StringBuilder searchUrl = new StringBuilder().append(fsmUrl).append("/").append("_search?tenantId=").append(tenantId).append("&driverId=").append(driverId);
        logger.info("## " + searchUrl);

        ResponseEntity<String> response = restTemplate.postForEntity(searchUrl.toString(), entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info(response.getBody());
            return(JsonUtil.getFSMObjectFromJson(response.getBody()));
        } else {
            logger.error("## Request Failed");
            logger.error(response.getStatusCode().toString());
            logger.error(response.getBody());
            return null;
        }
    }

    public List<FsmVehicleTrip> fetchFsmTripsForApplication(String applicationNo, String tenantId, String authToken, String vehicleTripUrl) {
        HttpEntity<Map<String, Object>> entity = getMapHttpEntity(authToken);
        StringBuilder searchUrl = new StringBuilder().append(vehicleTripUrl).append("/").append("_search?tenantId=").append(tenantId).append("&refernceNos=").append(applicationNo);
        logger.info("## " + vehicleTripUrl);

        ResponseEntity<String> response = restTemplate.postForEntity(searchUrl.toString(), entity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            logger.info(response.getBody());
            return(JsonUtil.getFSMVehicleTripObjectFromJson(response.getBody()));

        } else {
            logger.error("## Request Failed");
            logger.error(response.getStatusCode().toString());
            logger.error(response.getBody());
            return null;
        }
    }

    private static HttpEntity<Map<String, Object>> getMapHttpEntity(String authToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> mapInner = new HashMap<>();
        mapInner.put("apiId", "Rainmaker");
        if (authToken != null)
            mapInner.put("authToken", authToken);
        mapInner.put("msgId", "1694796531963|en_IN");

        Map<String, Object> mapOuter = new HashMap<>();
        mapOuter.put("RequestInfo", mapInner);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(mapOuter, headers);
        return entity;
    }

}
