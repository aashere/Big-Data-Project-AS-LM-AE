//In this class, we learned to begin to write MapReduce code from the MaxTemperature example in textbook 
//"Hadoop: The Definitive Guide", 4th edition, by Tom White. 
//Since this was how we learned to write import statements, loop structure and method signature, and exiting for MapR code,
//these parts of my code are based off of how I learned to write them from the textbook

//import statements for MapR
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
public class CountRecs{
 public static void main(String[] args) throws Exception {
 //we create a new job and set the class and the name of the job
 Job job = new Job();
 job.setJarByClass(CountRecs.class);
 job.setJobName("Count Recs");
 //here we set the input path (the file the mapper will get the data from) and the output path (where the reducer will write the output)
 //we take the input and output path from what the user specifies in their arguements when they run the code
 FileInputFormat.addInputPath(job, new Path(args[0]));
 FileOutputFormat.setOutputPath(job, new Path(args[1]));
 
 //we set the mapper and reducer class
 job.setMapperClass(CountRecsMapper.class);
 job.setReducerClass(CountRecsReducer.class);
 //we set the output key as a text, and the output value as an IntWritable
 job.setOutputKeyClass(Text.class);
 job.setOutputValueClass(IntWritable.class);
 job.setNumReduceTasks(1);
 System.exit(job.waitForCompletion(true) ? 0 : 1);
 }}
