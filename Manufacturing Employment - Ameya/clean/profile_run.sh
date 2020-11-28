# Run the program on raw data
hadoop jar countrecs.jar CountRecs /user/as12366/project/raw_data/"$STATE".csv /user/as12366/project/output $STATE
hdfs dfs -get /user/as12366/project/output/part-00000
mv part-00000 "$STATE"_countrawrecs.txt
#Remove output directory
hdfs dfs -rm -r /user/as12366/project/output

# Run the program on clean data
hadoop jar countrecs.jar CountRecs /user/as12366/project/cleaned_data/"$STATE".csv /user/as12366/project/output $STATE
hdfs dfs -get /user/as12366/project/output/part-00000
mv part-00000 "$STATE"_countcleanrecs.txt
#Remove output directory
hdfs dfs -rm -r /user/as12366/project/output

#Print to bad_records.txt the difference between these two outputs to check for bad records
diff "$STATE"_countrawrecs.txt "$STATE"_countcleanrecs.txt >> bad_records.txt
rm "$STATE"_countrawrecs.txt "$STATE"_countcleanrecs.txt