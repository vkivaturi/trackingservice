package org.openapitools.api;

import org.digit.tracking.service.ConfigService;
import org.digit.tracking.util.JsonUtil;
import org.digit.tracking.util.TrackingApiUtil;
import org.openapitools.model.Alert;
import org.openapitools.model.TripAlert;
import org.openapitools.model.TripService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.NativeWebRequest;

import javax.annotation.Generated;
import java.util.List;
import java.util.Optional;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2023-07-29T17:17:16.820768500+05:30[Asia/Calcutta]")
@Controller
@RequestMapping("${openapi.trackingService.base-path:/api/v3}")
public class ConfigApiController implements ConfigApi {

    Logger logger = LoggerFactory.getLogger(ConfigApiController.class);

    private final NativeWebRequest request;

    @Autowired
    public ConfigApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Autowired
    ConfigService configService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    @Override
    public ResponseEntity<List<TripAlert>> findAlerts(){
        logger.info("## findAlerts inside ConfigApiController");

        List<Alert> alerts = configService.getTripAlerts();
        TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(alerts));
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }
    @Override
    public ResponseEntity<List<TripService>> findServices(){
        logger.info("## findTripService is invoked");

        List<TripService> services = configService.getTripServices();
        TrackingApiUtil.setResponse(request, JsonUtil.getJsonFromObject(services));
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

}
