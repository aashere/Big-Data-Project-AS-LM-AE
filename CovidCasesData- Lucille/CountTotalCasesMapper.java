import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class CountTotalCasesMapper
 extends Mapper<LongWritable, Text, Text, IntWritable> {

@Override
public void map(LongWritable key, Text value, Context context)
throws IOException, InterruptedException {
int i;

String curr_line = value.toString();
String[] lines = curr_line.split(",");
//check that month is up to september
if (lines[0].substring(1,2).equals("/")){
	context.write(new Text(lines[1]+","), new IntWritable(Integer.parseInt(lines[3].trim())));


}
}}
