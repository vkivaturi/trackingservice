package org.digit.tracking.service;

import org.openapitools.model.Alert;
import org.openapitools.model.TripService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigService {
    public List<Alert> getTripAlerts() {
        //TODO - Mock list of alerts. Replace with database call
        return fetchAlertsFromDB();
    }

    public List<TripService> getTripServices() {
        //TODO - Mock list of alerts. Replace with database call
        return fetchServicesFromDB();
    }

    private List<Alert> fetchAlertsFromDB() {
        List<Alert> alertlist = new ArrayList<Alert>();
        Alert alert = new Alert();
        alert.setCode("IL-DUMP");
        alert.setTitle("Illegal dump yard");
        alertlist.add(alert);

        alert = new Alert();
        alert.setCode("IL-AREA");
        alert.setTitle("Illegal area for the trip");
        alertlist.add(alert);
        return alertlist;
    }
    private List<TripService> fetchServicesFromDB() {
        List<TripService> servicelist = new ArrayList<TripService>();
        TripService service = new TripService();
        service.setCode("SANIT-FSCS");
        service.setName("Fecal Sludge Collection Service");
        service.setUlbId("Amritsar");
        servicelist.add(service);

        service = new TripService();
        service.setCode("HEALTH-FUMIG");
        service.setName("Mosquito Fumigation");
        service.setUlbId("Cuttack");
        servicelist.add(service);
        return servicelist;
    }


}

