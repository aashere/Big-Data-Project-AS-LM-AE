#!/bin/bash
_testDir='../Data/test/*.nc4'
_2019='../Data/2019/*.nc4'
_2020='../Data/2020/*.nc4'

#Start 2019
echo "Extracting from 2019 datasets..."
totalFiles=$(find $_2019 -maxdepth 2 -type f | wc -l)
filesCounted=0

echo -ne "0 / $totalFiles files processed\r"
for file in $_2019
do
  _outputFilePrefix=$(dirname $file)/"output_raw"/$(basename $file .nc4)
  mkdir -p $_outputFilePrefix #make output directory
  h5dump -d xco2      -o "${_outputFilePrefix}/output_co2.txt" $file > /dev/null 2>&1
  h5dump -d longitude -o "${_outputFilePrefix}/output_long.txt" $file > /dev/null 2>&1
  h5dump -d latitude  -o "${_outputFilePrefix}/output_lat.txt" $file > /dev/null 2>&1
  h5dump -d date      -o "${_outputFilePrefix}/output_date.txt" $file > /dev/null 2>&1
  filesCounted=$((filesCounted+1))
  echo -ne "$filesCounted / $totalFiles files processed\r"
done
echo -ne "\n"

#Start 2020
echo "Extracting from 2020 datasets..."
totalFiles=$(find $_2020 -maxdepth 2 -type f | wc -l)
filesCounted=0

echo -ne "0 / $totalFiles files processed\r"
for file in $_2020
do
  _outputFilePrefix=$(dirname $file)/"output_raw"/$(basename $file .nc4)
  mkdir -p $_outputFilePrefix #make output directory
  h5dump -d xco2      -o "${_outputFilePrefix}/output_co2.txt" $file > /dev/null 2>&1
  h5dump -d longitude -o "${_outputFilePrefix}/output_long.txt" $file > /dev/null 2>&1
  h5dump -d latitude  -o "${_outputFilePrefix}/output_lat.txt" $file > /dev/null 2>&1
  h5dump -d date      -o "${_outputFilePrefix}/output_date.txt" $file > /dev/null 2>&1
  filesCounted=$((filesCounted+1))
  echo -ne "$filesCounted / $totalFiles files processed\r"
done
echo -ne "\n"

#Pause to see result
/bin/bash
