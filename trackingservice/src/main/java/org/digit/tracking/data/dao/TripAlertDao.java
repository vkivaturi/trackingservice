package org.digit.tracking.data.dao;

import org.digit.tracking.data.model.TripAlert;
import org.digit.tracking.data.rowmapper.RouteMapper;
import org.digit.tracking.data.rowmapper.TripAlertMapper;
import org.openapitools.model.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
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
    final String sqlFetchTripAlertsByFilters = "SELECT ta.id as id, ta.tripId as tripId, ta.tenantId as tenantId, ta.tripProgressId as tripProgressId, " +
            "ta.alert as alert, ta.alertDateTime as alertDateTime, tr.referenceNo as applicationNo " +
            "FROM TripAlert ta, Trip tr " +
            "where " +
            "ta.tenantId = COALESCE(:tenantId, ta.tenantId) and " +
            "ta.tripId = COALESCE(:tripId, ta.tripId) and " +
            "ta.alertDateTime BETWEEN COALESCE(:startDate, ta.alertDateTime) and COALESCE(:endDate, ta.alertDateTime) and " +
            "ta.tripId = tr.id and " +
            "tr.referenceNo = COALESCE(:applicationNo, tr.referenceNo) ";
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

        List<TripAlert> tripAlertList = namedParameterJdbcTemplate.query(sqlFetchTripAlertsByFilters, params, new TripAlertMapper());
        return tripAlertList;
    }

}
