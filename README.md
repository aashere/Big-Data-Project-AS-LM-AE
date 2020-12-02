Big Data Project Final Code Drop: Ameya Shere (as12366), Lucille Mure (lam923), and Andrew Epifano (ae1586)

Purpose of project: We wanted to determine if there was a correlation on a state-by-state level between manufacturing employment and covid cases, manufacturing employment and carbon emissions, and/or carbon emissions and covid cases.

Division of labor:
1) Ameya: Prepared manufacturing employment data. Ran analytic for correlation between manufacturing employment and covid cases.
2) Lucille: Prepared covid cases data. Ran analytic for correlation between carbon emissions and covid cases.
3) Andrew: Prepared carbon emissions data. Ran analytic for correlation between manufacturing employment and carbon emissions.

Note: Inside each of the required directories for this code drop are folders/files labeled with our names to indicate which person's portion of the project that file/folder corresponds to.

Main directory/file tree structure for this final code drop:
/
|-- data_ingest/
|   |-- AmeyaShere_data_ingestion.pdf
|   |-- LucilleMure_data_ingestion.pdf
|   |-- AndrewEpifano_data_ingestion/
|-- etl_code/
|   |-- ameyashere_etl_code/
|   |-- lucillemure_etl_code/
|   |-- andrewepifano_etl_code/
|-- profiling_code/
|   |-- ameyashere_profiling_code/
|   |-- lucillemure_profiling_code/
|   |-- andrewepifano_profiling_code/
|-- ana_code/
|   |-- ameyashere_ana_code/
|   |-- lucillemure_ana_code/
|   |-- andrewepifano_ana_code/
|-- screenshots/
|   |-- ameyashere_screenshots/
|   |-- lucillemure_screenshots/
|   |-- andrewepifano_screenshots/
|-- README.md

See below for information on running each of our individual analytics and for detailed directories for our individual folders.

------------------------------------------------------------------------------------------------------------
Ameya Shere (as12366): Manufacturing Employment Data and Manufacturing Employment vs. Covid analytic

A few notes:
1) In all directories with scripts, the command "chmod +x *.sh" must be run before scripts can be executed.
2) Since my "states.sh" script executes the cleaning and profiling jobs at the same time, code and scripts in directories "etl_code/ameyashere_etl_code" and "profiling_code/ameyashere_profiling_code" must be combined into one directory in order for "states.sh" to work properly.
3) "states.sh" takes over an hour to execute and produce cleaned and profiled code. In order to avoid the existing cleaned and profiled data being deleted and an hour-long job being needed to get that data back, I have commented out the first couple lines of "states.sh". If you would like to fully recreate the execution of "states.sh", please uncomment those first couple lines; however, please be aware that the existing cleaned and profiled data will be deleted in that case.
4) "analytic.sh" begins with a line that brings Lucille's Covid data from her Hive database to my local directory. I have used an environment variable "$HIVE_PASSWORD" in this command in order to avoid writing my password directly in the script. As such, the first line will fail when the variable is not set. However, if Lucille's "monthlycasesoutput" table is retrieved in some other way and brought to the local directory as a CSV, the rest of the script will succeed.

Running the code:
Note: Input data can be found in "/user/as12366/project/raw_data".
1) Cleaning and profiling: These two MapReduce jobs clean the data, check for missing or bad data, and profile the data. This process is repeated for every individual state data file. The data this code runs on can be found in "/user/as12366/project/raw_data" on HDFS. The HDFS directory the result of this code can be found in is "/user/as12366/project/cleaned_data".
	a) Make sure the code inside "etl_code/ameyashere_etl_code" and "profiling_code/ameyashere_profiling_code" is put into the same directory.
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

Directory and File Tree (my files and directories in this submission):
/
|-- data_ingest/
|   |-- AmeyaShere_data_ingestion.pdf
|-- etl_code/
|   |-- ameyashere_etl_code/
|       |-- Clean.java
|       |-- CleanMapper.java
|       |-- CleanReducer.java
|       |-- clean_setup.sh
|       |-- clean_run.sh
|       |-- go.sh
|       |-- states.sh
|-- profiling_code/
|   |-- ameyashere_profiling_code/
|       |-- CountRecs.java
|       |-- CountRecsMapper.java
|       |-- CountRecsReducer.java
|       |-- profile_setup.sh
|       |-- profile_run.sh
|-- ana_code/
|   |-- ameyashere_ana_code/
|       |-- join/
|       |   |-- join.sh
|       |   |-- join.sql
|       |-- normalize/
|       |   |-- normalize.sh
|       |   |-- normalize.sql
|       |-- analytic/
|           |-- analytic.sh
|           |-- analytic.sql
|-- screenshots/
    |-- ameyashere_screenshots/
        |-- screenshot_1.jpeg
        |-- screenshot_2.jpeg
        |-- screenshot_3.jpeg
        |-- screenshot_4-1.jpeg
        |-- screenshot_4-2.jpeg
        |-- screenshot_4-3.jpeg
        |-- screenshot_4-4.jpeg
        |-- screenshot_4-5.jpeg

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

