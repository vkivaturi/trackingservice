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
  PRIMARY KEY ("id")
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
  PRIMARY KEY ("id")
);

CREATE TABLE "ServiceType" (
  "Code" varchar(100) NOT NULL,
  "Name" varchar(100) NOT NULL,
  "ULBid" varchar(100) DEFAULT NULL,
  PRIMARY KEY ("Code")
);

CREATE TABLE "LocationAlert" (
  "code" varchar(100) NOT NULL,
  "title" varchar(100) NOT NULL,
  PRIMARY KEY ("code")
);

