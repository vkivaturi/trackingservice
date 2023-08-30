-- trackingdb.POI definition

CREATE TABLE "POI" (
  "id" varchar(100) NOT NULL,
  "locationName" varchar(100) NOT NULL,
  "status" varchar(100) DEFAULT NULL,
  "type" varchar(100) DEFAULT NULL,
  "alert" json DEFAULT NULL,
  "createdDate" varchar(100) DEFAULT NULL,
  "createdBy" varchar(100) DEFAULT NULL,
  "updatedDate" varchar(100) DEFAULT NULL,
  "updatedBy" varchar(100) DEFAULT NULL,
  "userId" varchar(100) DEFAULT NULL,
  "positionPoint" point NOT NULL /*!80003 SRID 4326 */,
  "positionPolygon" polygon NOT NULL /*!80003 SRID 4326 */,
  "positionLine" linestring NOT NULL /*!80003 SRID 4326 */,
  PRIMARY KEY ("id"),
  SPATIAL KEY "positionPoint" ("positionPoint"),
  SPATIAL KEY "positionPolygon" ("positionPolygon"),
  SPATIAL KEY "positionLine" ("positionLine"),
  KEY "POI_locationName_IDX" ("locationName"),
  KEY "POI_userId_IDX" ("userId")
);

-- trackingdb.Route definition

CREATE TABLE "Route" (
  "id" varchar(100) NOT NULL,
  "startPoi" varchar(100) DEFAULT NULL,
  "endPoi" varchar(100) DEFAULT NULL,
  "name" varchar(100) DEFAULT NULL,
  "status" varchar(100) DEFAULT NULL,
  "intermediatePois" json DEFAULT NULL,
  "createdDate" varchar(100) DEFAULT NULL,
  "createdBy" varchar(100) DEFAULT NULL,
  "updatedDate" varchar(100) DEFAULT NULL,
  "updatedBy" varchar(100) DEFAULT NULL,
  "userId" varchar(100) DEFAULT NULL,
  PRIMARY KEY ("id"),
  KEY "Route_name_IDX" ("name"),
  KEY "Route_userId_IDX" ("userId")
);

CREATE TABLE "Trip" (
  "id" varchar(100) NOT NULL,
  "operator" json DEFAULT NULL,
  "serviceCode" varchar(100) DEFAULT NULL,
  "status" varchar(100) DEFAULT NULL,
  "routeId" varchar(100) DEFAULT NULL,
  "plannedStartTime" varchar(100) DEFAULT NULL,
  "plannedEndTime" varchar(100) DEFAULT NULL,
  "actualStartTime" varchar(100) DEFAULT NULL,
  "actualEndTime" varchar(100) DEFAULT NULL,
  "userId" varchar(100) DEFAULT NULL,
  "createdDate" varchar(100) DEFAULT NULL,
  "createdBy" varchar(100) DEFAULT NULL,
  "updatedDate" varchar(100) DEFAULT NULL,
  "updatedBy" varchar(100) DEFAULT NULL,
  "locationAlerts" json DEFAULT NULL,
  PRIMARY KEY ("id")
);

-- trackingdb.TripProgress definition

CREATE TABLE "TripProgress" (
  "id" varchar(100) NOT NULL,
  "tripId" varchar(100) DEFAULT NULL,
  "progressReportedTime" varchar(100) DEFAULT NULL,
  "userId" varchar(100) DEFAULT NULL,
  "positionPoint" point DEFAULT NULL,
  "progressTime" varchar(100) DEFAULT NULL,
  "matchedPoiId" varchar(100) DEFAULT NULL,
  "createdBy" varchar(100) DEFAULT NULL,
  "createdDate" varchar(100) DEFAULT NULL,
  "updatedDate" varchar(100) DEFAULT NULL,
  "updatedBy" varchar(100) DEFAULT NULL,
  "locationAlertCode" varchar(100) DEFAULT NULL,
  PRIMARY KEY ("id")
);

-- trackingdb.ServiceType definition

CREATE TABLE "ServiceType" (
  "Code" varchar(100) NOT NULL,
  "Name" varchar(100) NOT NULL,
  "tenantId" varchar(100) DEFAULT NULL,
  PRIMARY KEY ("Code")
);

CREATE TABLE "LocationAlert" (
  "code" varchar(100) NOT NULL,
  "title" varchar(100) NOT NULL,
  PRIMARY KEY ("code")
);

-- trackingdb.Rule definition

CREATE TABLE "Rule" (
  "ruleCode" varchar(100) NOT NULL,
  "description" varchar(1000) DEFAULT NULL,
  "implCode" varchar(100) DEFAULT NULL,
  PRIMARY KEY ("ruleCode"),
  KEY "Rule_ruleCode_IDX" ("ruleCode")
);

-- trackingdb.RuleMapping definition

CREATE TABLE "RuleMapping" (
  "id" bigint NOT NULL AUTO_INCREMENT,
  "ruleCode" varchar(100) NOT NULL,
  "tenantId" varchar(100) DEFAULT NULL,
  "serviceCode" varchar(100) DEFAULT NULL,
  PRIMARY KEY ("id"),
  KEY "RuleMapping_ruleCode_IDX" ("ruleCode"),
  KEY "RuleMapping_tenantId_IDX" ("tenantId"),
  KEY "RuleMapping_serviceCode_IDX" ("serviceCode")
);

-- trackingdb.RuleTrip definition

CREATE TABLE "RuleTrip" (
  "tripId" varchar(100) NOT NULL,
  "ruleCode" varchar(100) NOT NULL,
  "implCode" varchar(100) NOT NULL,
  PRIMARY KEY ("tripId","ruleCode"),
  KEY "RuleTrip_implCode_IDX" ("implCode")
);


