CREATE TABLE POI (
	id varchar(100) NOT NULL,
	locationName varchar(100) NOT NULL,
	status varchar(100) NULL,
	`type` varchar(100) NULL,
	locationDetails json NULL,
	alert json NULL,
	createdDate varchar(100) NULL,
	createdBy varchar(100) NULL,
	updatedDate varchar(100) NULL,
	updatedBy varchar(100) NULL,
	CONSTRAINT POI_PK PRIMARY KEY (id)
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

