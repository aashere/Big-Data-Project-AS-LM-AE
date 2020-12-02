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
//the key out is a Text value, and the value out is an Text
public class CleanMapper
 extends Mapper<LongWritable, Text, Text, Text> {

//in this method signature we can see that the key in from the input is a LongWritable value and the 
//value in is an Text value
@Override
public void map(LongWritable key, Text value, Context context)
throws IOException, InterruptedException {
int i;
//we store each row as curr_line and split that line into a list called lines
String curr_line = value.toString();
String[] lines = curr_line.split(",");
//list of all 50 states so that we only take data from U.S. states (some U.S. territories are included in the input data)
//but we are just doing the 50 states so we do not want to use that data 
String[] states=
{"AL","	AK","AZ","AR","CA","CO", "CT","DE","FL","GA",
"HI","ID","IL","IN","IA","KS","KY","LA","ME","MD",
"MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ",
"NM","NY","NC","ND","OH","OK","OR","PA","RI","SC",
"SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"};
//check that the date is valid MM/DD/YY format
if (lines[0].length()>=6){
    if (lines[0].length()<=8){
    //check that the location is a U.S. state
	for(i=0;i<50;i++){
            if (states[i].equals(lines[1])){
                //we do not want to use any records with a negative number of cases, for this would be an incorrect entry
            	if (Integer.parseInt(lines[2])>=0){
                	if (Integer.parseInt(lines[5])>=0){
                        //we write back the date, and the state as the key, and the number of total cases +new cases as the value
                   		 context.write(new Text(lines[0]+" "+lines[1]),new Text(lines[2]+ " "+lines[5]));

                }
            }
        }
    }
}
}
}


}
