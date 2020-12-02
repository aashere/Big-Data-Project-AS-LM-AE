-- Create table for all emissions data by state
use ae1586;
drop table if exists allStates;
create external table allStates (
    state STRING,
    period STRING,
    avgCO2 FLOAT,
    medianCO2 FLOAT,
    minCO2 FLOAT,
    maxCO2 FLOAT,
    totalRecords INT)
row format delimited fields terminated by ','
location '/user/ae1586/project/statistics_output';

-- Create table for all manufacturing data by state
drop table if exists allStatesManufacturing;
create external table allStatesManufacturing (
    state STRING,
    label STRING,
    value decimal(30,1))
row format delimited fields terminated by ','
location '/user/ae1586/project/joined_manufacturing/';


-- Create correlation table
drop table if exists emissionsManufacturingCorrelation;
create table emissionsManufacturingCorrelation as
select
  allStates.state as state,
  allStates.period as period,
  allStates.avgCO2 as "x",
  allStatesManufacturing.value  as "y",
  allStates.avgCO2*allStatesManufacturing.value as "xy",
  allStates.avgCO2*allStates.avgCO2 as "xx",
  allStatesManufacturing.value*allStatesManufacturing.value as "yy"
from allStates JOIN allStatesManufacturing
  ON allStates.state = allStatesManufacturing.state and allStates.period = allStatesManufacturing.label;
