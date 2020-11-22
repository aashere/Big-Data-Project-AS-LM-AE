import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

public class CleanReducer extends Reducer<Text, Text, Text, Text> {


    @Override
    public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        /**We don't need a reduce job*/

    }

}
