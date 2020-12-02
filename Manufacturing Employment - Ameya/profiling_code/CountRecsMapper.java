import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.conf.*;

/*Mapper class will output the state as key and 1 as value
 */
public class CountRecsMapper 
		extends MapReduceBase 
		implements Mapper<LongWritable, Text, Text, Text>{
	
	//Retrieve state name from job configuration
	private static String state;
	public void configure(JobConf conf){
		state = conf.get("state");
	}
	
	private final static Text one = new Text(""+1);
	private Text output_key = new Text();
	
	public void map(LongWritable key, Text value, 
			OutputCollector<Text, Text> output, 
			Reporter reporter) 
		throws IOException{
		
		String input = value.toString();
		
		//Tokenize csv line by comma
		Scanner comma_scan = new Scanner(input);
		comma_scan.useDelimiter(",");
		//Grab first data item
		String first_item = comma_scan.next();
		comma_scan.close();

		//Skip header row
		if(first_item.equals("State") || first_item.equals("Series ID")){
			return;
		}

		//Output the state as key and 1 as value
		output_key.set(state);
		output.collect(output_key, one);
	}
}
