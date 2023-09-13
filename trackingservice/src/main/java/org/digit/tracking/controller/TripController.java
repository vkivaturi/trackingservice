package org.digit.tracking.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.digit.tracking.service.TripService;
import org.digit.tracking.util.Constants;
import org.digit.tracking.util.JsonUtil;
import org.digit.tracking.util.TrackingApiUtil;
import org.openapitools.api.ApiUtil;
import org.openapitools.api.TripApi;
import org.openapitools.model.ACK;
import org.openapitools.model.LocationAlert;
import org.openapitools.model.Trip;
import org.openapitools.model.TripProgress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Generated;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-29T19:38:13.286370500+05:30[Asia/Calcutta]")
@Controller
@RequestMapping("${openapi.trackingService.base-path:/api/v3}")
@CrossOrigin(maxAge = 3600)
public class TripController implements TripApi {
    Logger logger = LoggerFactory.getLogger(TripController.class);

    @Autowired
    TripService tripService;

    private final NativeWebRequest request;

    @Autowired
    public TripController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<ACK> createTrip(
            @Parameter(name = "Trip", description = "Create a new Trip in the system", required = true) @Valid @RequestBody Trip trip
    ) {
        logger.info("## createTrip is invoked");
        String tripId = tripService.createdTrip(trip);

        //Initialise response object
        ACK ack = new ACK();

        if (tripId != null) {
            ack.setId(tripId);
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

    @Override
    public ResponseEntity<ACK> progressTrip(
            @Parameter(name = "TripProgress", description = "Update an existent trip in the system", required = true) @Valid @RequestBody TripProgress tripProgress
    ) {
        logger.info("## progressTrip is invoked");
        String tripProgressId = tripService.createdTripProgress(tripProgress);

        //Initialise response object
        ACK ack = new ACK();

        if (tripProgressId != null) {
            ack.setId(tripProgressId);
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

    @Override
    public ResponseEntity<TripProgress> getTripProgressById(
            @Parameter(name = "progressId", description = "ID of trip progress to search", in = ParameterIn.QUERY) @Valid @RequestParam(value = "progressId", required = false) String progressId,
            @Parameter(name = "tripId", description = "Trip id of trip progress to search", in = ParameterIn.QUERY) @Valid @RequestParam(value = "tripId", required = false) String tripId,
            @Parameter(name = "pageSize", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @Parameter(name = "pageNumber", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "pageNumber", required = false) Integer pageNumber
    ) {
        logger.info("## getTripProgressById is invoked");

        //rules.loadModel("691ad062-a70c-4018-ad59-d92465b4aeaf");

//        RuleEngine re = new RuleEngine();
//        ruleEngine.executeSingleRuleMethod(RULE_LOAD_METHOD, "691ad062-a70c-4018-ad59-d92465b4aeaf");
        //
        List<TripProgress> tripProgressList = tripService.getTripProgressById(progressId, tripId);
        TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(tripProgressList));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    public ResponseEntity<ACK> updateTripProgress(
            @Parameter(name = "TripProgress", description = "Update an existent trip progress in the system", required = true) @Valid @RequestBody TripProgress tripProgress
    ) {
        logger.info("## updateTripProgress is invoked");

        String tripProgressId = tripService.updateTripProgress(tripProgress.getId(), tripProgress.getUserId(), tripProgress.getMatchedPoiId());
        ACK ack = new ACK();

        if (tripProgressId != null) {
            ack.setId(tripProgressId);
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

        @Override
    public ResponseEntity<ACK> updateTrip(
            @Parameter(name = "Trip", description = "Update an existent trip in the system", required = true) @Valid @RequestBody Trip trip
    ) {
        logger.info("## updateTrip is invoked");

        String tripId = tripService.updateTrip(trip);
        ACK ack = new ACK();

        if (tripId != null) {
            ack.setId(tripId);
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

    @Override
    public ResponseEntity<List<Trip>> findTrip(
            @Parameter(name = "status", description = "Status values that need to be considered for filter", in = ParameterIn.QUERY) @Valid @RequestParam(value = "status", required = false, defaultValue = "active") String status,
            @Parameter(name = "name", description = "Trip name that needs to be considered for filter", in = ParameterIn.QUERY) @Valid @RequestParam(value = "name", required = false) String name,
            @Parameter(name = "userId", description = "user id who created the trip", in = ParameterIn.QUERY) @Valid @RequestParam(value = "userId", required = false) String userId,
            @Parameter(name = "operatorId", description = "Operator id to whom the trip is assigned", in = ParameterIn.QUERY) @Valid @RequestParam(value = "operatorId", required = false) String operatorId,
            @Parameter(name = "pageSize", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @Parameter(name = "pageNumber", description = "", in = ParameterIn.QUERY) @Valid @RequestParam(value = "pageNumber", required = false) Integer pageNumber
    ) {
        logger.info("## findTrip is invoked");
        List<Trip> trips = tripService.getTripsBySearch(operatorId, name, status, userId);
        TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(trips));
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Trip> getTripById(
            @Parameter(name = "tripId", description = "ID of Trip to return", required = true, in = ParameterIn.PATH) @PathVariable("tripId") String tripId
    ) {
        logger.info("## getTripById is invoked");
        Trip trip = tripService.getTripById(tripId);
        TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(trip));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<LocationAlert>> getTripAlerts(
            @NotNull @Parameter(name = "tripId", description = "Trip id of trip progress to search", required = true, in = ParameterIn.QUERY) @Valid @RequestParam(value = "tripId", required = true) String tripId
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"code\" : \"Alert-001\", \"title\" : \"Illegal dumpyard\" }, { \"code\" : \"Alert-005\", \"title\" : \"Trip completed without reaching destination\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
