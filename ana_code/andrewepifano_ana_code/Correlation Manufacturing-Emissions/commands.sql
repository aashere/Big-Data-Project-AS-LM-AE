--Save manufacturing data from Ameya's impala table to a csv so I can import
impala-shell -B -q "use as12366; select * from normalized" -o 'all_state_manufacturing.csv' --output_delimiter=',';

--Select all records
select * from allStates;

-- find states with max avg CO2
select * from allStates where period="ALL" order by avgCO2 DESC;

-- get all month period readings
select * from allStates where period<>"ALL" and period<>"2019" and period<>"2020";

--export into a csv
impala-shell -B -q "use ae1586; select * from allStates" -o 'all_state_emissions.csv' --output_delimiter=',';

-- Calculate correlation of all data
select (count(*)*sum(xy) - sum(x)*sum(y))/SQRT((count(*)*sum(xx) - sum(x)*sum(x))*(count(*)*sum(yy) - sum(y)*sum(y)))
as correlation from emissionsManufacturingCorrelation;

-- Calculate correlation by state
SELECT state,
(count(state)*sum(xy) - sum(x)*sum(y))/SQRT((count(state)*sum(xx) - sum(x)*sum(x))*(count(state)*sum(yy) - sum(y)*sum(y)))
as correlation
from emissionsManufacturingCorrelation GROUP BY state ORDER BY ABS(correlation) DESC;
