package org.digit.tracking.data.rowmapper;

import org.digit.tracking.data.model.TripAlert;
import org.digit.tracking.util.DbUtil;
import org.openapitools.model.Operator;
import org.openapitools.model.Trip;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TripAlertMapper implements RowMapper<TripAlert> {
    DbUtil dbUtil = new DbUtil();
    public TripAlert mapRow(ResultSet rs, int rowNum) throws SQLException {
        TripAlert tripAlert = new TripAlert();
        tripAlert.setId(rs.getString("id"));
        tripAlert.setTripId(rs.getString("tripId"));
        tripAlert.setTripProgressId(rs.getString("tripProgressId"));
        tripAlert.setAlert(rs.getString("alert"));
        tripAlert.setApplicationNo(rs.getString("applicationNo"));
        tripAlert.setAlertDateTime(rs.getString("alertDateTime"));
        return tripAlert;
    }
}
