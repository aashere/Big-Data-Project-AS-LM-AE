#Execute the SQL commands to bring all the individual state files into one joined table and remove the extra header rows
ssh -t compute-1-2 "cd pbdaa/project/join ; impala-shell -f join.sql ; exit"
#Export the joined table to HDFS as a CSV
ssh -t compute-1-2 "cd pbdaa/project/join ; impala-shell -B -q 'use as12366; select * from joined order by state, label ASC' -o 'All_States.csv' --print_header --output_delimiter=',' ; exit"
#All_States.csv will contain the joined state data in HDFS
hdfs dfs -rm /user/as12366/project/joined_data/All_States.csv
hdfs dfs -put All_States.csv /user/as12366/project/joined_data
rm All_States.csv
