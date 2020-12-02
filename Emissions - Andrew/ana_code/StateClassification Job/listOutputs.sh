if [ $# -eq 0 ]
then
  hdfs dfs -ls project/state_output | grep -v "part"
else
  file=$(hdfs dfs -find project/state_output -iname "*{$1}*")
  hdfs dfs -cat "$file"
fi

