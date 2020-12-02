# Remove last output
hdfs dfs -rm -r project/statistics_output

# Run the job
hadoop jar calculateStats.jar StateStats /user/ae1586/project/state_output /user/ae1586/project/statistics_output