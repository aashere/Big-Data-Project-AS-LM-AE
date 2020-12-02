//In this class, we learned to begin to write MapReduce code from the MaxTemperature example in textbook 
//"Hadoop: The Definitive Guide", 4th edition, by Tom White. 
//Since this was how we learned to write import statements, loop structure and method signature for MapR code,
//these parts of my code are based off of how I learned to write them from the textbook

//import statements
import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

//in this constructor, we can see that the key in from the input is a LongWritable value, the value in from the input is a Text Value,
//the key out is a Text value, and the value out is an Intwritable
public class CountRecsMapper
 extends Mapper<LongWritable, Text, Text, IntWritable> {


 //in this method signature we can see that the key in from the input is a LongWritable value and the 
 //value in is an Text value
@Override
public void map(LongWritable key, Text value, Context context)
throws IOException, InterruptedException {
int i;

//here, for each row of the column we write a key value pair of ("count",1) to the reducer
//this lets the reducer sum all the values
context.write(new Text("count"), new IntWritable(1));



}}
