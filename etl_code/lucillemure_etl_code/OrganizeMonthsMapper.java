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
//the key out is a Text value, and the value out is an IntWritable
public class OrganizeMonthsMapper
 extends Mapper<LongWritable, Text, Text, IntWritable> {

@Override
//in this method signature we can see that the key in from the input is a LongWritable value and the 
//value in is an Text values
public void map(LongWritable key, Text value, Context context)
throws IOException, InterruptedException {
int i;
//we store each row as curr_line and split that line into a list called line
String curr_line = value.toString();
String[] lines = curr_line.split(",");
//ensure that the months only go up to Septmeber (we only want to analyze the data from January to September)
if (lines[0].substring(1,2).equals("/")){
    //assign a number to each month so that keys will be ordered, line[3] of the schema is the new cases that month, so adding all the new cases for a month per state will 
    //give us the total number of cases in that month
    context.write(new Text (Integer.toString((Integer.parseInt(lines[0].substring(0,1)))) +"," +lines[1] + ","), new IntWritable(Integer.parseInt(lines[3].trim())) ); }

}}
