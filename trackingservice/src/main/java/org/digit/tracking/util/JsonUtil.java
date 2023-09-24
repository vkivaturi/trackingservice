package org.digit.tracking.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.digit.tracking.data.model.FsmApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    static Logger logger = LoggerFactory.getLogger(JsonUtil.class);

    public static String getJsonFromObject(Object obj){
        ObjectMapper converter = new ObjectMapper();
        converter.registerModule(new JavaTimeModule());
        try {
            return converter.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //FSM entity data is required for vehicle tracking of FSM use cases. Application number and pickup coordinates are the most important ones
    public static List<FsmApplication> getFSMObjectFromJson(String jsonString){
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObj;
        try {
            jsonObj = mapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> result = mapper.convertValue(jsonObj, new TypeReference<Map<String, Object>>(){});

        //Fetch data in this json path - /fsm/address/geoLocation
        List<Object> listOfApplications = (List<Object>) result.get("fsm");
        List<FsmApplication> fsmApplicationList = new ArrayList<>();

        for (Object listOfApplication : listOfApplications) {
            FsmApplication fsmApplication = getFsmApplication((Map<String, Object>) listOfApplication);
            //Add to applications list
            fsmApplicationList.add(fsmApplication);
        }
        return fsmApplicationList;
    }

    private static FsmApplication getFsmApplication(Map<String, Object> listOfApplication) {
        FsmApplication fsmApplication = new FsmApplication();
        Map<String, Object> mapOfApplication = listOfApplication;

        //Fetch application level attributes
        fsmApplication.setApplicationNo(String.valueOf(mapOfApplication.get("applicationNo")));
        fsmApplication.setApplicationStatus(String.valueOf(mapOfApplication.get("applicationStatus")));
        fsmApplication.setTenantId(String.valueOf(mapOfApplication.get("tenantId")));
        fsmApplication.setDriverId(String.valueOf(mapOfApplication.get("driverId")));

        //Fetch address and geolocation attributes
        Map<String, Object> mapOfAddress = (Map<String, Object>) mapOfApplication.get("address");
        Map<String, Object> mapOfLocation = (Map<String, Object>) mapOfAddress.get("geoLocation");
        fsmApplication.setPickupLocationLatitude(String.valueOf(mapOfLocation.get("latitude")));
        fsmApplication.setPickupLocationLongitude(String.valueOf(mapOfLocation.get("longitude")));
        return fsmApplication;
    }
}
