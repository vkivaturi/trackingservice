package org.digit.tracking.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.digit.tracking.data.model.FsmApplication;
import org.digit.tracking.data.model.FsmVehicleTrip;
import org.openapitools.model.Location;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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
        Map<String, Object> result = getStringObjectMap(jsonString);

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

    //Identify location of FSTP
    public static Location getLocationObjectFromJson(String jsonString){
        Map<String, Object> result = getStringObjectMap(jsonString);

        //TODO - Parsing JSON this way is not clean. Replace with better code.
        //Fetch data in this json path - /fsm/address/geoLocation
        Map<String,Object> mdm = (Map<String,Object>) result.get("MdmsRes");
        Map<String,Object> fsm = (Map<String,Object>) mdm.get("FSM");
        List<Object> plantList = (List<Object>) fsm.get("FSTPPlantInfo");

        //TODO - since FSM application currently does not provide destination, pick the first record. This will work for Odisha but will not be correct in future
        Map<String, String> plantMap = (Map<String, String>) plantList.get(0);

        Location location = new Location();
        location.setLatitude(Float.valueOf(plantMap.get("latitude")));
        location.setLongitude(Float.valueOf(plantMap.get("longitude")));

        return location;
    }

    public static List<FsmVehicleTrip> getFSMVehicleTripObjectFromJson(String jsonString) {
        Map<String, Object> result = getStringObjectMap(jsonString);

        //Fetch data in this json path - /vehicleTrip
        List<Object> listOfTrips = (List<Object>) result.get("vehicleTrip");

        List<FsmVehicleTrip> fsmVehicleTripList = new ArrayList<>();

        for (Object vehicleTrip : listOfTrips) {
            FsmVehicleTrip fsmVehicleTrip = new FsmVehicleTrip();
            Map<String, Object> mapOfVehicleTrip = (Map<String, Object>) vehicleTrip;

            fsmVehicleTrip.setTripApplicationNo(String.valueOf(mapOfVehicleTrip.get("applicationNo")));
            fsmVehicleTrip.setTripApplicationStatus(String.valueOf(mapOfVehicleTrip.get("applicationStatus")));
            //Add to applications list
            fsmVehicleTripList.add(fsmVehicleTrip);
        }
        return fsmVehicleTripList;
    }
    private static Map<String, Object> getStringObjectMap(String jsonString) {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonObj;
        try {
            jsonObj = mapper.readTree(jsonString);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        Map<String, Object> result = mapper.convertValue(jsonObj, new TypeReference<Map<String, Object>>(){});
        return result;
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
