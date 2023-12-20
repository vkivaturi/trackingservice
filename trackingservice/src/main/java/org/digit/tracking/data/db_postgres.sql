CREATE TABLE "POI" (
  "id" varchar(100) NOT NULL,
  "locationName" varchar(100) NOT NULL,
  "status" varchar(100) DEFAULT NULL,
  "type" varchar(100) DEFAULT NULL,
  "createdDate" varchar(100) DEFAULT NULL,
  "createdBy" varchar(100) DEFAULT NULL,
  "updatedDate" varchar(100) DEFAULT NULL,
  "updatedBy" varchar(100) DEFAULT NULL,
  "userId" varchar(100) DEFAULT NULL,
  "position" geometry NOT NULL,
  "tenantId" varchar(100) DEFAULT NULL,
  "alert" varchar(100) DEFAULT NULL,
  PRIMARY KEY ("id"),
  CONSTRAINT "POI_locationName_IDX" UNIQUE ("locationName"),
  CONSTRAINT "POI_userId_IDX" UNIQUE ("userId")
);

CREATE TABLE "Route" (
  "id" varchar(100) NOT NULL,
  "startPoi" varchar(100) DEFAULT NULL,
  "endPoi" varchar(100) DEFAULT NULL,
  "name" varchar(100) DEFAULT NULL,
  "status" varchar(100) DEFAULT NULL,
  "intermediatePois" jsonb DEFAULT NULL,
  "createdDate" varchar(100) DEFAULT NULL,
  "createdBy" varchar(100) DEFAULT NULL,
  "updatedDate" varchar(100) DEFAULT NULL,
  "updatedBy" varchar(100) DEFAULT NULL,
  "userId" varchar(100) DEFAULT NULL,
  PRIMARY KEY ("id"),
  CONSTRAINT "Route_name_IDX" UNIQUE ("name"),
  CONSTRAINT "Route_userId_IDX" UNIQUE ("userId")
);

CREATE TABLE "Trip" (
  "id" varchar(100) NOT NULL,
  "operator" jsonb DEFAULT NULL,
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
  "tenantId" varchar(100) DEFAULT NULL,
  "tripEndType" varchar(100) DEFAULT NULL,
  "referenceNo" varchar(100) DEFAULT NULL,
  "alerts" varchar(100) DEFAULT NULL,
  PRIMARY KEY ("id")
);

CREATE TABLE "TripProgress" (
  "id" varchar(100) NOT NULL,
  "tripId" varchar(100) DEFAULT NULL,
  "progressReportedTime" varchar(100) DEFAULT NULL,
  "userId" varchar(100) DEFAULT NULL,
  "positionPoint" geometry DEFAULT NULL,
  "progressTime" varchar(100) DEFAULT NULL,
  "matchedPoiId" varchar(100) DEFAULT NULL,
  "createdBy" varchar(100) DEFAULT NULL,
  "createdDate" varchar(100) DEFAULT NULL,
  "updatedDate" varchar(100) DEFAULT NULL,
  "updatedBy" varchar(100) DEFAULT NULL,
  "locationAlertCode" varchar(100) DEFAULT NULL,
  PRIMARY KEY ("id")
);

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

CREATE TABLE "TripAlert" (
  "id" varchar(100) NOT NULL,
  "tripId" varchar(100) DEFAULT NULL,
  "tripProgressId" varchar(100) DEFAULT NULL,
  "alert" varchar(100) DEFAULT NULL,
  "alertDateTime" varchar(100) DEFAULT NULL,
  "tenantId" varchar(100) DEFAULT NULL,
  PRIMARY KEY ("id"),
  CONSTRAINT "TripAlert_tripId_IDX" FOREIGN KEY ("tripId") REFERENCES "Trip"("id")
);
