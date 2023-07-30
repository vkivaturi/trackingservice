package org.digit.tracking.service;

import org.openapitools.model.Location;
import org.openapitools.model.POI;
import org.openapitools.model.Route;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RouteService {
    public List<Route> getRoutesBySearch(Route route) {
        //TODO - Mock list of routes. Replace with database call
        return fetchRoutesFromDB(route);
    }

    private List<Route> fetchRoutesFromDB(Route searchRoute) {
        List<Route> routelist = new ArrayList<Route>();
        //Add first Route
        Route route = new Route();
        route.setId("ROUTE_ID_1");
        route.setName("Koramangala to Silkboard");
        route.setStatus(Route.StatusEnum.ACTIVE);
        route.setStartPoi("START_POI_ID");
        route.setEndPoi("END_POI_ID");
        route.setIntermediatePois(new ArrayList<String>(Arrays.asList("POI1", "POI2")));
        routelist.add(route);

        //Add second route
        route = new Route();
        route.setId("ROUTE_ID_2");
        route.setName("HAL to Whitefield");
        route.setStatus(Route.StatusEnum.ACTIVE);
        route.setStartPoi("START_POI_ID_2");
        route.setEndPoi("END_POI_ID_2");
        route.setIntermediatePois(new ArrayList<String>(Arrays.asList("POI12", "POI22")));
        routelist.add(route);

        return routelist;
    }

}

