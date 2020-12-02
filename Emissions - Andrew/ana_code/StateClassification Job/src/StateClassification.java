import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;

public class StateClassification {

    /*

        The goal of this map reduce is to take every entry, classify it's state and output it with its state
        instead of long/lat.

        Ideally there will be an output file for each state. Then we can run another job on each state file
        for min/max/avg/median
     */


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
        if(args.length != 2){
            System.out.println("Usage: StateClassification <input path> <output path>");
            System.exit(-1);
        }

        Job job = new Job();
        job.setJarByClass(StateClassification.class);
        job.setJobName("State Classifications");

        FileInputFormat.setInputDirRecursive(job, true);
        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));

        job.setMapperClass(StateMapper.class);
        job.setReducerClass(StateReducer.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        //add a named output so we can write state names for each key
        MultipleOutputs.addNamedOutput(job, "text", TextOutputFormat.class, Text.class, Text.class);

        job.addCacheFile(new URI("hdfs://dumbo/user/ae1586/project/config/stateBoundaries.txt"));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }




}
