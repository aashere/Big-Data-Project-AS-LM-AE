1) Cleaning code can be found in the Code/Cleaning folder
2) Profiling code can be found in the Code/Profiling folder
3) Scripts can be found in the Scripts folder

Please note that I ran both these MR jobs on all 50 of my state data files individually using shell scripts. Before using any scripts, the command "chmod +x *.sh" must be run. The script structure is as follows:

states.sh runs code for each state
-| clean_setup.sh compiles the cleaning code
-| profile_setup.sh compiles the profiling code
-| go.sh runs the cleaning and profiling jobs
--| clean_run.sh runs the Clean MR job on all 50 state data files
--| profile_run.sh runs the CountRecs MR job on all 50 raw state data files, then all 50 		clean state data files, and then writes any differences between the record counts to a 	local file called bad_records.txt to help in identifying bad records

My HDFS directory structure looks like this:

/user/as12366/project
-| raw_data for my uncleaned data
-| cleaned_data for my cleaned data

Also, please note that the code and scripts must be in the same directory in order for the scripts to work properly.