package org.digit.tracking.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openapitools.model.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class POIMapper implements RowMapper<POI> {
    public POI mapRow(ResultSet rs, int rowNum) throws SQLException {
        POI poi = new POI();
        poi.setId(rs.getString("code"));
        poi.setType(POI.TypeEnum.valueOf(rs.getString("type")));
        poi.setLocationName(rs.getString("locationName"));
        poi.setLocationDetails(getLocationDetails(rs));
        poi.setAlert(getAlertList(rs));
        poi.setStatus(POI.StatusEnum.valueOf(rs.getString("status")));
        poi.setAudit(getAuditDetails(rs));
        return poi;
    }

    private Audit getAuditDetails(ResultSet rs) throws SQLException {
        Audit audit = new Audit();
        audit.setCreatedBy(rs.getString("createdBy"));
        audit.setCreatedDate(rs.getString("createdDate"));
        audit.setUpdatedBy(rs.getString("updatedBy"));
        audit.setUpdatedDate(rs.getString("updatedDate"));
        return audit;
    }

    private List<String> getAlertList(ResultSet rs) throws SQLException {
        //Fetch alert list into a string json
        String alertsJson = rs.getString("alertList");
        //Convert json string to array of string
        ObjectMapper mapper = new ObjectMapper();
        List<String> locationAlertsList = new ArrayList<>();
        try {
            locationAlertsList = Arrays.asList(mapper.readValue(alertsJson, String[].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return locationAlertsList;
    }

    private List<Location> getLocationDetails(ResultSet rs) throws SQLException {
        //Fetch alert list into a string json
        String locationsJson = rs.getString("locationList");
        //Convert json string to array of locations
        ObjectMapper mapper = new ObjectMapper();
        List<Location> locationList = new ArrayList<Location>();
        try {
            locationList = Arrays.asList(mapper.readValue(locationsJson, Location[].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return locationList;
    }
}
