CREATE KEYSPACE weather_daily
  WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };

CREATE TABLE temperature (
	id varchar,
  sender_id varchar,
  day varchar,
	month varchar,
	year varchar,
	temperature double,
  city_id varchar,
  city_name varchar,
	PRIMARY KEY (id,city_id,sender_id,day,month,year)
);