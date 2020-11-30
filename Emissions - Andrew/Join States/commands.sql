--Select all records
select * from allStates;

-- find states with max avg CO2
select * from allStates where period="ALL" order by avgCO2 DESC;

-- get all month period readings
select * from allStates where period<>"ALL" and period<>"2019" and period<>"2020";


--export into a csv
impala-shell -B -q "use ae1586; select * from allStates" -o 'all_state_emissions.csv' --output_delimiter=',';

-- Should put state, period, avgCO2 and employment into one table
select allStates.state, allStates.period, allStates.avgCO2, joined.employment from allStates JOIN joined
  ON allStates.state = joined.state and allStates.period = joined.period
