import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;

//This MR job cleans the input file, checking for bad or missing data
public class Clean{
	public static void main(String[] args) throws Exception{
		//Set job configuration settings
		JobConf conf = new JobConf(Clean.class);
		conf.setJobName("clean");
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		conf.setMapperClass(CleanMapper.class);
		conf.setReducerClass(CleanReducer.class);
		conf.setNumReduceTasks(1);

		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		//Pass in state name from command line as third argument to MR job
		conf.set("state", args[2]);
		//Use a comma to separate key and value so that output can be a csv
		conf.set("mapreduce.output.textoutputformat.separator", ",");

		JobClient.runJob(conf);
	}

}

