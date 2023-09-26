package org.digit.tracking.data.dao;

import org.digit.tracking.data.rowmapper.TripMapper;
import org.digit.tracking.data.rowmapper.TripProgressMapper;
import org.digit.tracking.util.DbUtil;
import org.digit.tracking.util.JsonUtil;
import org.openapitools.model.Location;
import org.openapitools.model.Trip;
import org.openapitools.model.TripProgress;
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
public class TripDao {
    Logger logger = LoggerFactory.getLogger(TripDao.class);
    @Autowired
    DbUtil dbUtil;

    final String sqlFetchTripById = "SELECT id, operator, serviceCode, status, routeId, userId, " +
            " plannedStartTime, plannedEndTime, actualStartTime, actualEndTime, locationAlerts " +
            " FROM Trip where id = ?";
    //Join multiple tables to fetch trip related information
    final String sqlFetchTripByFilters = "SELECT tr.id, tr.operator, tr.serviceCode, tr.status, tr.routeId, tr.userId," +
            " tr.plannedStartTime, tr.plannedEndTime, tr.actualStartTime, tr.actualEndTime, tr.tenantId, " +
            " tr.tripEndType, tr.referenceNo, tr.alerts" +
            " FROM Trip tr " +
            "where " +
            "tr.tenantId = COALESCE(:tenantId, tr.tenantId) and " +
            "tr.serviceCode = COALESCE(:serviceCode, tr.serviceCode) and " +
            "tr.referenceNo = COALESCE(:referenceNos, tr.referenceNo)  "
            ;
    final String sqlCreateTrip = "insert into Trip (id, operator, serviceCode, status, routeId, userId, " +
            "plannedStartTime, plannedEndTime, actualStartTime, actualEndTime," +
            "createdDate, createdBy, updatedDate, updatedBy) values (?,?,?,?,?, ?,?,?,?,?,?,?,?,?)";
    final String sqlCreateTripProgressPoint = "insert into TripProgress (id, tripId, progressReportedTime, progressTime, positionPoint, userId) " +
            "values (?,?,?,?,ST_GeomFromText(?, 4326),?)";
    final String sqlUpdateTrip = "update Trip set routeId = COALESCE(?, routeId), status = COALESCE(?, status), locationAlerts = COALESCE(?, locationAlerts), " +
            "updatedDate = ? , updatedBy = ?" +
            "where id = ?";

    final String sqlUpdateTripProgress = "update TripProgress set matchedPoiId = ?, updatedDate = ? , updatedBy = ? where id = ?";

    final String sqlGetTripProgressById = "SELECT id, tripId, progressReportedTime, userId, ST_AStext(positionPoint) as positionPoint, progressTime, " +
            " matchedPoiId " +
            " FROM TripProgress where id = COALESCE(?, id) and tripId = COALESCE(?, tripId)";
    private DataSource dataSource;

    //Datasource bean is injected
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Trip fetchTripbyId(String tripId) {
        logger.info("## fetchTripbyId");
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        Object[] args = new Object[]{tripId};
        List<Trip> tripList = jdbcTemplateObject.query(sqlFetchTripById, new TripMapper(), args);
        return (tripList.isEmpty())? null : tripList.get(0);

    }
    public List<Trip> fetchTripbyFilters(String status, String userId, String operatorId, String tenantId, String businessService, String referenceNos ) {
        logger.info("## fetchTripbyFilters");
        //JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("tenantId", tenantId);
        params.put("serviceCode", businessService);
        params.put("referenceNos", referenceNos);

        //Object[] args = new Object[]{ status, userId, operatorId, tenantId, businessService, refernceNos};
        //List<Trip> tripList = jdbcTemplateObject.query(sqlFetchTripByFilters, new TripMapper(), args);
        List<Trip> tripList = namedParameterJdbcTemplate.query(sqlFetchTripByFilters, params, new TripMapper());
        return tripList;
    }
    //Create Trip and save it in database
    public String createTrip(Trip trip) {
        logger.info("## createTrip in table");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        //Prepare input data for the SQL
        String idLocal = dbUtil.getId();
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        String currentDateString = offsetDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
        //Audit information
        String createdBy = trip.getUserId();
        String updatedBy = trip.getUserId();

        Object[] args = new Object[]{idLocal, JsonUtil.getJsonFromObject(trip.getOperator()), trip.getServiceCode(), trip.getStatus().toString(),
                trip.getRouteId(), createdBy, trip.getPlannedStartTime(), trip.getPlannedEndTime(), trip.getActualStartTime(),
                trip.getActualEndTime(), currentDateString,
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

    //Update trip using an id and the supported list of attributes
    public String updateTrip(Trip trip) {
        logger.info("## updateTrip inside DAO");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        String currentDateString = offsetDateTime.format(DateTimeFormatter.ISO_DATE_TIME);

        String statusLocal = (trip.getStatus() != null) ? trip.getStatus().toString() : null;
        String alerts = (trip.getAlerts() != null) ? trip.getAlerts() : null;
        //Audit information
        String updatedBy = trip.getUserId();

        Object[] args = new Object[]{trip.getRouteId(), statusLocal, alerts,
                currentDateString, updatedBy, trip.getId()};

        int result = jdbcTemplate.update(sqlUpdateTrip, args);
        if (result != 0) {
            logger.info("Trip updated with id " + trip.getId());
            return trip.getId();
        } else {
            logger.error("Trip update failed with id " + trip.getId());
            return null;
        }
    }

    //Trip progress related data updates
    public String createTripProgress(Location location, String reportedTime, String progressTime, String tripId, String userId) {
        logger.info("## createTripProgress in table");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

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
    public List<TripProgress> getTripProgress(String tripProgressId, String tripId) {
        logger.info("## getTripProgressById");
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        Object[] args = new Object[]{tripProgressId, tripId};
        List<TripProgress> tripProgressList = jdbcTemplateObject.query(sqlGetTripProgressById, new TripProgressMapper(), args);
        return tripProgressList;
    }

}
