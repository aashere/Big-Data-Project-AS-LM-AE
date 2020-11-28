# Remove class and jar files
rm Clean.class CleanMapper.class CleanReducer.class
rm clean.jar

# Compile
javac -classpath `yarn classpath` -d . CleanMapper.java
javac -classpath `yarn classpath` -d . CleanReducer.java
javac -classpath `yarn classpath`:. -d . Clean.java

# Create jar file
jar -cvf clean.jar *.class
