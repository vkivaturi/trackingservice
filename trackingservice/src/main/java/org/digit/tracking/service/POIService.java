package org.digit.tracking.service;

import org.openapitools.model.Location;
import org.openapitools.model.POI;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class POIService {
    public List<POI> getPOIsBySearch(POI poi) {
        //TODO - Mock list of alerts. Replace with database call
        return fetchPOIsFromDB(poi);
    }

    private List<POI> fetchPOIsFromDB(POI searchPoi) {
        List<POI> poilist = new ArrayList<POI>();
        //Add first POI
        POI poi = new POI();
        poi.setId("POI_ID_1");
        poi.setStatus(POI.StatusEnum.ACTIVE);
        poi.setLocationName("Forum mall, Bangalore");
        poi.setType(POI.TypeEnum.POINT);

        poi.setAlert(new ArrayList<String>(Arrays.asList("IL-DUMP", "IL-AREA")));

        List<Location> locations = new ArrayList<Location>();
        Location location = new Location();
        location.setLatitude(1232.4f);
        location.setLongitude(22432.22f);
        locations.add(location);

        poi.setLocationDetails(locations);
        poilist.add(poi);

        //Add second POI
        poi = new POI();

        poi.setId("POI_ID_2");
        poi.setStatus(POI.StatusEnum.ACTIVE);
        poi.setLocationName("Koramangala, Bangalore");
        poi.setType(POI.TypeEnum.POLYGON);

        locations = new ArrayList<Location>();
        location = new Location();
        location.setLatitude(342342.4f);
        location.setLongitude(565534.22f);
        locations.add(location);

        location = new Location();
        location.setLatitude(234234.4f);
        location.setLongitude(42623.22f);
        locations.add(location);

        poi.setLocationDetails(locations);
        poilist.add(poi);

        return poilist;
    }

}

