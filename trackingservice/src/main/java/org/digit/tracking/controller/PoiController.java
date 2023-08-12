package org.digit.tracking.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.digit.tracking.service.POIService;
import org.digit.tracking.util.Constants;
import org.digit.tracking.util.JsonUtil;
import org.digit.tracking.util.TrackingApiUtil;
import org.openapitools.api.ApiUtil;
import org.openapitools.api.PoiApi;
import org.openapitools.model.ACK;
import org.openapitools.model.Location;
import org.openapitools.model.POI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Generated;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-29T19:38:13.286370500+05:30[Asia/Calcutta]")
@Controller
@RequestMapping("${openapi.trackingService.base-path:/api/v3}")
public class PoiController implements PoiApi {
    Logger logger = LoggerFactory.getLogger(PoiController.class);

    @Autowired
    POIService poiService;

    private final NativeWebRequest request;

    @Autowired
    public PoiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<List<POI>> findPOI(
            @Parameter(name = "status", description = "Status values that need to be considered for filter", in = ParameterIn.QUERY) @Valid @RequestParam(value = "status", required = false, defaultValue = "active") String status,
            @Parameter(name = "locationName", description = "Location name that needs to be considered for filter", in = ParameterIn.QUERY) @Valid @RequestParam(value = "locationName", required = false) String locationName
    ) {
        logger.info("## findPOI is invoked");
        //TODO - Replace null with POI object
        List<POI> pois = poiService.getPOIsBySearch(null);
        TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(pois));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<POI> getPoiById(
            @Parameter(name = "poiId", description = "ID of POI to return", required = true, in = ParameterIn.PATH) @PathVariable("poiId") String poiId
    ) {
        logger.info("## getPOIbyId is invoked");
        List<POI> pois = poiService.getPOIsById(poiId);
        TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(pois));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public ResponseEntity<ACK> createPOI(
            @Parameter(name = "POI", description = "Create a new POI in the system", required = true) @Valid @RequestBody POI POI
    ) {
        logger.info("## createPOI is invoked");

        String poiId = poiService.createPOI(POI);
        //TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(alerts));
        //Initialise response object
        ACK ack = new ACK();

        if (poiId != null) {
            ack.setId(poiId);
            ack.setResponseCode("CODE-001");
            ack.setResponseMessage(Constants.MSG_RESPONSE_SUCCESS_SAVE);
            TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(ack));
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            ack.setResponseCode("CODE-002");
            ack.setResponseMessage(Constants.MSG_RESPONSE_ERROR_SAVE);
            TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(ack));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<List<POI>> searchNearby(
            @Parameter(name = "latitude", description = "Latitude of the user location", required = true, in = ParameterIn.PATH) @PathVariable("latitude") Float latitude,
            @Parameter(name = "longitude", description = "longitude of the user location", required = true, in = ParameterIn.PATH) @PathVariable("longitude") Float longitude,
            @Parameter(name = "distanceMeters", description = "Distance near the user to be searched", required = true, in = ParameterIn.PATH) @PathVariable("distanceMeters") Integer distanceMeters
    ) {
        logger.info("## searchNearby is invoked");
        Location location = new Location();
        location.setLongitude(longitude);
        location.setLatitude(latitude);
        List<POI> pois = poiService.searchNearby(location, distanceMeters);
        TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(pois));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Void> updatePOI(
            @Parameter(name = "POI", description = "Update an existent POI in the system", required = true) @Valid @RequestBody POI POI
    ) {
        logger.info("## updatePOI is invoked in controller");

        String poiId = poiService.updatePOI(POI);
        ACK ack = new ACK();

        if (poiId != null) {
            ack.setId(poiId);
            ack.setResponseCode("CODE-001");
            ack.setResponseMessage(Constants.MSG_RESPONSE_SUCCESS_SAVE);
            TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(ack));
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            ack.setResponseCode("CODE-002");
            ack.setResponseMessage(Constants.MSG_RESPONSE_ERROR_SAVE);
            TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(ack));
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
