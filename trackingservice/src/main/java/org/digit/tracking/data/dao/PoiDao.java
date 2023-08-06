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
    final String sqlFetchPoiById = "SELECT id, locationName, status, type, locationDetails, alert, userId FROM POI where id = ?";
    final String sqlFetchPoiByFilters = "SELECT * FROM POI";
    final String sqlCreatePoi = "insert into POI (id, locationName, status, type, alert, " +
            "createdDate, createdBy, updatedDate, updatedBy, userId, positionPoint, positionPolygon, positionLine) " +
            "values (?,?,?,?,?,?,?,?,?,?, ST_GeomFromText(?, 4326), ST_GeomFromText(?, 4326), ST_GeomFromText(?, 4326) )";
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

    public List<POI> fetchPOIbyFilters() {
        logger.info("## fetchPOIbyFilters");
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        List<POI> poiList = jdbcTemplateObject.query(sqlFetchPoiByFilters, new POIMapper());

        return poiList;
    }

    //Create POI and save it in database
    public String createPOI(POI poi) {
        logger.info("## createPOI");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        //Prepare input data for the SQL
        String idLocal = DbUtil.getId();
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
            StringBuffer polyBuffStr = new StringBuffer();
            int indx = 1;
            for (Location location : poi.getLocationDetails()) {
                polyBuffStr.append(location.getLatitude() + " " + location.getLongitude() );
                //Avoid a comma after the last element in concatenated list
                if (indx < poi.getLocationDetails().size()) {
                    indx++;
                    polyBuffStr.append(" , ");
                }
            }
            positionPolygon = "POLYGON((" + polyBuffStr + "))";
        } else {
            //In case of a LINE, only two LatLongs will are required
//            positionLine = "LINESTRING(" + poi.getLocationDetails().get(0).getLatitude() + " " + poi.getLocationDetails().get(0).getLongitude() + " , "
//                    + poi.getLocationDetails().get(1).getLatitude() + " " + poi.getLocationDetails().get(1).getLongitude() + ")";
            StringBuffer polyBuffStr = new StringBuffer();
            int indx = 1;
            for (Location location : poi.getLocationDetails()) {
                polyBuffStr.append(location.getLatitude() + " " + location.getLongitude() );
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

        logger.info(sqlCreatePoi);

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
