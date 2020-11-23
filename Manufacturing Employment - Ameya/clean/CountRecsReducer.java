import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.conf.*;

/* Reducer class will sum up the number of records for this state
 */
public class CountRecsReducer
			extends MapReduceBase
			implements Reducer<Text, Text, Text, Text>{
	
	//Retrieve state name from job configuration
	private static String state;
	public void configure(JobConf conf){
		state = conf.get("state");
	}

	private Text out_value = new Text();
	public void reduce(Text output_key, Iterator<Text> values,
			OutputCollector<Text, Text> output,
			Reporter reporter) throws IOException{

			int totRecords = 0;
			//Sum up all the 1's for a given key
			while(values.hasNext()){
				String val = values.next().toString();
				totRecords+=Integer.parseInt(val);
			}
			out_value.set(""+totRecords);
			//Output the number of occurrences of the key
			output.collect(output_key, out_value);
			
	}
}

