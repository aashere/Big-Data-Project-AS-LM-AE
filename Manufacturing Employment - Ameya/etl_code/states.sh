#remove cleaned data directory
#NOTE: I have commented these lines out for now, as they will delete the existing
#cleaned data, and this job will take over an hour to execute and produce new cleaned data.
#In order to fully re-execute this script, the following lines must be uncommented.

#hdfs dfs -rm -r /user/as12366/project/cleaned_data
#Create cleaned data directory
#hdfs dfs -mkdir /user/as12366/project/cleaned_data

#Remove and recreate bad records file
rm bad_records.txt
touch bad_records.txt

#Compile code
./clean_setup.sh
./profile_setup.sh

#Run code for each state
export STATE=Alabama
./go.sh
export STATE=Alaska
./go.sh
export STATE=Arizona
./go.sh
export STATE=Arkansas
./go.sh
export STATE=California
./go.sh
export STATE=Colorado
./go.sh
export STATE=Connecticut
./go.sh
export STATE=Delaware
./go.sh
export STATE=Florida
./go.sh
export STATE=Georgia
./go.sh
export STATE=Hawaii
./go.sh
export STATE=Idaho
./go.sh
export STATE=Illinois
./go.sh
export STATE=Indiana
./go.sh
export STATE=Iowa
./go.sh
export STATE=Kansas
./go.sh
export STATE=Kentucky
./go.sh
export STATE=Louisiana
./go.sh
export STATE=Maine
./go.sh
export STATE=Maryland
./go.sh
export STATE=Massachusetts
./go.sh
export STATE=Michigan
./go.sh
export STATE=Minnesota
./go.sh
export STATE=Mississippi
./go.sh
export STATE=Missouri
./go.sh
export STATE=Montana
./go.sh
export STATE=Nebraska
./go.sh
export STATE=Nevada
./go.sh
export STATE=New_Hampshire
./go.sh
export STATE=New_Jersey
./go.sh
export STATE=New_Mexico
./go.sh
export STATE=New_York
./go.sh
export STATE=North_Carolina
./go.sh
export STATE=North_Dakota
./go.sh
export STATE=Ohio
./go.sh
export STATE=Oklahoma
./go.sh
export STATE=Oregon
./go.sh
export STATE=Pennsylvania
./go.sh
export STATE=Rhode_Island
./go.sh
export STATE=South_Carolina
./go.sh
export STATE=South_Dakota
./go.sh
export STATE=Tennessee
./go.sh
export STATE=Texas
./go.sh
export STATE=Utah
./go.sh
export STATE=Vermont
./go.sh
export STATE=Virginia
./go.sh
export STATE=Washington
./go.sh
export STATE=West_Virginia
./go.sh
export STATE=Wisconsin
./go.sh
export STATE=Wyoming
./go.sh
