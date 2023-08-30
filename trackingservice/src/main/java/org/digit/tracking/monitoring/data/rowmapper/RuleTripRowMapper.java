package org.digit.tracking.monitoring.data.rowmapper;

import org.digit.tracking.monitoring.data.model.RuleData;
import org.digit.tracking.monitoring.data.model.RuleTrip;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RuleTripRowMapper implements RowMapper<RuleTrip> {
    public RuleTrip mapRow(ResultSet rs, int rowNum) throws SQLException {
        RuleTrip ruleTrip = new RuleTrip();
        ruleTrip.setRuleCode(rs.getString("ruleCode"));
        ruleTrip.setImplCode(rs.getString("implCode"));
        ruleTrip.setTripId(rs.getString("tripId"));
        return ruleTrip;
    }

}
