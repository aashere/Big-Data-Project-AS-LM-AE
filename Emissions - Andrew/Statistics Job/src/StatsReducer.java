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

    private StateNames names;

    @Override
    public void setup(Context context){
        mos = new MultipleOutputs(context);
        names = new StateNames();
        stats = new ArrayList<>();

        //Generate stat object for each month 2020-2019
        //Using seperate loops so it's in order in output
        stats.add(new Stat(StatRange.ALL_TIME, "ALL"));
        stats.add(new Stat(StatRange.YEARLY, "2019"));
        for(int i = 1; i <= 12; i++){
            stats.add(new Stat(StatRange.MONTHLY, "2019-" + i));
        }

        stats.add(new Stat(StatRange.YEARLY, "2020"));
        for(int i = 1; i <= 12; i++){
            stats.add(new Stat(StatRange.MONTHLY, "2020-" + i));
        }

    }

    @Override
    public void reduce(Text key, Iterable<FloatArrayWritable> values, Context context) throws IOException, InterruptedException {
        for(FloatArrayWritable arr : values){
            FloatWritable[] vals = arr.get();
            float co2 = vals[0].get();
            int year = (int)vals[1].get();
            int month = (int)vals[2].get();
            for(Stat stat : this.stats){ //Only 27 different ones here
                stat.addData("" + year, month, co2);
            }
        }

        for(Stat stat : this.stats){
            if(stat.hasBeenModified){
                //Write to the output (key=stat.id, value=stats, filename=state)
                mos.write((Text)null, new Text(stat.toString(names.getAbbr(key.toString()))), key.toString());
            }
        }
    }

}
