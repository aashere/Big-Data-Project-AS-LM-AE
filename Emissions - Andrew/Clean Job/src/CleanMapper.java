import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.Arrays;

public class CleanMapper extends Mapper<LongWritable, Text, LongWritable, Text> {

    /*
        Take in csv's. Get indvidual values. Remove hour/minute. Write with the same key to the context
     */

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        String[] columns = line.split(","); //we don't need any special regex because there are no text fields which would contain a comma
        String[] cleaned = Arrays.copyOfRange(columns, 0, 6); //Remove last two columns (hour, minute)

        context.write(null, new Text(String.join(",", cleaned)));

    }
}
