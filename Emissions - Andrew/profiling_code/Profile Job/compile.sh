rm CountRecs.jar

javac -classpath `yarn classpath` -d . CountRecsMapper.java CountRecsReducer.java
javac -classpath `yarn classpath`:. -d . CountRecs.java

jar -cvf CountRecs.jar *.class

rm CountRecs.class CountRecsMapper.class CountRecsReducer.class
