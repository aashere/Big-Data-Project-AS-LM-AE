//In this class, we learned to begin to write MapReduce code from the MaxTemperature example in textbook 
//"Hadoop: The Definitive Guide", 4th edition, by Tom White. 
//Since this was how we learned to write import statements, loop structure and method signature for MapR code,
//these parts of my code are based off of how I learned to write them from the textbook

//import statements
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

//in this constructor, we can see that the key in is a Text value, the value in is an Intwritable,
//the key out is a Text value, and the value out is an Intwritable
public class CountRecsReducer
 extends Reducer<Text, IntWritable, Text, IntWritable> {

 //in this method signature we can see that the key in is a Text value (in this case it is "count" since we want to sum every entry) and the 
 //value in is an intwritable
 @Override
 public void reduce(Text key, Iterable<IntWritable> values, Context context)
 throws IOException, InterruptedException {

 //we set count to 0, and then increment by 1 for every new entry. Since the values all have the same key "count"
 //we will increment our count by 1 for each row in the table
 int count=0;
 for (IntWritable value : values) {
    count+= value.get();
 }
 //we will write to the output "count" followed by the total amount of rows
 context.write(key, new IntWritable(count));
 }
}
