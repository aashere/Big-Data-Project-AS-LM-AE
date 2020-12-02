# Put boundary files in correct location
hdfs dfs -put -f stateBoundaries.txt project/config/stateBoundaries.txt

# Compile jar
./compile.sh

