import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StateMapper extends Mapper<LongWritable, Text, Text, Text> {

    HashMap<String, Region> states;

    @Override
    public void setup(Context context) throws IOException {
        this.states = new HashMap<>();

        URI[] cachedFiles = context.getCacheFiles();
        if(cachedFiles == null || cachedFiles.length == 0){
            //unable to access cache file
            System.out.println("Can't access state definitions");
            System.exit(1);
            return;
        }

        FileSystem fs = FileSystem.get(context.getConfiguration());
        Path filePath = new Path(cachedFiles[0].toString());
        this.loadStates(fs, filePath);

    }

    public void loadStates(FileSystem fs, Path stateFile) throws IOException {
        Scanner scanner = new Scanner(fs.open(stateFile));
        while(scanner.hasNextLine()){
            String[] line = scanner.nextLine().split("\\?");
            String state = line[0];
            Region region = new Region(state);
            line = line[1].split(","); //regions
            for(String coord : line){
                String[] split = coord.split(":");
                double lon = Double.parseDouble(split[0]);
                double lat = Double.parseDouble(split[1]);
                GeographicPosition pos = new GeographicPosition(lat, lon);
                region.addPoint(pos);
            }
            this.states.put(region.name, region);
        }

    }

    @Override
    public void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] columns = value.toString().split(",");

        if(columns[0].equals("CO2 PPM")){ //ignore header row
            return;
        }

        double lat = Double.parseDouble(columns[1]);
        double lon = Double.parseDouble(columns[2]);

        GeographicPosition pos = new GeographicPosition(lat, lon);

        String state = "OUTSIDE-US";

        for(Map.Entry<String, Region> en : states.entrySet()){
            if(pos.isInsideRegion(en.getValue())){
                //We're in this state
                state = en.getKey().toUpperCase();
                break;
            }
        }

        if(state.equals("OUTSIDE-US")){ //Just keep count of these, otherwise we having timeout issues during reduce step
            context.write(new Text(state), new Text("1"));
        }else{
            String[] newColumns = new String[5];
            newColumns[0] = columns[0]; //CO2 PPM
            newColumns[1] = columns[3]; //Year
            newColumns[2] = columns[4]; //Month
            newColumns[3] = columns[5]; //Day
            newColumns[4] = state; //State, used in last analytic job

            String val = String.join(",", newColumns);
            context.write(new Text(state), new Text(val)); //Write out state and data
        }



    }

}
