package org.digit.tracking.monitoring.data.rowmapper;

import org.digit.tracking.monitoring.data.model.RuleData;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RuleRowMapper implements RowMapper<RuleData> {
    public RuleData mapRow(ResultSet rs, int rowNum) throws SQLException {
        RuleData ruleData = new RuleData();
        ruleData.setRuleCode(rs.getString("ruleCode"));
        ruleData.setImplCode(rs.getString("implCode"));
        ruleData.setDescription(rs.getString("description"));
        return ruleData;
    }

}
