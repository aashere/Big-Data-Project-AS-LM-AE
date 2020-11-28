rm calculateStats.jar

javac -classpath `yarn classpath` -d . Stat.java StatRange.java StatsMapper.java StatsReducer.java FloatArrayWritable.java
javac -classpath `yarn classpath`:. -d . StateStats.java

jar -cvf calculateStats.jar *.class

rm Stat.class StatRange.class StatsMapper.class StatsReducer.class StateStats.class FloatArrayWritable.class
