import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

public class CountRecsMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        context.write(new Text("totalRecords"), new LongWritable(1));

    }
}
