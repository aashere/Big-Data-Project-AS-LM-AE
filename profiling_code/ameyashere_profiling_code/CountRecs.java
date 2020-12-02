import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;

//This MR job counts the number of records in the input file
public class CountRecs{

	public static void main(String[] args) throws Exception{
		//Set job configuration settings
		JobConf conf = new JobConf(CountRecs.class);
		conf.setJobName("countrecs");
		
		conf.setOutputKeyClass(Text.class);
		conf.setOutputValueClass(Text.class);

		conf.setMapperClass(CountRecsMapper.class);
		conf.setReducerClass(CountRecsReducer.class);
		conf.setNumReduceTasks(1);

		conf.setInputFormat(TextInputFormat.class);
		conf.setOutputFormat(TextOutputFormat.class);

		FileInputFormat.setInputPaths(conf, new Path(args[0]));
		FileOutputFormat.setOutputPath(conf, new Path(args[1]));

		//Pass in state name from command line as third argument to MR job
		conf.set("state", args[2]);

		JobClient.runJob(conf);
	}

}

