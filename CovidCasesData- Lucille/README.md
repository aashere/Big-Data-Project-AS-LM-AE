In this folder, there is code for cleaning the data. Schema.csv is the result of running this code.
There is also code for counting the total cases in the US (up to September 2020). The output
of this code is called TotalCases.csv. HDFS commands were used to compile and run this code.
For cleaning, the commands to compile were:
javac -classpath "$(yarn classpath)" -d . CleanMapper.java
javac -classpath "$(yarn classpath)" -d . CleanReducer.java
javac -classpath "$(yarn classpath)":. -d . Clean.java
jar -cvf Clean.jar *.class 
And the command to run the code was:
hadoop jar Clean.jar Clean /user/lam923/hw9/covid_data.csv /user/lam923/hw9/output 
For counting the cases, the commands to compile were:
avac -classpath "$(yarn classpath)" -d . CountTotalCasesMapper.java
javac -classpath "$(yarn classpath)" -d . CountTotalCasesReducer.java
javac -classpath "$(yarn classpath)":. -d . CountTotalCases.java
jar -cvf CountTotalCases.jar *.class 
And the command to run the code was:
hadoop jar CountTotalCases.jar CountTotalCases /user/lam923/CountCovidCases/schema.csv /user/lam923/CountCovidCases/output7