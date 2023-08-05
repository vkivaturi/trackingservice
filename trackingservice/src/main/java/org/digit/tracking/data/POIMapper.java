package org.digit.tracking.data;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.digit.tracking.util.DbUtil;
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
        poi.setId(rs.getString("id"));
        poi.setType(POI.TypeEnum.valueOf(rs.getString("type").toUpperCase()));
        poi.setLocationName(rs.getString("locationName"));
        poi.setLocationDetails(DbUtil.dbJsonToList(rs, "locationDetails", Location.class));
        poi.setAlert(DbUtil.dbJsonToList(rs, "alert", String.class));
        poi.setStatus(POI.StatusEnum.valueOf(rs.getString("status").toUpperCase()));
        poi.setAudit(DbUtil.getAuditDetails(rs));
        return poi;
    }
}
