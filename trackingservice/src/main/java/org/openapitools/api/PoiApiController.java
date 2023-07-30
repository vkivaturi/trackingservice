package org.openapitools.api;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.digit.tracking.service.POIService;
import org.digit.tracking.util.JsonUtil;
import org.digit.tracking.util.TrackingApiUtil;
import org.openapitools.model.Alert;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-29T19:38:13.286370500+05:30[Asia/Calcutta]")
@Controller
@RequestMapping("${openapi.trackingService.base-path:/api/v3}")
public class PoiApiController implements PoiApi {
    Logger logger = LoggerFactory.getLogger(PoiApiController.class);

    @Autowired
    POIService poiService;

    private final NativeWebRequest request;

    @Autowired
    public PoiApiController(NativeWebRequest request) {
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
        //TODO - Replace null with POI object
        List<POI> pois = poiService.getPOIsBySearch(null);
        TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(pois));
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Override
    public ResponseEntity<Void> createPOI(
            @Parameter(name = "POI", description = "Create a new POI in the system", required = true) @Valid @RequestBody POI POI
    ) {
        logger.info("## createPOI is invoked");
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Void> updatePOI(
            @Parameter(name = "POI", description = "Update an existent POI in the system", required = true) @Valid @RequestBody POI POI
    ) {
        logger.info("## updatePOI is invoked");
        return new ResponseEntity<>(HttpStatus.OK);

    }

}
