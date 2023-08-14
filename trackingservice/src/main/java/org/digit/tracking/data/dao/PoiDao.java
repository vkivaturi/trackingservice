package org.digit.tracking.data.dao;

import org.digit.tracking.data.rowmapper.POIMapper;
import org.digit.tracking.util.DbUtil;
import org.digit.tracking.util.JsonUtil;
import org.openapitools.model.Location;
import org.openapitools.model.POI;
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
public class PoiDao {
    Logger logger = LoggerFactory.getLogger(PoiDao.class);

    @Autowired
    DbUtil dbUtil;
    final String sqlFetchPoiById = "SELECT id, locationName, status, type, alert, userId, " +
            "ST_AStext(positionPoint) as positionPoint, ST_AStext(positionPolygon) as positionPolygon, " +
            "ST_AStext(positionLine) as positionLine FROM POI where id = ?";
    final String sqlFetchPoiByFilters = "SELECT * FROM POI";

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
            "HAVING distanceMeters <= ? " +
            "order by distanceMeters asc;";
    final String sqlCreatePoi = "insert into POI (id, locationName, status, type, alert, " +
            "createdDate, createdBy, updatedDate, updatedBy, userId, positionPoint, positionPolygon, positionLine) " +
            "values (?,?,?,?,?,?,?,?,?,?, " +
            "ST_PointFromText(?, 4326, 'axis-order=lat-long'), " +
            "ST_GeomFromText(?, 4326, 'axis-order=lat-long'), " +
            "ST_GeomFromText(?, 4326, 'axis-order=lat-long') )";
    final String sqlUpdatePoi = "update POI set locationName = COALESCE(?, locationName), status = COALESCE(?, status), " +
            "updatedDate = ? , updatedBy = ?" +
            "where id = ?";

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
        //TODO - Try to avoid repetition of same argument
        Object[] args = new Object[]{positionPoint, positionPoint, positionPoint, distanceMeters};
        List<POI> poiList = jdbcTemplateObject.query(sqlSearchNearbyOfLocation, new POIMapper(), args);

        return poiList;
    }

    public List<POI> fetchPOIbyFilters() {
        logger.info("## fetchPOIbyFilters");
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        List<POI> poiList = jdbcTemplateObject.query(sqlFetchPoiByFilters, new POIMapper());

        return poiList;
    }

    //Update POI based on supported fields
    public String updatePOI(POI poi) {
        logger.info("## updatePOI inside DAO");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        String currentDateString = offsetDateTime.format(DateTimeFormatter.ISO_DATE_TIME);

        String statusLocal = (poi.getStatus() != null) ? poi.getStatus().toString() : null;
        //Audit information
        String updatedBy = poi.getUserId();

        Object[] args = new Object[]{poi.getLocationName(), statusLocal,
                currentDateString, updatedBy, poi.getId()};

        int result = jdbcTemplate.update(sqlUpdatePoi, args);
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
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        //Prepare input data for the SQL
        String idLocal = dbUtil.getId();
        //String locationDetails = JsonUtil.getJsonFromObject(poi.getLocationDetails());
        String alerts = JsonUtil.getJsonFromObject(poi.getAlert());

        //Initialise spatial position fields
        String positionPoint = "POINT(0.0 0.0)";;
        String positionPolygon = "POLYGON((0.0 0.0, 0.1 0.1, 0.1 0.1, 0.0 0.0))";
        String positionLine = "LINESTRING(0.0 0.0, 0.1 0.1)";

        //TODO - Optimise the entire block of code here
        if (poi.getType() == POI.TypeEnum.POINT) {
            //In case of a POINT, only one LatLong pair will be sent by client app in locationDetails
            positionPoint = "POINT(" + poi.getLocationDetails().get(0).getLatitude() + " " + poi.getLocationDetails().get(0).getLongitude() + ")";
        } else if (poi.getType() == POI.TypeEnum.POLYGON) {
            StringBuilder polyBuffStr = new StringBuilder();
            int indx = 1;
            for (Location location : poi.getLocationDetails()) {
                polyBuffStr.append(location.getLatitude()).append(" ").append(location.getLongitude());
                //Avoid a comma after the last element in concatenated list
                if (indx < poi.getLocationDetails().size()) {
                    indx++;
                    polyBuffStr.append(" , ");
                }
            }
            positionPolygon = "POLYGON((" + polyBuffStr + "))";
        } else {
            StringBuilder polyBuffStr = new StringBuilder();
            int indx = 1;
            for (Location location : poi.getLocationDetails()) {
                polyBuffStr.append(location.getLatitude()).append(" ").append(location.getLongitude());
                //Avoid a comma after the last element in concatenated list
                if (indx < poi.getLocationDetails().size()) {
                    indx++;
                    polyBuffStr.append(" , ");
                }
            }
            positionLine = "LINESTRING(" + polyBuffStr + ")";
        }

        OffsetDateTime offsetDateTime = OffsetDateTime.now();
        String currentDateString = offsetDateTime.format(DateTimeFormatter.ISO_DATE_TIME);

        //Audit information
        String createdBy = poi.getUserId();
        String updatedBy = poi.getUserId();

        Object[] args = new Object[]{idLocal, poi.getLocationName(), poi.getStatus().toString(),
                poi.getType().toString(), alerts, currentDateString,
                createdBy, currentDateString, updatedBy, poi.getUserId(), positionPoint, positionPolygon, positionLine};

        int result = jdbcTemplate.update(sqlCreatePoi, args);
        if (result != 0) {
            logger.info("POI created with id " + idLocal);
            return idLocal;
        } else {
            logger.error("POI creation failed with id, locationName " + idLocal + " " + poi.getLocationName());
            return null;
        }
    }
}
