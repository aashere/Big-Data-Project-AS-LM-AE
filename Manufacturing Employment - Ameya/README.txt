Ameya Shere (as12366): Manufacturing Data and Manufacturing vs. Covid part of analytic

A few notes:
1) In all directories with scripts, the command "chmod +x *.sh" must be run before scripts can be executed.
2) Since my "states.sh" script executes the cleaning and profiling jobs at the same time, code and scripts in directories "etl_code" and "profiling_code" must be combined into one directory in order for "states.sh" to work properly.
3) "states.sh" takes over an hour to execute and produce cleaned and profiled code. In order to avoid the existing cleaned and profiled data being deleted and an hour-long job being needed to get that data back, I have commented out the first couple lines of "states.sh". If you would like to fully recreate the execution of "states.sh", please uncomment those first couple lines; however, please be aware that the existing cleaned and profiled data will be deleted in that case.
4) "analytic.sh" begins with a line that brings Lucille's Covid data from her Hive database to my local directory. I have used an environment variable "$HIVE_PASSWORD" in this command in order to avoid writing my password directly in the script. As such, the first line will fail when the variable is not set. However, if Lucille's "monthlycasesoutput" table is retrieved in some other way and brought to the local directory as a CSV, the rest of the script will succeed.

Running the code:
Note: Input data can be found in "/user/as12366/project/raw_data".
1) Cleaning and profiling: These two MapReduce jobs clean the data, check for missing or bad data, and profile the data. This process is repeated for every individual state data file. The data this code runs on can be found in "/user/as12366/project/raw_data" on HDFS. The HDFS directory the result of this code can be found in is "/user/as12366/project/cleaned_data".
	a) Make sure the code inside "etl_code" and "profiling_code" is put into the same directory.
	b) Navigate to this directory.
	c) Run "./states.sh". Please heed the notes above regarding this script.
2) Joining: This Impala job takes all the individual state data files and joins them into one big table. The data this code runs on can be found in "/user/as12366/project/cleaned_data". The HDFS file the result of this code can be found in is "/user/as12366/joined_data/All_States.csv".
	a) Navigate to the directory containing "join.sh" and "join.sql".
	b) Run "./join.sh".
3) Normalizing: This Impala job takes the joined data and normalizes it for consistency across all 3 of our datasets, preparing it for use in the analytic phase. Specifically, the job changes state names to state codes and removes leading 0s in front of single digit months in the date labels. The job creates two tables for assistance based on CSV files I manually created. These CSV files are stored in HDFS as follows: the CSV file mapping state names to state codes is stored in "/user/as12366/project/state_codes", and the CSV file mapping old date labels to new, normalized date labels is stored in "/user/as12366/project/dates". The data this code runs on can be found in "/user/as12366/project/joined_data". The HDFS file the result of this code can be found in is "/user/as12366/project/normalized_data/Manufacturing.csv".
	a) Navigate to the directory containing "normalize.sh" and "normalize.sql".
	b) Run "./normalize.sh".
4) Analytic: This Impala job retrieves Lucille's "monthlycasesoutput" table from her Hive database and stores it in HDFS at location "/user/as12366/project/covid_data". The job then brings the "monthlycasesoutput" table into Impala, joins it with my normalized manufacturing data (keeping only rows from Jan. 2020 and later so that the date labels match up), and calculates the correlation coefficient for each state for Manufacturing Employment vs. Covid cases. This code runs on tables in Impala. The HDFS file the result of this code can be found in is "/user/as12366/project/final_analytic/Manufacturing_vs_Covid_FINAL.csv".
	a) Navigate to the directory containing "analytic.sh" and "analytic.sql".
	b) Run "./analytic.sh". Please heed the note above regarding this script.

Directory and File Tree (My DUMBO filesystem):
/home/as12366/project
|-- clean/
|   |-- Clean.java
|   |-- CleanMapper.java
|   |-- CleanReducer.java
|   |-- clean_setup.sh
|   |-- clean_run.sh
|   |-- CountRecs.java
|   |-- CountRecsMapper.java
|   |-- CountRecsReducer.java
|   |-- profile_setup.sh
|   |-- profile_run.sh
|   |-- go.sh
|   |-- states.sh
|-- join/
|   |-- join.sh
|   |-- join.sql
|-- normalize/
|   |-- normalize.sh
|   |-- normalize.sql
|-- analytic/
    |-- analytic.sh
    |-- analytic.sql

Directory and File Tree (My HDFS filesystem):
/user/as12366/project
|-- raw_data/
|   |-- **Contains 50 files, one for each state
|-- cleaned_data/
|   |-- **Contains 50 files, one for each state
|-- joined_data/
|   |-- All_States.csv
|-- state_codes/
|   |-- state_codes.csv
|-- dates/
|   |-- dates.csv
|-- normalized_data/
|   |-- Manufacturing.csv
|-- covid_data/
|   |-- monthlycasesoutput.csv
|-- final_analytic/
    |-- Manufacturing_vs_Covid_FINAL.csv

File Description Glossary (specific details can be found in code comments in each file):
analytic.sh - Runs the analytic by calculating correlation coefficients between Manufacturing Employment and Covid cases
analytic.sql - Queries to join tables and calculate correlation coefficients, used by analytic.sh
Clean.java - Cleaning job
CleanMapper.java - Mapper for cleaning job
CleanReducer.java - Reducer for cleaning job
clean_setup.sh - Compiles the cleaning code, used by states.sh
clean_run.sh - Runs the cleaning job on a state, used by go.sh
CountRecs.java - Profiling job
CountRecsMapper.java - Mapper for profiling job
CountRecsReducer.java - Reducer for profiling job
go.sh - Executes both the cleaning and the profiling jobs in one script, used by states.sh
join.sh - Joins all 50 individual state data files into one large file
join.sql - Queries to join state tables together, used by join.sh
normalize.sh - Makes state codes and date labels consistent for use in generating analytic
normalize.sql - Queries to normalize the data, used by normalize.sh
profile_setup.sh - Compiles the profiling code, used by states.sh
profile_run.sh - Runs the profiling job on a state's raw data file, then on its clean data file, and then writes any differences between the record counts to a local file called bad_records.txt to help in identifying bad records, used by go.sh
states.sh - Runs cleaning and profiling code for each state