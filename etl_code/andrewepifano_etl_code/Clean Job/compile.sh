rm Clean.jar

javac -classpath `yarn classpath` -d . CleanMapper.java CleanReducer.java
javac -classpath `yarn classpath`:. -d . Clean.java

jar -cvf Clean.jar *.class

rm Clean.class CleanMapper.class StatsReducer.class
