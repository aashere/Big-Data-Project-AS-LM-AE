if [ $# -eq 0 ]
then
  hdfs dfs -ls project/statistics_output | grep -v "part"
else
  file=$(hdfs dfs -find project/statistics_output -iname "*{$1}*")
  hdfs dfs -cat "$file"
fi

