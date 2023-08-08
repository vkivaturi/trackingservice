package org.digit.tracking.data.dao;

import org.digit.tracking.data.rowmapper.TripMapper;
import org.digit.tracking.DbUtil;
import org.digit.tracking.JsonUtil;
import org.openapitools.model.Location;
import org.openapitools.model.Trip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class TripDao {
    Logger logger = LoggerFactory.getLogger(TripDao.class);
    final String sqlFetchTripById = "SELECT id, operator, serviceCode, status, routeId, userId," +
            " plannedStartTime, plannedEndTime, actualStartTime, actualEndTime" +
            " FROM Trip where id = ?";
    final String sqlFetchTripByFilters = "SELECT * FROM Trip";
    final String sqlCreateTrip = "insert into Trip (id, operator, serviceCode, status, routeId, userId, plannedStartTime, plannedEndTime, actualStartTime, actualEndTime," +
            "createdDate, createdBy, updatedDate, updatedBy) values (?,?,?,?,?, ?,?,?,?,?,?,?,?,?)";
    final String sqlCreateTripProgressPoint = "insert into TripProgress (id, tripId, progressReportedTime, progressTime, positionPoint, userId) " +
            "values (?,?,?,?,ST_GeomFromText(?, 4326),?)";

    private DataSource dataSource;

    //Datasource bean is injected
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Trip> fetchTripbyId(String tripId) {
        logger.info("## fetchTripbyId");
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        Object[] args = new Object[]{tripId};
        List<Trip> tripList = jdbcTemplateObject.query(sqlFetchTripById, new TripMapper(), args);
        return tripList;
    }
    public List<Trip> fetchTripbyFilters() {
        logger.info("## fetchTripbyFilters");
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        List<Trip> tripList = jdbcTemplateObject.query(sqlFetchTripByFilters, new TripMapper());
        return tripList;
    }
    //Create Trip and save it in database
    public String createTrip(Trip trip) {
        logger.info("## createTrip in table");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        //Prepare input data for the SQL
        String idLocal = DbUtil.getId();
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        String currentDateString = offsetDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        //Audit information
        String createdBy = trip.getUserId();
        String updatedBy = trip.getUserId();

        Object[] args = new Object[]{idLocal, JsonUtil.getJsonFromObject(trip.getOperator()), trip.getServiceCode(), trip.getStatus().toString(),
                trip.getRouteId(), trip.getPlannedStartTime(), trip.getPlannedEndTime(), trip.getActualStartTime(),
                trip.getActualEndTime(), trip.getUserId(), currentDateString,
                createdBy, currentDateString, updatedBy};

        int result = jdbcTemplate.update(sqlCreateTrip, args);
        if (result != 0) {
            logger.info("Trip created with id " + idLocal);
            return idLocal;
        } else {
            logger.error("Trip creation failed with id, locationName " + idLocal + " " + trip.getServiceCode());
            return null;
        }
    }

    public String createTripProgress(Location location, String reportedTime, String progressTime, String tripId, String userId) {
        logger.info("## createTripProgress in table");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        String positionPoint = "POINT(" + location.getLatitude() + " " + location.getLongitude() + ")";
        //Prepare input data for the SQL
        String idLocal = DbUtil.getId();

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

}
