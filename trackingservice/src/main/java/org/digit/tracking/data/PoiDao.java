package org.digit.tracking.data;

import org.digit.tracking.util.DbUtil;
import org.digit.tracking.util.JsonUtil;
import org.openapitools.model.POI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

@Service
public class PoiDao {
    Logger logger = LoggerFactory.getLogger(PoiDao.class);
    final String sqlFetchPoiById = "SELECT id, locationName, status, type, locationDetails, alert, createdDate, createdBy, updatedDate, updatedBy FROM POI where id = ?";
    final String sqlFetchPoiByFilters = "SELECT * FROM POI";
    final String sqlCreatePoi = "insert into POI (id, locationName, status, type, locationDetails, alert, createdDate, createdBy, updatedDate, updatedBy) values (?,?,?,?,?,?,?,?,?,?)";
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
        String locationDetails = JsonUtil.getJsonFromObject(poi.getLocationDetails());
        String alerts = JsonUtil.getJsonFromObject(poi.getAlert());
        Object[] args = new Object[]{idLocal, poi.getLocationName(), poi.getStatus().toString(),
                poi.getType().toString(), locationDetails, alerts, poi.getAudit().getCreatedDate(),
                poi.getAudit().getCreatedBy(), poi.getAudit().getUpdatedDate(), poi.getAudit().getUpdatedBy()};

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
