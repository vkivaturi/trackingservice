package org.digit.tracking.monitoring.data.model;

public class RuleTrip {

    private String tripId;

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getImplCode() {
        return implCode;
    }

    public void setImplCode(String implCode) {
        this.implCode = implCode;
    }

    private String ruleCode;
    private String implCode;


}
