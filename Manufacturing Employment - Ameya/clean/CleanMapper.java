import java.io.IOException;
import java.util.*;

import org.apache.hadoop.io.*;
import org.apache.hadoop.util.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.conf.*;

/*Mapper class has several roles here:
 *	1) Skip records with missing data
 * 	2) Skip records with misformatted data
 * 	3) Drop the unneeded "Series ID" column
 * 	4) Modify the "Label" column
 * 	5) Add the "State" column and prepend state name to all records
 * 
 * The Mapper class will output the label as a key and the cleaned
 * record as a value, so that pairs will enter the reducer in 
 * lexicographical Label order, and will ultimately be written in an
 * appropriate order by date to output file.
 */
public class CleanMapper 
		extends MapReduceBase 
		implements Mapper<LongWritable, Text, Text, Text>{
	
	//Retrieve state name from job configuration
	private static String state;
	public void configure(JobConf conf){
		state = conf.get("state");
	}

	//Variables for output key and value
	private final static Text zero = new Text(""+0);
	private Text output_key = new Text();
	private Text output_value = new Text();
	
	public void map(LongWritable key, Text value, 
			OutputCollector<Text, Text> output, 
			Reporter reporter) 
		throws IOException{
		
		//Arraylist to store record data
		ArrayList<String> arr = new ArrayList<String>();

		//Grab input
		String input = value.toString();		
		//Tokenize csv line by comma
		Scanner comma_scan = new Scanner(input);
		comma_scan.useDelimiter(",");

		//String for output
		String out = "";
		
		//Populate arr
		while(comma_scan.hasNext()){
			arr.add(comma_scan.next());
		}
		comma_scan.close();

		//Check that there are 5 data items, if not, skip record
		if(arr.size()!=5){
			return;
		}

		//Check that no data fields are empty, if so, skip record
		for(int i = 0; i<arr.size(); i++){
			if(arr.get(i).equals("")){
				return;
			}
		}

		//For header row, remove Series ID column, add State column, and
		//output with key of 0 to ensure it is written at the top of the
		//output file in the Reducer 
		if(arr.get(0).equals("Series ID")){
			out = "State,";
			//Drop Series ID column
			for(int i = 1; i<arr.size(); i++){
				if(i<arr.size()-1){
					out+=arr.get(i)+",";
				}
				else{
					out+=arr.get(i);
				}
			}
			//Output header row
			output_value.set(out);
			output.collect(zero, output_value);
		}
		//For data rows, check that data is formatted properly, if not, skip record
		else{
			//Check that Year is an integer
			try{
				int year = Integer.parseInt(arr.get(1));
			}
			catch(NumberFormatException e){
				return;
			}
			//Check that period takes the form Mxy, where x and y are integers
			try{
				char M = arr.get(2).charAt(0);
				if(M!='M'){
					return;
				}
				int month_first_digit = Integer.parseInt(arr.get(2).substring(1, 2));
				int month_second_digit = Integer.parseInt(arr.get(2).substring(2));
			} 
			catch(NumberFormatException e){
				return;
			}
			//Check that the value is a float
			try{
				float val = Float.parseFloat(arr.get(4));
			}
			catch(NumberFormatException e){
				return;
			}
	
			//Prepend the state name to record
			out = state+",";
			//New Label of form YYYY-MM
			String label = arr.get(1)+"-"+arr.get(2).substring(1);
			//Remove Series ID data and replace old Label with new Label
			for(int i = 1; i<arr.size(); i++){
				if(i<arr.size()-1){
					if(i == 3){
						out+=label+",";
					}
					else{
						out+=arr.get(i)+",";
					}
				}
				else{
					out+=arr.get(i);
				}
			}
			//Output cleaned record
			output_key.set(label);
			output_value.set(out);
			output.collect(output_key, output_value);
		}
	}
}
