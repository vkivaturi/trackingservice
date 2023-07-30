package org.openapitools.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.digit.tracking.service.POIService;
import org.digit.tracking.service.TripService;
import org.digit.tracking.util.JsonUtil;
import org.digit.tracking.util.TrackingApiUtil;
import org.openapitools.model.POI;
import org.openapitools.model.Trip;
import org.openapitools.model.TripProgress;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.context.request.NativeWebRequest;

import javax.validation.constraints.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-29T19:38:13.286370500+05:30[Asia/Calcutta]")
@Controller
@RequestMapping("${openapi.trackingService.base-path:/api/v3}")
public class TripApiController implements TripApi {
    Logger logger = LoggerFactory.getLogger(TripApiController.class);

    @Autowired
    TripService tripService;

    private final NativeWebRequest request;

    @Autowired
    public TripApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<Trip> createTrip(
            @Parameter(name = "Trip", description = "Create a new Trip in the system", required = true) @Valid @RequestBody Trip trip
    ) {
        logger.info("## createTrip is invoked");
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Void> progressTrip(
            @Parameter(name = "TripProgress", description = "Update an existent trip in the system", required = true) @Valid @RequestBody TripProgress tripProgress
    ) {
        logger.info("## progressTrip is invoked");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateTrip(
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
        //TODO - Replace null with Trip object
        List<Trip> trips = tripService.getTripsBySearch(null);
        TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(trips));
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
