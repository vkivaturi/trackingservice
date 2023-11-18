package org.digit.tracking.data.dao;

import org.digit.tracking.data.rowmapper.POIMapper;
import org.digit.tracking.util.DaoUtil;
import org.digit.tracking.util.DbUtil;
import org.digit.tracking.util.JsonUtil;
import org.openapitools.model.Location;
import org.openapitools.model.POI;
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
public class PoiDao {
    Logger logger = LoggerFactory.getLogger(PoiDao.class);

    @Autowired
    DbUtil dbUtil;
    final String sqlFetchPoiById = "SELECT id, locationName, status, type, alert, userId, " +
            "ST_AStext(positionPoint) as positionPoint, ST_AStext(positionPolygon) as positionPolygon, " +
            "ST_AStext(positionLine) as positionLine, 0 as distanceMeters FROM POI where id = ? and status = 'active' ";

    //Query filter is slightly complicated as it has to handle scenario where input field is null and data in table is also null.
    //In such case, COALESCE is incorrect as MySQL returns a false for null = null check
    final String sqlFetchPoiByFilters = "SELECT id, locationName, status, type, alert, userId, " +
            "ST_AStext(positionPoint) as positionPoint, ST_AStext(positionPolygon) as positionPolygon, " +
            "ST_AStext(positionLine) as positionLine, 0 as distanceMeters FROM POI " +
            "where " +
            "tenantId = :tenantId " +
            "and status = 'active' " +
            "and locationName like COALESCE(:locationName, locationName) " +
            "and ((alert is null and :alert is null) or (alert = COALESCE(:alert, alert))) " +
            ";";

    final String sqlSearchNearbyOfLocation = "SELECT id, locationName, status, type, alert, userId, " +
            "ST_AStext(positionPoint) as positionPoint, " +
            "ST_AStext(positionPolygon) as positionPolygon, " +
            "ST_AStext(positionLine) as positionLine, " +
            "CASE " +
            "WHEN type = 'point' THEN ST_Distance(positionPoint, ST_GeomFromText( ?, 4326 )) " +
            "WHEN type = 'line' THEN ST_Distance(positionLine, ST_GeomFromText( ?, 4326 )) " +
            "WHEN type = 'polygon' THEN ST_Distance(positionPolygon, ST_GeomFromText( ?, 4326 )) " +
            "END AS distanceMeters " +
            "FROM POI poi " +
            "where status = 'active' " +
            "HAVING distanceMeters <= ? " +
            "order by distanceMeters asc;";
    final String sqlCreatePoi = "insert into POI (id, tenantId, locationName, status, type, alert, " +
            "createdDate, createdBy, updatedDate, updatedBy, userId, positionPoint, positionPolygon, positionLine) " +
            "values (?,?,?,?,?,?,?,?,?,?,?, " +
            "ST_PointFromText(?, 4326, 'axis-order=lat-long'), " +
            "ST_GeomFromText(?, 4326, 'axis-order=lat-long'), " +
            "ST_GeomFromText(?, 4326, 'axis-order=lat-long') )";
    final String sqlUpdatePoi = "update POI " +
            "set " +
            "type = :type, " +
            "positionPoint = ST_PointFromText(:positionPoint, 4326, 'axis-order=lat-long'), " +
            "positionPolygon = ST_GeomFromText(:positionPolygon, 4326, 'axis-order=lat-long'), " +
            "positionLine = ST_GeomFromText(:positionLine, 4326, 'axis-order=lat-long'), " +
            "updatedDate = :currentDateString , " +
            "updatedBy = :updatedBy " +
            "where id = :poiId " +
            "and tenantId = :tenantId";
    final String sqlUpdateInactivatePoi = "update POI " +
            "set " +
            "status = :status, " +
            "updatedDate = :currentDateString , " +
            "updatedBy = :updatedBy " +
            "where id = :poiId " +
            "and tenantId = :tenantId";

    private DataSource dataSource;

    //Datasource bean is injected
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<POI> fetchPOIbyId(String poiId) {
        logger.info("## fetchPOIbyId");
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        Object[] args = new Object[]{poiId};
        List<POI> poiList = jdbcTemplateObject.query(sqlFetchPoiById, new POIMapper(), args);

        return poiList;
    }

