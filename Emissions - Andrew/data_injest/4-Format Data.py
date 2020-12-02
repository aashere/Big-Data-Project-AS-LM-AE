import re
import os
import csv

rawData2019 = os.path.abspath("../Data/2019/output_raw")
rawData2020 = os.path.abspath("../Data/2020/output_raw")

indexRegex = "\(.*\):"
spaceRegex = "\s+"


def loopDirs(rootDir):
    list = os.listdir(rootDir)
    totalDirs = len(list)
    printProgress(0, totalDirs)
    for i, file in enumerate(list):
        formatDir(os.path.join(rootDir, file))
        printProgress(i, totalDirs)

def formatDir(dir):
    co2 = getArrayFromFile(os.path.join(dir, "output_co2.txt"))
    lat = getArrayFromFile(os.path.join(dir, "output_lat.txt"))
    long = getArrayFromFile(os.path.join(dir, "output_long.txt"))
    date = getArrayFromFile(os.path.join(dir, "output_date.txt")) #should be 7x as long as other datasets
    #separate date info
    year = date[::7]
    month = date[1::7]
    day = date[2::7]
    hour = date[3::7]
    minute = date[4::7] #We don't need to save the seconds/milliseconds
    saveCSV(os.path.join(dir, "formatted.csv"), zip(co2, lat, long, year, month, day, hour, minute))

def saveCSV(path, zippedData):
    with open(path, 'w', newline='') as f:
        csvWriter = csv.writer(f)
        csvWriter.writerow(["CO2 PPM", "Latitude", "Longitude", "Year", "Month", "Day", "Hour", "Minute"])
        csvWriter.writerows(zippedData)

def getArrayFromFile(fileName):
    file = open(fileName, mode='r')
    data = file.read()
    data = re.sub(indexRegex, "", data)
    data = re.sub(spaceRegex, "", data)
    file.close()
    return data.split(",")

def printProgress(iter, totalIterations):
    percent = "%.1f" % (100*(float(iter)/totalIterations)) #get iter as percentage of totalIterations formatted to 1 decimal place
    print(f'\r{iter} out of {totalIterations} | {percent}%', end = "\r")
    if iter == totalIterations:
        print()

print("Formatting 2019 data...")
loopDirs(rawData2019)
print("Formatting 2020 data...")
loopDirs(rawData2020)
