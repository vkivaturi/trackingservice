package org.digit.tripmonitoring.service.data;

import java.util.List;

public class Route {
    private String routeId;
    private String routeName;
    private Boolean routeStatus;
    private String startPOIId;
    private List<String> intermediatePOIId;
    private String endPOIId;
    private Integer routeTraversalTime;

    public Route() {
    }

    public Route(String routeId, String routeName, Boolean routeStatus, String startPOIId, List<String> intermediatePOIId, String endPOIId, Integer routeTraversalTime) {
        this.routeId = routeId;
        this.routeName = routeName;
        this.routeStatus = routeStatus;
        this.startPOIId = startPOIId;
        this.intermediatePOIId = intermediatePOIId;
        this.endPOIId = endPOIId;
        this.routeTraversalTime = routeTraversalTime;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;
    }

    public Boolean getRouteStatus() {
        return routeStatus;
    }

    public void setRouteStatus(Boolean routeStatus) {
        this.routeStatus = routeStatus;
    }

    public String getStartPOIId() {
        return startPOIId;
    }

    public void setStartPOIId(String startPOIId) {
        this.startPOIId = startPOIId;
    }

    public List<String> getIntermediatePOIId() {
        return intermediatePOIId;
    }

    public void setIntermediatePOIId(List<String> intermediatePOIId) {
        this.intermediatePOIId = intermediatePOIId;
    }

    public String getEndPOIId() {
        return endPOIId;
    }

    public void setEndPOIId(String endPOIId) {
        this.endPOIId = endPOIId;
    }

    public Integer getRouteTraversalTime() {
        return routeTraversalTime;
    }

    public void setRouteTraversalTime(Integer routeTraversalTime) {
        this.routeTraversalTime = routeTraversalTime;
    }
}
