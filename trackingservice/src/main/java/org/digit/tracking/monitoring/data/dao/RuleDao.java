package org.digit.tracking.monitoring.data.dao;

import org.digit.tracking.monitoring.data.model.RuleData;
import org.digit.tracking.monitoring.data.model.RuleTrip;
import org.digit.tracking.monitoring.data.rowmapper.RuleRowMapper;
import org.digit.tracking.monitoring.data.rowmapper.RuleTripRowMapper;
import org.digit.tracking.util.DbUtil;
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
public class RuleDao {

    Logger logger = LoggerFactory.getLogger(RuleDao.class);

    @Autowired
    DbUtil dbUtil;

    final String sqlCreateRuleTrip = "insert into RuleTrip (tripId, ruleCode, implCode ) values (:tripId, :ruleCode, :implCode)";

    final String sqlFetchMappedRules = "SELECT rl.ruleCode, rl.implCode, rl.description " +
            "FROM Rule rl, RuleMapping rm " +
            "where " +
            "rm.tenantId = COALESCE(:tenantId, rm.tenantId) " +
            "and rm.serviceCode = COALESCE(:serviceCode, rm.serviceCode) " +
            "and rm.ruleCode = rl.ruleCode " +
            ";";

    final String sqlFetchRulesForTrip = "SELECT ruleCode, implCode, tripId " +
            "FROM RuleTrip " +
            "where " +
            "tripId = :tripId " +
            ";";

    private DataSource dataSource;

    //Datasource bean is injected
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //Create a rule trip entry and return the id of new entry created
    public int createRuleTrip(String tripId, String ruleCode, String implCode) {
        logger.info("## createRuleTrip");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("tripId", tripId);
        params.put("ruleCode", ruleCode);
        params.put("implCode", implCode);

        int result = namedParameterJdbcTemplate.update(sqlCreateRuleTrip, params);

        return result;
    }

    //Fetch rule mapping details based on tenant and service information
    public List<RuleData> getRuleDataByFilters(String tenantId, String serviceCode){
        logger.info("## getRuleDataByFilters");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("tenantId", tenantId);
        params.put("serviceCode", serviceCode);
        List<RuleData> ruleDataList = namedParameterJdbcTemplate.query(sqlFetchMappedRules, params, new RuleRowMapper());

        return ruleDataList;
    }

    //Fetch the list of rules mapped to a trip
    public List<RuleTrip> getRulesForTripByTripId(String tripId){
        logger.info("## getRulesForTripByTripId");
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("tripId", tripId);
        List<RuleTrip> ruleTripList = namedParameterJdbcTemplate.query(sqlFetchRulesForTrip, params, new RuleTripRowMapper());

        return ruleTripList;
    }

}
