import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.output.MultipleOutputs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StateReducer extends Reducer<Text, Text, Text, Text> {


    private MultipleOutputs mos;

    @Override
    public void setup(Context context){
        mos = new MultipleOutputs(context);
    }

    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        if(key.toString().equals("OUTSIDE-US")){ //just count these
            long total = 0;
            for(Text t : values){
                total += Long.parseLong(t.toString());
            }
            mos.write(key, new Text("" + total), key.toString());
        }else{
            List<String> vals = new ArrayList<>();
            for(Text text : values){
                vals.add(text.toString());
            }

            String value = String.join("\n", vals);
            mos.write(key, new Text(value), key.toString());
        }
    }
}
