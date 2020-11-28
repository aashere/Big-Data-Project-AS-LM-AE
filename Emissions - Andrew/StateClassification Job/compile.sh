rm classifyStates.jar

javac -classpath `yarn classpath` -d . GeographicPosition.java Region.java StateMapper.java StateReducer.java
javac -classpath `yarn classpath`:. -d . StateClassification.java

jar -cvf classifyStates.jar *.class

rm StateClassification.class StateMapper.class StateReducer.class GeographicPosition.class Region.class
