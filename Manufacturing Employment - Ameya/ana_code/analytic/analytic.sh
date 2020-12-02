#Get Lucille's Covid data from her Hive table - Note: I used an environment variable $HIVE_PASSWORD in order to avoid writing my password directly in this script. This first line may fail as a result, but if the "monthlycasesoutput" table is retrieved from Lucille's Hive database and put in the local directory, the rest of the script will work.
beeline -u jdbc:hive2://babar.es.its.nyu.edu:10000/ -n as12366 -p "$HIVE_PASSWORD" --outputformat=csv2 -e 'use lam923; select * from monthlycasesoutput order by state, month ASC' > monthlycasesoutput.csv
#Move Lucille's Covid data to HDFS
hdfs dfs -put monthlycasesoutput.csv /user/as12366/project/covid_data
rm monthlycasesoutput.csv

#Run the commands in Impala to correlate the Manufacturing and Covid datasets and get correlation coefficients for each state
ssh -t compute-1-2 "cd pbdaa/project/analytic ; impala-shell -f analytic.sql; exit"
#Export the final analytic (the correlation coefficients) to HDFS
ssh -t compute-1-2 "cd pbdaa/project/analytic ; impala-shell -B -q 'use as12366; select * from manufacturing_vs_covid order by state ASC limit 49' -o 'Manufacturing_vs_Covid_FINAL.csv' --print_header --output_delimiter=',' ; exit"

#Manufacturing_vs_Covid_FINAL.csv will contain the final analytic data
hdfs dfs -rm /user/as12366/project/final_analytic/Manufacturing_vs_Covid_FINAL.csv
hdfs dfs -put Manufacturing_vs_Covid_FINAL.csv /user/as12366/project/final_analytic
rm Manufacturing_vs_Covid_FINAL.csv
