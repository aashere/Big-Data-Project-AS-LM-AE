import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class StatsReducer extends Reducer<Text, FloatArrayWritable, Text, Text> {

    //For each state we want to generate the following: average, max, min, median, totalRecords
    //For the following date ranges: All time, 2019, 2020, each month

    private MultipleOutputs mos;
    List<Stat> stats;

    @Override
    public void setup(Context context){
        mos = new MultipleOutputs(context);
        stats = new ArrayList<>();

        //Generate stat object for each month 2020-2019
        //Using seperate loops so it's in order in output
        stats.add(new Stat(StatRange.ALL_TIME, "ALL-TIME"));
        stats.add(new Stat(StatRange.YEARLY, "2019-ALL"));
        for(int i = 1; i <= 12; i++){
            String monthName = nameOfMonth(i);
            stats.add(new Stat(StatRange.MONTHLY, "2019-" + monthName));
        }

        stats.add(new Stat(StatRange.YEARLY, "2020-ALL"));
        for(int i = 1; i <= 12; i++){
            String monthName = nameOfMonth(i);
            stats.add(new Stat(StatRange.MONTHLY, "2020-" + monthName));
        }

    }

    @Override
    public void reduce(Text key, Iterable<FloatArrayWritable> values, Context context) throws IOException, InterruptedException {
        for(FloatArrayWritable arr : values){
            FloatWritable[] vals = arr.get();
            float co2 = vals[0].get();
            int year = (int)vals[1].get();
            int month = (int)vals[2].get();
            String monthName = nameOfMonth(month);
            for(Stat stat : this.stats){ //Only 27 different ones here
                stat.addData("" + year, monthName, co2);
            }
        }

        for(Stat stat : this.stats){
            if(stat.hasBeenModified){
                //Write to the output (key=stat.id, value=stats, filename=state)
                mos.write(new Text(stat.id), new Text(stat.toString()), key.toString());
            }
        }
    }

    public String nameOfMonth(int month){
        return Month.of(month).name();
    }
}
