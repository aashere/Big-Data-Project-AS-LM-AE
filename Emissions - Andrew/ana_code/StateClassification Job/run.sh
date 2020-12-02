# Remove last output
hdfs dfs -rm -r project/state_output

# Run the job for 2019 and 2020
hadoop jar classifyStates.jar StateClassification /user/ae1586/project/cleaned_output /user/ae1586/project/state_output