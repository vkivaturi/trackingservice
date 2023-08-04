package org.digit.tracking.data;

import org.digit.tracking.util.IdUtil;
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
    final String sqlFetchPoiById = "SELECT * FROM POI";
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

        List<POI> poiList = jdbcTemplateObject.query(sqlFetchPoiById, new POIMapper());

        return poiList;
    }

    public List<POI> fetchPOIbyFilters() {
        logger.info("## fetchPOIbyFilters");
        JdbcTemplate jdbcTemplateObject = new JdbcTemplate(dataSource);
        List<POI> poiList = jdbcTemplateObject.query(sqlFetchPoiByFilters, new POIMapper());

        return poiList;
    }

    public String createPOI(POI poi) {
        logger.info("## createPOI");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        String idLocal = IdUtil.getId();
        Object[] args = new Object[]{idLocal, poi.getLocationName(), poi.getStatus(), poi.getType(), poi.getLocationDetails(), poi.getAlert(), poi.getAudit()};
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