    public List<POI> searchNearby(Location userLocation, int distanceMeters) {
        logger.info("## searchNearby in Dao");
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        String positionPoint = " POINT(" + userLocation.getLatitude() + " " + userLocation.getLongitude() + ")";
        //TODO - Try to avoid repetition of same argument. Use named parameters
        Object[] args = new Object[]{positionPoint, positionPoint, positionPoint, distanceMeters};
        List<POI> poiList = jdbcTemplateObject.query(sqlSearchNearbyOfLocation, new POIMapper(), args);

        return poiList;
    }

    public List<POI> fetchPOIbyFilters(String locationName, String alert, String tenantId) {
        logger.info("## fetchPOIbyFilters");

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("tenantId", tenantId);
        params.put("alert", alert);

        //Partial search is supported for location name
        if (locationName != null) {
            params.put("locationName", "%"+locationName+"%");
        }else{
            params.put("locationName", null);
        }

        List<POI> poiList = namedParameterJdbcTemplate.query(sqlFetchPoiByFilters, params, new POIMapper());

        return poiList;
    }

    //Update POI based on supported fields
    public String updatePOI(POI poi) {
        logger.info("## updatePOI inside DAO");
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        String currentDateString = offsetDateTime.format(DateTimeFormatter.ISO_DATE_TIME);

        Map<String, String> positionsMap = DaoUtil.getPositionStringMap(poi);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("tenantId", poi.getTenantId());
        params.put("poiId", poi.getId());
        params.put("type", poi.getType().getValue());
        params.put("positionPoint", positionsMap.get("positionPoint"));
        params.put("positionPolygon", positionsMap.get("positionPolygon"));
        params.put("positionLine", positionsMap.get("positionLine"));
        params.put("currentDateString", currentDateString);
        params.put("updatedBy", poi.getUserId());

        int result = namedParameterJdbcTemplate.update(sqlUpdatePoi, params);
        if (result != 0) {
            logger.info("POI updated with id " + poi.getId());
            return poi.getId();
        } else {
            logger.error("POI update failed with id " + poi.getId());
            return null;
        }
    }

    //Create POI and save it in database
    public String createPOI(POI poi) {
        logger.info("## createPOI");
        //TODO - Convert this to named jdbc template
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        //Prepare input data for the SQL
        String idLocal = dbUtil.getId();
        String alerts = JsonUtil.getJsonFromObject(poi.getAlert());

        Map<String, String> positionsMap = DaoUtil.getPositionStringMap(poi);

        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        String currentDateString = offsetDateTime.format(DateTimeFormatter.ISO_DATE_TIME);

        //Audit information
        String createdBy = poi.getUserId();
        String updatedBy = poi.getUserId();

        Object[] args = new Object[]{idLocal, poi.getTenantId(), poi.getLocationName(), poi.getStatus().toString(),
                poi.getType().toString(), alerts, currentDateString,
                createdBy, currentDateString, updatedBy, poi.getUserId(), positionsMap.get("positionPoint"),
                positionsMap.get("positionPolygon"), positionsMap.get("positionLine")};

        int result = jdbcTemplate.update(sqlCreatePoi, args);
        if (result != 0) {
            logger.info("POI created with id " + idLocal);
            return idLocal;
        } else {
            logger.error("POI creation failed with id, locationName " + idLocal + " " + poi.getLocationName());
            return null;
        }
    }

    public String inactivatePOI(POI poi) {
        logger.info("## inactivatePOI inside DAO");
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        String currentDateString = offsetDateTime.format(DateTimeFormatter.ISO_DATE_TIME);

        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("tenantId", poi.getTenantId());
        params.put("poiId", poi.getId());
        params.put("updatedBy", poi.getUserId());
        params.put("status", POI.StatusEnum.INACTIVE.getValue());
        params.put("currentDateString", currentDateString);

        int result = namedParameterJdbcTemplate.update(sqlUpdateInactivatePoi, params);
        if (result != 0) {
            logger.info("POI inactivated with id " + poi.getId());
            return poi.getId();
        } else {
            logger.error("POI inactivation failed with id " + poi.getId());
            return null;
        }
    }
}
