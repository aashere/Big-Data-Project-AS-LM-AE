import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.conf.*;

/* Reducer class will write records to output file in order of increasing date, with the
 * header row at the top.
 *
 */
public class CleanReducer
			extends MapReduceBase
			implements Reducer<Text, Text, Text, Text>{
	
	//Retrieve state name from job configuration
	private static String state;
	public void configure(JobConf conf){
		state = conf.get("state");
	}

	private Text out_key = new Text();
	private Text out_value = new Text();
	
	public void reduce(Text output_key, Iterator<Text> values,
			OutputCollector<Text, Text> output,
			Reporter reporter) throws IOException{
			
			String record = "";
			int totValues = 0;
			//Grab the cleaned record
			while(values.hasNext()){
				record = values.next().toString();
				totValues+=1;
			}
			//Make sure there is only one unique record for this label; otherwise, skip this label
			if(totValues>1){
				return;
			}

			//Grab first item of this record as output key
			//Tokenize csv line by comma
			Scanner comma_scan = new Scanner(record);
			comma_scan.useDelimiter(",");
			String first_item = comma_scan.next();
			comma_scan.close();
			out_key.set(first_item);
			
			//Grab rest of record as output value
			out_value.set(record.substring(first_item.length()+1));

			//Output the cleaned record
			output.collect(out_key, out_value);
	}
}

