CODE DROP 1: AMEYA SHERE (as12366)
----------------------------------

Here are the parts of the code drop:
1) Cleaning and profiling code with scripts can be found in the "clean" folder
2) Joining code can be found in the "join" folder

Please note before using any scripts, the command "chmod +x *.sh" must be run in the directory where the scripts reside. 

Cleaning and Profiling:

states.sh runs code for each state (run with command "./states.sh")
|- clean_setup.sh compiles the cleaning code
|- profile_setup.sh compiles the profiling code
|- go.sh runs the cleaning and profiling jobs
|-- clean_run.sh runs the Clean MR job on all 50 state data files
|-- profile_run.sh runs the CountRecs MR job on all 50 raw state data files, then all 50 clean state data files, and then writes any differences between the record counts to a local file called bad_records.txt to help in identifying bad records

Joining:

join.sh creates a table called joined_with_headers in Impala that joins all the cleaned state csv files, then creates another table called joined that migrates all non-header records from joined_with_headers, then saves those records in ascending order by state and label in All_States.csv on HDFS (run with command "./join.sh")
|- commands.sql contains the Impala SQL commands to create the tables properly

My HDFS directory structure looks like this:
/user/as12366/project
|- raw_data for my uncleaned data
|- cleaned_data for my cleaned data
|- joined_data for my joined cleaned data, stored in All_States.csv

My DUMBO directory structure looks like this:
/home/as12366
|- pbdaa
|-- clean for cleaning and profiling code
|-- join for joining code

Thank you!
