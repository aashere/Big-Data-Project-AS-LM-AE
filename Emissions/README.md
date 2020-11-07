## Downloads:
### Original Datasource: https://oco2.gesdisc.eosdis.nasa.gov/data/OCO2_DATA/OCO2_L2_Lite_FP.10r/
The download scripts require that you setup an account with EarthData (https://urs.earthdata.nasa.gov/users/new) and setup system variables according to "curl for Mac/Linux" here (https://disc.gsfc.nasa.gov/data-access)

## Moving Data to HDFS:
The data is compressed into a tar file useing the "Compress Inputs" script. Then ssh onto the dumbo server. Use scp to download the tar file onto the dumbo server (using this method: https://www.urbaninsight.com/article/running-scp-through-ssh-tunnel). Then extract the data locally on the dumbo server (*tar -xf inputs.tar.gz*) and use the hdfs put command to put the 2019 and 2020 input folders to the correct location on hdfs (*/project/input/2019, /project/input/2020*).

## General:
You may need to change the line ending mode to LF is it is set to CRLF before running these files.
