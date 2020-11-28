# Remove class and jar files
rm CountRecs.class CountRecsMapper.class CountRecsReducer.class
rm countrecs.jar

# Compile
javac -classpath `yarn classpath` -d . CountRecsMapper.java
javac -classpath `yarn classpath` -d . CountRecsReducer.java
javac -classpath `yarn classpath`:. -d . CountRecs.java

# Create jar file
jar -cvf countrecs.jar *.class
