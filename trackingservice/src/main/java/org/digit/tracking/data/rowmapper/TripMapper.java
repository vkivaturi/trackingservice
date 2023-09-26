package org.digit.tracking.data.rowmapper;

import org.digit.tracking.util.DbUtil;
import org.openapitools.model.Operator;
import org.openapitools.model.Trip;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TripMapper implements RowMapper<Trip> {
    DbUtil dbUtil = new DbUtil();
    public Trip mapRow(ResultSet rs, int rowNum) throws SQLException {
        Trip trip = new Trip();
        trip.setId(rs.getString("id"));
        trip.setOperator(dbUtil.dbJsonToOperator(rs, "operator", Operator.class));
        trip.setServiceCode(rs.getString("serviceCode"));
        trip.setStatus(Trip.StatusEnum.valueOf(rs.getString("status").toUpperCase()));
        trip.setRouteId(rs.getString("routeId"));
        trip.setActualEndTime(rs.getString("actualEndTime"));
        trip.setActualStartTime(rs.getString("actualStartTime"));
        trip.setPlannedStartTime(rs.getString("plannedStartTime"));
        trip.setPlannedEndTime(rs.getString("plannedEndTime"));
        trip.setUserId(rs.getString("userId"));
        trip.setTenantId(rs.getString("tenantId"));
        trip.setReferenceNo(rs.getString("referenceNo"));
        trip.setTripEndType(rs.getString("tripEndType"));
        trip.setAlerts(rs.getString("alerts"));
        return trip;
    }
}
