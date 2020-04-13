CREATE TABLE "areas" (
  "id" varchar(50) PRIMARY KEY NOT NULL,
  "name" varchar(500),
  polygon polygon,
  area_sqm double precision
);


CREATE INDEX areas_polygon_geo on areas USING GiST (polygon);

