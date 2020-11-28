import java.io.IOException;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
public class CleanMapper
 extends Mapper<LongWritable, Text, Text, Text> {

@Override
public void map(LongWritable key, Text value, Context context)
throws IOException, InterruptedException {
int i;
String curr_line = value.toString();
String[] lines = curr_line.split(",");
String[] states=
{"AL","	AK","AZ","AR","CA","CO", "CT","DE","FL","GA",
"HI","ID","IL","IN","IA","KS","KY","LA","ME","MD",
"MA","MI","MN","MS","MO","MT","NE","NV","NH","NJ",
"NM","NY","NC","ND","OH","OK","OR","PA","RI","SC",
"SD","TN","TX","UT","VT","VA","WA","WV","WI","WY"};
if (lines[0].length()>=6){
    if (lines[0].length()<=8){
	for(i=0;i<50;i++){
            if (states[i].equals(lines[1])){
            	if (Integer.parseInt(lines[2])>=0){
                	if (Integer.parseInt(lines[5])>=0){
                   		 context.write(new Text(lines[0]+" "+lines[1]),new Text(lines[2]+ " "+lines[5]));

                }
            }
        }
    }
}
}
}


}
