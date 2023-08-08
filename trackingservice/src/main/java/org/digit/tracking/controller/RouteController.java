package org.digit.tracking.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.digit.tracking.service.RouteService;
import org.digit.tracking.Constants;
import org.digit.tracking.JsonUtil;
import org.digit.tracking.TrackingApiUtil;
import org.openapitools.api.RouteApi;
import org.openapitools.model.ACK;
import org.openapitools.model.Route;
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

import javax.annotation.Generated;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-29T19:38:13.286370500+05:30[Asia/Calcutta]")
@Controller
@RequestMapping("${openapi.trackingService.base-path:/api/v3}")
public class RouteController implements RouteApi {
    Logger logger = LoggerFactory.getLogger(RouteController.class);

    private final NativeWebRequest request;

    @Autowired
    public RouteController(NativeWebRequest request) {
        this.request = request;
    }

    @Autowired
    RouteService routeService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<ACK> createRoute(
            @Parameter(name = "Route", description = "Create a new Route in the system", required = true) @Valid @RequestBody Route route
    ) {
        logger.info("## createRoute is invoked");

        String routeId = routeService.createRoute(route);
        //Initialise response object
        ACK ack = new ACK();

        if (routeId != null) {
            ack.setId(routeId);
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
    public ResponseEntity<ACK> updateRoute(
            @Parameter(name = "Route", description = "Update an existent route in the system", required = true) @Valid @RequestBody Route route
    ) {
        logger.info("## updateRoute is invoked");
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<List<Route>> findRoute(
            @Parameter(name = "status", description = "Status values that need to be considered for filter", in = ParameterIn.QUERY) @Valid @RequestParam(value = "status", required = false, defaultValue = "active") String status,
            @Parameter(name = "routeName", description = "route name that needs to be considered for filter", in = ParameterIn.QUERY) @Valid @RequestParam(value = "routeName", required = false) String routeName
    ) {
        logger.info("## findRoute is invoked");
        //TODO - Replace null with POI object
//        List<Route> routes = routeService.getRoutesBySearch(null);
//        TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(routes));
        return new ResponseEntity<>(HttpStatus.OK);

    }
    @Override
    public ResponseEntity<Route> getRouteById(
            @Parameter(name = "routeId", description = "ID of Route to return", required = true, in = ParameterIn.PATH) @PathVariable("routeId") String routeId
    ) {
        logger.info("## getRouteById is invoked");
        List<Route> routes = routeService.getRoutesById(routeId);
        TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(routes));
        return new ResponseEntity<>(HttpStatus.OK);

    }


}
