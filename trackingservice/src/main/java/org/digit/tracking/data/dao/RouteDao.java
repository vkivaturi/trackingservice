package org.digit.tracking.data.dao;

import org.digit.tracking.data.rowmapper.RouteMapper;
import org.digit.tracking.util.DbUtil;
import org.digit.tracking.util.JsonUtil;
import org.openapitools.model.Route;
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
public class RouteDao {
    Logger logger = LoggerFactory.getLogger(RouteDao.class);

    @Autowired
    DbUtil dbUtil;
    final String sqlFetchRouteById = "SELECT id, startPoi, endPoi, name, status, intermediatePois, " +
            "userId FROM Route where id = ?";
    final String sqlFetchRouteByFilters = "SELECT * FROM Route";
    final String sqlCreateRoute = "insert into Route (id, startPoi, endPoi, name, status, intermediatePois, " +
            "userId, createdDate, createdBy, updatedDate, updatedBy) values (?,?,?,?,?, ?,?,?,?,?,?)";
    private DataSource dataSource;

    //Datasource bean is injected
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Route> fetchRoutebyId(String routeId) {
        logger.info("## fetchRoutebyId");
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        Object[] args = new Object[]{routeId};
        List<Route> routeList = jdbcTemplateObject.query(sqlFetchRouteById, new RouteMapper(), args);
        return routeList;
    }
    public List<Route> fetchRoutebyFilters() {
        logger.info("## fetchRoutebyFilters");
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        List<Route> routeList = jdbcTemplateObject.query(sqlFetchRouteByFilters, new RouteMapper());
        return routeList;
    }
    //Create Route and save it in database
    public String createRoute(Route route) {
        logger.info("## createRoute");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        //Prepare input data for the SQL
        String idLocal = dbUtil.getId();
        String intermediatePois = JsonUtil.getJsonFromObject(route.getIntermediatePois());

        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        String currentDateString = offsetDateTime.format(DateTimeFormatter.ISO_DATE_TIME);

        //Audit information
        String createdBy = route.getUserId();
        String updatedBy = route.getUserId();

        Object[] args = new Object[]{idLocal, route.getStartPoi(), route.getEndPoi(), route.getName(), route.getStatus().toString(),
                intermediatePois, route.getUserId(), currentDateString,
                createdBy, currentDateString, updatedBy};

        int result = jdbcTemplate.update(sqlCreateRoute, args);
        if (result != 0) {
            logger.info("Route created with id " + idLocal);
            return idLocal;
        } else {
            logger.error("Route creation failed with id, locationName " + idLocal + " " + route.getName());
            return null;
        }
    }
}
