package org.digit.tracking.data.dao;

import org.apache.tomcat.util.bcel.Const;
import org.digit.tracking.data.model.TripAlert;
import org.digit.tracking.data.rowmapper.RouteMapper;
import org.digit.tracking.data.rowmapper.TripAlertMapper;
import org.digit.tracking.util.Constants;
import org.openapitools.model.Route;
import org.openapitools.model.Trip;
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
public class TripAlertDao {
    Logger logger = LoggerFactory.getLogger(TripAlertDao.class);
    private DataSource dataSource;

    //Datasource bean is injected
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //Fetch trip alerts and map them to the application numbers
    final String sqlFetchTripAlertsByFilters = "SELECT ta.tripId as tripId, ta.tenantId as tenantId, ta.tripProgressId as tripProgressId, " +
            "ta.alert as alert, max(ta.alertDateTime) as alertDateTime, tr.referenceNo as applicationNo, '' as id "+
            "FROM TripAlert ta, Trip tr " +
            "where " +
            "ta.tenantId = COALESCE(:tenantId, ta.tenantId) and " +
            "ta.tripId = COALESCE(:tripId, ta.tripId) and " +
            "ta.alertDateTime BETWEEN COALESCE(:startDate, ta.alertDateTime) and COALESCE(:endDate, ta.alertDateTime) and " +
            "ta.tripId = tr.id and " +
            "tr.referenceNo = COALESCE(:applicationNo, tr.referenceNo) " +
            "group by ta.tripId, ta.tenantId, ta.tripProgressId, ta.alert, tr.referenceNo having count(ta.alert) > (case when ta.alert = :stoppageAlertCode then :stoppageAlertThreshold else 0 end);";

    //Search for TripAlerts based on filters
    public List<TripAlert> fetchTripAlertsByFilters(String tenantId, String applicationNo, String tripId, String startDate, String endDate) {
        logger.info("## fetchTripAlertsByFilters");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("tenantId", tenantId);
        params.put("applicationNo", applicationNo);
        params.put("tripId", tripId);
        params.put("startDate", startDate);
        params.put("endDate", endDate);
        params.put("stoppageAlertCode", Constants.ILLEGAL_DUMP_YARD_STOPPAGE_CODE);
        params.put("stoppageAlertThreshold", Constants.ILLEGAL_DUMP_YARD_STOPPAGE_THRESHOLD);

        List<TripAlert> tripAlertList = namedParameterJdbcTemplate.query(sqlFetchTripAlertsByFilters, params, new TripAlertMapper());
        return tripAlertList;
    }

    public String updateTripAlert(TripAlert tripAlert) {
        logger.info("## updateTripAlert inside DAO");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        Map<String,Object> params = new HashMap<String,Object>();
        params.put("tripProgressId", tripAlert.getTripProgressId());
        params.put("alert", tripAlert.getAlert());
        params.put("alertDateTime", tripAlert.getAlertDateTime());

        int rowsUpdated = namedParameterJdbcTemplate.update(sqlFetchTripAlertsByFilters, params);

        if (rowsUpdated != 0) {
            logger.info("Trip alert updated with id " + tripAlert.getTripProgressId());
            return tripAlert.getTripProgressId();
        } else {
            logger.error("Trip update failed with id " + tripAlert.getTripProgressId());
            return null;
        }
    }
}
