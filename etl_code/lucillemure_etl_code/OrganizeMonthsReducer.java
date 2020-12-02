//In this class, we learned to begin to write MapReduce code from the MaxTemperature example in textbook 
//"Hadoop: The Definitive Guide", 4th edition, by Tom White. 
//Since this was how we learned to write import statements, loop structure and method signature for MapR code,
//these parts of my code are based off of how I learned to write them from the textbook

//import statements
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
//in this constructor, we can see that the key in is a Text value, the value in is an IntWritable,
//the key out is a Text value, and the value out is an Text
public class OrganizeMonthsReducer
 extends Reducer<Text, IntWritable, Text, Text> {

 //in this method signature we can see that the key in is a Text value and the 
 //value in is an Text
 @Override
 public void reduce(Text key,  Iterable<IntWritable> values, Context context)
 throws IOException, InterruptedException {

 //here, we sum the number of new cases during a month for a certain state, giving us the total number of cases that month for a state
 int count=0;
 for (IntWritable value : values) {
    count+= value.get();
 }
 //we write back the month, the state, as well as the total number of cases for the state
 //since there was an error in hive where hive was unable to remove the whitespace between the key and the value with trim()
 //we have a filler column between the key and the value
 //this ensures that we will be able to convert the monthly cases to an int in hive
 context.write(key, new Text("Filler" + "," + Integer.toString(count)));
 }
}
