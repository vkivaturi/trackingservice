package org.digit.tracking.data.rowmapper;

import org.digit.tracking.util.DbUtil;
import org.openapitools.model.Route;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RouteMapper implements RowMapper<Route> {
    public Route mapRow(ResultSet rs, int rowNum) throws SQLException {
        Route route = new Route();
        route.setId(rs.getString("id"));
        route.setStartPoi(rs.getString("startPoi"));
        route.setEndPoi(rs.getString("endPoi"));
        route.setName(rs.getString("name"));
        route.setStatus(Route.StatusEnum.valueOf(rs.getString("status").toUpperCase()));
        route.setIntermediatePois(DbUtil.dbJsonToList(rs, "intermediatePois", String.class));
        route.setUserId(rs.getString("userId"));
        //route.setAudit(DbUtil.getAuditDetails(rs));
        return route;
    }
}
