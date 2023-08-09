package org.digit.tracking.data.rowmapper;

import org.digit.tracking.util.DbUtil;
import org.openapitools.model.*;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class POIMapper implements RowMapper<POI> {
    public POI mapRow(ResultSet rs, int rowNum) throws SQLException {
        POI poi = new POI();
        poi.setId(rs.getString("id"));
        poi.setType(POI.TypeEnum.valueOf(rs.getString("type").toUpperCase()));
        poi.setLocationName(rs.getString("locationName"));
        poi.setLocationDetails(DbUtil.dbJsonToList(rs, "locationDetails", Location.class));
        poi.setAlert(DbUtil.dbJsonToList(rs, "alert", String.class));
        poi.setStatus(POI.StatusEnum.valueOf(rs.getString("status").toUpperCase()));
        poi.setUserId(rs.getString("userId"));
        //poi.setAudit(DbUtil.getAuditDetails(rs));
        return poi;
    }
}
