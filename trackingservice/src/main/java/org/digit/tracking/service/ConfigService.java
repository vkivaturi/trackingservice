package org.digit.tracking.service;

import org.openapitools.model.LocationAlert;
import org.openapitools.model.ServiceType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigService {
    public List<LocationAlert> getLocationAlerts() {
        //TODO - Mock list of alerts. Replace with database call
        return fetchAlertsFromDB();
    }

    public List<ServiceType> getServiceTypes() {
        //TODO - Mock list of alerts. Replace with database call
        return fetchServicesFromDB();
    }

    private List<LocationAlert> fetchAlertsFromDB() {
        List<LocationAlert> alertlist = new ArrayList<LocationAlert>();
        LocationAlert alert = new LocationAlert();
        alert.setCode("IL-DUMP");
        alert.setTitle("Illegal dump yard");
        alertlist.add(alert);

        alert = new LocationAlert();
        alert.setCode("IL-AREA");
        alert.setTitle("Illegal area for the trip");
        alertlist.add(alert);
        return alertlist;
    }
    private List<ServiceType> fetchServicesFromDB() {
        List<ServiceType> servicelist = new ArrayList<ServiceType>();
        ServiceType service = new ServiceType();
        service.setCode("SANIT-FSCS");
        service.setName("Fecal Sludge Collection Service");
        service.setUlbId("Amritsar");
        servicelist.add(service);

        service = new ServiceType();
        service.setCode("HEALTH-FUMIG");
        service.setName("Mosquito Fumigation");
        service.setUlbId("Cuttack");
        servicelist.add(service);
        return servicelist;
    }
}