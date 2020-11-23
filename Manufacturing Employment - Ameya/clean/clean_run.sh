# Run the program
hadoop jar clean.jar Clean /user/as12366/project/raw_data/"$STATE".csv /user/as12366/project/output $STATE
#Rename part file as csv and move to cleaned_data directory
hdfs dfs -mv /user/as12366/project/output/part-00000 /user/as12366/project/cleaned_data/"$STATE".csv
#Remove output directory
hdfs dfs -rm -r /user/as12366/project/output