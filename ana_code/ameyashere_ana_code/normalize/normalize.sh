#Execute the commands to turn state names into state codes and to remove leading 0's in the date labels
ssh -t compute-1-2 "cd pbdaa/project/normalize ; impala-shell -f normalize.sql ; exit"
#Export the normalized data to HDFS
ssh -t compute-1-2 "cd pbdaa/project/normalize ; impala-shell -B -q 'use as12366; select * from normalized order by state, label ASC' -o 'Manufacturing.csv' --print_header --output_delimiter=',' ; exit"
#Manufacturing.csv will contain the normalized data in HDFS
hdfs dfs -rm /user/as12366/project/normalized_data/Manufacturing.csv
hdfs dfs -put Manufacturing.csv /user/as12366/project/normalized_data
rm Manufacturing.csv
