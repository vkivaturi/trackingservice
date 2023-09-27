package org.digit.tracking.data.dao;

import org.digit.tracking.data.rowmapper.TripMapper;
import org.digit.tracking.data.rowmapper.TripProgressMapper;
import org.digit.tracking.data.rowmapper.TripProgressResponseMapper;
import org.digit.tracking.util.DbUtil;
import org.digit.tracking.util.JsonUtil;
import org.openapitools.model.Location;
import org.openapitools.model.Trip;
import org.openapitools.model.TripProgress;
import org.openapitools.model.TripProgressResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TripProgressDao {
    Logger logger = LoggerFactory.getLogger(TripProgressDao.class);
    @Autowired
    DbUtil dbUtil;

    final String sqlCreateTripProgressPoint = "insert into TripProgress (id, tripId, progressReportedTime, progressTime, positionPoint, userId) " +
            "values (?,?,?,?,ST_GeomFromText(?, 4326),?)";
    final String sqlUpdateTripProgress = "update TripProgress set matchedPoiId = ?, updatedDate = ? , updatedBy = ? where id = ?";

    final String sqlGetTripProgressById = "SELECT id, tripId, progressReportedTime, userId, ST_AStext(positionPoint) as positionPoint, progressTime, " +
            " matchedPoiId " +
            " FROM TripProgress where id = COALESCE(?, id) and tripId = COALESCE(?, tripId) " +
            "order by progressTime asc";
    private DataSource dataSource;

    //Datasource bean is injected
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //Trip progress related data updates
    public String createTripProgress(Location location, String reportedTime, String progressTime, String tripId, String userId) {
        logger.info("## createTripProgress in table");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        //TODO - Convert to named parameters
        String positionPoint = "POINT(" + location.getLatitude() + " " + location.getLongitude() + ")";
        //Prepare input data for the SQL
        String idLocal = dbUtil.getId();

        Object[] args = new Object[]{idLocal, tripId, reportedTime, progressTime, positionPoint, userId};

        int result = jdbcTemplate.update(sqlCreateTripProgressPoint, args);
        if (result != 0) {
            logger.info("Trip progress created with id " + idLocal);
            return idLocal;
        } else {
            logger.error("Trip progress creation failed with id, tripId " + idLocal + " " + tripId);
            return null;
        }
    }

    //Update trip progress using an id and the supported list of attributes
    public String updateTripProgress(String tripPogressId, String userId, String matchedPoiId) {
        logger.info("## updateTripProgress inside DAO");
        //TODO - Convert to named parameters
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        String currentDateString = offsetDateTime.format(DateTimeFormatter.ISO_DATE_TIME);

        Object[] args = new Object[]{matchedPoiId, currentDateString, userId, tripPogressId};

        int result = jdbcTemplate.update(sqlUpdateTripProgress, args);
        if (result != 0) {
            logger.info("Trip progress updated with id " + tripPogressId);
            return tripPogressId;
        } else {
            logger.error("Trip progress update failed with id " + tripPogressId);
            return null;
        }
    }
    //Get trip progress data based on progress id or trip id
    public List<TripProgressResponse> getTripProgress(String tripProgressId, String tripId) {
        logger.info("## getTripProgressById");
        //TODO - Convert to named parameters
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        Object[] args = new Object[]{tripProgressId, tripId};
        List<TripProgressResponse> tripProgressResponseList = jdbcTemplateObject.query(sqlGetTripProgressById, new TripProgressResponseMapper(), args);
        return tripProgressResponseList;
    }

}
