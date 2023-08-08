package org.digit.tracking.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.digit.tracking.service.TripService;
import org.digit.tracking.Constants;
import org.digit.tracking.JsonUtil;
import org.digit.tracking.TrackingApiUtil;
import org.openapitools.api.TripApi;
import org.openapitools.model.ACK;
import org.openapitools.model.Trip;
import org.openapitools.model.TripProgress;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-29T19:38:13.286370500+05:30[Asia/Calcutta]")
@Controller
@RequestMapping("${openapi.trackingService.base-path:/api/v3}")
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
    public ResponseEntity<ACK> updateTrip(
            @Parameter(name = "Trip", description = "Update an existent trip in the system", required = true) @Valid @RequestBody Trip trip
    ) {
        logger.info("## updateTrip is invoked");
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<Trip>> findTrip(
            @Parameter(name = "status", description = "Status values that need to be considered for filter", in = ParameterIn.QUERY) @Valid @RequestParam(value = "status", required = false, defaultValue = "active") String status,
            @Parameter(name = "tripName", description = "Trip name that needs to be considered for filter", in = ParameterIn.QUERY) @Valid @RequestParam(value = "tripName", required = false) String tripName
    ) {
        logger.info("## findTrip is invoked");
        //TODO - Replace null with Trip object
        List<Trip> trips = tripService.getTripsBySearch(null);
        TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(trips));
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @Override
    public ResponseEntity<Trip> getTripById(
            @Parameter(name = "tripId", description = "ID of Trip to return", required = true, in = ParameterIn.PATH) @PathVariable("tripId") String tripId
    ) {
        logger.info("## getTripById is invoked");
        List<Trip> trips = tripService.getTripById(tripId);
        TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(trips));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
