package org.digit.tracking.util;

public class Constants {
    final public static String MSG_RESPONSE_SUCCESS_SAVE = "Data is saved successfully";
    final public static String MSG_RESPONSE_ERROR_SAVE = "Data is not saved. Error";
    final public static String MSG_RESPONSE_ERROR_FETCHING_DATA = "Data is not retrieved. Error";

    final public static String RULE_LOAD_METHOD = "loadModel";
    final public static String RULE_METHOD_PREFIX = "rule";
    final public static int POI_MATCH_THRESHOLD_METERS = 200;
    final public static String MONITORING_USER_ID = "MonitoringService";
    final public static String FMS_APPLICATION_URL = "https://unified-dev.digit.org/fsm/v1/";
    final public static String FMS_VEHICLE_TRIP_URL = "https://unified-dev.digit.org/vehicle/trip/v1/";
    final public static String ILLEGAL_DUMP_YARD_STOPPAGE_CODE = "Stoppage";
    final public static int ILLEGAL_DUMP_YARD_STOPPAGE_THRESHOLD = 5;
    final public static int TRIP_PROGRESS_FETCH_LIMIT = 1000;
    final public static String FSM_TRIP_COMPLETION_STATUS = "DISPOSE";
    final public static String TRIP_CLOSE_SYSTEM = "System verified";
    final public static String TRIP_CLOSE_DRIVER = "Driver initiated";

}
