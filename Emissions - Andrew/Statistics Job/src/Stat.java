import org.apache.hadoop.io.FloatWritable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Stat {

    StatRange range;
    String id; //2020, 2019, February etc depending on range

    List<Float> values;

    double totalCO2 = 0;
    double avgCO2 = 0; //Calculate at the end
    float medianCO2 = 0; //Calculate at the end
    float minCO2 = Float.MAX_VALUE;
    float maxCO2 = 0;
    long totalRecords = 0;

    boolean hasBeenModified = false;

    public Stat(StatRange range, String id){
        this.range = range;
        this.id = id;
        this.values = new ArrayList<>();
    }

    public void addData(String year, String month, float co2){
        switch(this.range){
            case ALL_TIME:
                break;
            case YEARLY:
                if(!this.id.equalsIgnoreCase(year + "-ALL"))return;
                break;
            case MONTHLY:
                if(!this.id.equalsIgnoreCase(year + "-" + month))return;
                break;
        }

        this.hasBeenModified = true;
        if(co2 < minCO2){
            minCO2 = co2;
        }
        if(co2 > maxCO2){
            maxCO2 = co2;
        }

        this.values.add(co2);
        this.totalCO2 += co2;
        this.totalRecords++;
    }


    public void calculateMedian(){
        //Sort arraylist
        Collections.sort(values);
        //find middle
        float middle = values.size() % 2 == 0 ? values.get((values.size() + 1)/2) : values.get(values.size()/2) + values.get((values.size() + 1)/2);
        this.medianCO2 = middle;
    }

    public String toString(){ //This should only be called once per time frame
        this.avgCO2 = (this.totalCO2/this.totalRecords);
        this.calculateMedian();
        return this.avgCO2 + "," + this.medianCO2 + "," + this.minCO2 + "," + this.maxCO2 + "," + totalRecords;
    }
}