------------------------------------------------------------------------------------------------------------
Lucille Mure (lam923): Covid Data and Carbon Emissions vs. Covid analytic

------------------------------------------------------------------------------------------------------------
Andrew Epifano (ae1586): Carbon Emissions data and Manufacturing vs. Carbon Emissions analytic

## Downloads:
### Original Datasource: https://oco2.gesdisc.eosdis.nasa.gov/data/OCO2_DATA/OCO2_L2_Lite_FP.10r/
The download scripts require that you setup an account with EarthData (https://urs.earthdata.nasa.gov/users/new) and setup system variables according to "curl for Mac/Linux" here (https://disc.gsfc.nasa.gov/data-access)

## Moving Data to HDFS:
1. The data is compressed into a tar file useing the "Compress Inputs" script.
2. SSH onto the dumbo server.
3. Use scp to download the tar file onto the dumbo server (using this method: https://www.urbaninsight.com/article/running-scp-through-ssh-tunnel).
4. Extract the data locally on the dumbo server (*tar -xf inputs.tar.gz*)
5. Use the hdfs put command to put the 2019 and 2020 input folders to the correct location on hdfs (*/project/input/2019, /project/input/2020*).

## General:
You may need to change the line ending mode to LF is it is set to CRLF before running these files.

# Running Mapreduce Jobs
* All the map reduce jobs should be paired with a compile.sh script that must be run in the same directory as the .java files themselves.
* To run the job, just run "hadoop jar [jarName].jar [MainClassName] [inputDir] [outputDir]

# Running Impala Queries
* You can find sql files that contain the queries I ran.

#File Structure
## Dumbo structure

project/  
├─ ClassifyStates/  
│  ├─ ClassifyStats.jar  
│  ├─ compile.sh  
│  ├─ GeographicPosition.java  
│  ├─ listOutputs.sh  
│  ├─ Region.java  
│  ├─ run.sh  
│  ├─ setup.sh  
│  ├─ stateBoundaries.txt  
│  ├─ StateClassification.java  
│  ├─ StateMapper.java  
│  ├─ StateReducer.java  
├─ Clean/  
│  ├─ Clean.jar  
│  ├─ Clean.java  
│  ├─ CleanMapper.java  
│  ├─ CleanReducer.java  
├─ JoinedData/  
│  ├─ all_states_emissions.csv  
├─ Profile/  
│  ├─ CountRecs.java  
│  ├─ CountRecs.jar  
│  ├─ CountRecsMapper.java  
│  ├─ CountRecsReducer.java  
├─ Statistics/  
│  ├─ calculateStats.jar  
│  ├─ compile.sh  
│  ├─ FloatArrayWritable.java  
│  ├─ listOutputs.sh  
│  ├─ run.sh  
│  ├─ StateNames.java  
│  ├─ StateStats.java  
│  ├─ Stat.java  
│  ├─ StatRange.java  
│  ├─ StatsMapper.java  
│  ├─ StatsReducer.java  
├─ temp/  
│  ├─ 2019/  
│  │  ├─ ...csv  
│  ├─ 2020/  
│  │  ├─ ...csv  

## hdfs structure
project/  
├─ cleaned_output/  
│  ├─ Output of cleaning job   
├─ config/  
│  ├─ Contains stateBoundaries information to be shared among all mappers during state classification job     
├─ input/   
│  ├─ Original inputs for 2020 and 2019   
├─ joined_states/  
│  ├─ All states outputs from statistics job in one file      
├─ record-counts/    
│  ├─ Count job output     
├─ state_output/  
│  ├─ State classification job output  
├─ statistics_output/  
│  ├─ Output for statistics job   
├─ joined_manufacturing/  
│  ├─ joined manufacturing data from Ameya's impala table

## State Classification
* States are classified using a ray-casting approach from longitude and latitude values (https://en.wikipedia.org/wiki/Point_in_polygon)
* I've used the polygons generated here: https://github.com/LyleScott/google-maps-us-states-polygons/blob/master/coords.js converted into a text file
* The text file is loaded into the job cache and used by all the mappers
* Any data entry that does not belong to a US state is tallied under the key "OUTSIDE-US"
#### Some scripts/commands
* List the outputs - *./listOutputs.sh*
* Print output for california - "*./listOutputs.sh california*"
* Print output for california for 2020 - "*./listOutputs.sh california | grep "2020*"


## State Statistics
* I generate the following datapoints for each state:
  * Average CO2 PPM
  * Median CO2 PPM
  * Min CO2 PPM
  * Max CO2 PPM
  * Total Records
* For each of the following ranges of time: All time (2019-2020), Yearly, Monthly
#### Some scripts/commands
* List the outputs - *./listOutputs.sh*
* Print output for new york - "*./listOutputs.sh "new york"*"

# Data workflow
1. Download semi-processed data from NASA
2. Extract data into a text format
3. Compress and move to HDFS
4. Clean and Profile the data which outputs a cleaned data set
5. Run the State Classification job on the cleaned data set
6. Run the Statistics job on the state-classified dataset
7. Put the dataset table using impala.
8. Compute correlation between each states monthly data and manufacturing data using query operations.
