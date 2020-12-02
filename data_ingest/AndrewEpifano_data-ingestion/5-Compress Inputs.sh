#!/bin/bash
echo "Compressing files..."

_2019='../Data/2019/output_raw/*'
_2020='../Data/2020/output_raw/*'

#make temp dirs
mkdir -p temp/2019/input
mkdir -p temp/2020/input

#Move 2019 data into temp location
echo "Moving 2019 files to temp location..."
totalFiles=$(find $_2019 -maxdepth 0 | wc -l)
filesCounted=0
echo -ne "0 / $totalFiles files moved\r"
for file in $_2019
do
  csvFile=${file}/formatted.csv #Local location file
  newCSVName=./temp/2019/$(basename $file).csv #temp location
  cp $csvFile $newCSVName
  filesCounted=$((filesCounted+1))
  echo -ne "$filesCounted / $totalFiles files moved\r"
done
echo -ne "\n"

#Move 2020 data into temp location
echo "Moving 2020 files to temp location..."
totalFiles=$(find $_2020 -maxdepth 0 | wc -l)
filesCounted=0
echo -ne "0 / $totalFiles files moved\r"
for file in $_2020
do
  csvFile=${file}/formatted.csv #Local location file
  newCSVName=./temp/2020/$(basename $file).csv #temp location
  cp $csvFile $newCSVName
  filesCounted=$((filesCounted+1))
  echo -ne "$filesCounted / $totalFiles files moved\r"
done
echo -ne "\n"

echo "Tar-ing contents..."
#One liner to keep track of progress courtesy of : https://superuser.com/questions/168749/is-there-a-way-to-see-any-tar-progress-per-file
#you need pv installed
tar cf - ./temp -P | pv -s $(du -sb ./temp | awk '{print $1}') | gzip > inputs.tar.gz
echo "Deleting Temp Directory..."
rm -r ./temp

echo "Done"
