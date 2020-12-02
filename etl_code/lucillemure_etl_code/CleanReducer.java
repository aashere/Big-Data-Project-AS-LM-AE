//In this class, we learned to begin to write MapReduce code from the MaxTemperature example in textbook 
//"Hadoop: The Definitive Guide", 4th edition, by Tom White. 
//Since this was how we learned to write import statements, loop structure and method signature for MapR code,
//these parts of my code are based off of how I learned to write them from the textbook

//import statements
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
//in this constructor, we can see that the key in is a Text value, the value in is an Text,
//the key out is a Text value, and the value out is an Text
public class CleanReducer
 extends Reducer<Text, Text, Text, Text> {

 //in this method signature we can see that the key in is a Text value and the 
 //value in is an Text
 @Override
 public void reduce(Text key, Iterable<Text> values, Context context)
 throws IOException, InterruptedException {

 //we go through the keys and values from the mapper, add them to a new line, and write them back to the outback. 
 //in this case, each value will have a unique key, thus,we are writing each row that we want to keep back indivually to the 
 //output
 String new_line="";
 for (Text value : values) {
    new_line+= value;
 }
 context.write(key, new Text(new_line));
 }
}
