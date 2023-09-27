package org.digit.tracking.data.rowmapper;

import org.digit.tracking.util.DbUtil;
import org.openapitools.model.Location;
import org.openapitools.model.TripProgressResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TripProgressResponseMapper implements RowMapper<TripProgressResponse> {
    DbUtil dbUtil = new DbUtil();
    public TripProgressResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        TripProgressResponse tripProgress = new TripProgressResponse();
        tripProgress.setId(rs.getString("id"));
        tripProgress.setTripId(rs.getString("tripId"));
        tripProgress.setProgressReportedTime(rs.getString("progressReportedTime"));
        tripProgress.setProgressTime(rs.getString("progressTime"));
        tripProgress.setLocation(dbUtil.convertSpatialSinglePointToLocation(rs.getString("positionPoint")));
        return tripProgress;
    }
}
