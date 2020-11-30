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
