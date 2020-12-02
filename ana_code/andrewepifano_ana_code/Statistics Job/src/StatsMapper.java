import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class StatsMapper extends Mapper<LongWritable, Text, Text, FloatArrayWritable> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        //Just write to the state as key, the values as number array
        String[] columns = value.toString().split(",");
        if(columns.length != 5){return;} //Ignore the OUTSIDE-US file

        FloatWritable[] values = new FloatWritable[4];
        values[0] = new FloatWritable(Float.parseFloat(columns[0])); //CO2 ppm
        values[1] = new FloatWritable(Float.parseFloat(columns[1])); //Year (set to float because ArrayWritable must all be same type)
        values[2] = new FloatWritable(Float.parseFloat(columns[2])); //Month
        values[3] = new FloatWritable(Float.parseFloat(columns[3])); //Day
        String state = columns[4];

        FloatArrayWritable arr = new FloatArrayWritable(values);
        context.write(new Text(state), arr);
    }
}
