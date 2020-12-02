import org.apache.hadoop.io.ArrayWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.Writable;

public class FloatArrayWritable extends ArrayWritable {

    public FloatArrayWritable() {
        super(FloatWritable.class);
    }

    public FloatArrayWritable(FloatWritable[] values){
        super(FloatWritable.class);
        set(values);
    }


    @Override
    public FloatWritable[] get(){
        Writable[] uncasted = super.get();
        if(uncasted != null){
            FloatWritable[] floats = new FloatWritable[uncasted.length];
            for(int i = 0; i < floats.length; i++){
                floats[i] = (FloatWritable)uncasted[i];
            }
            return floats;
        }else{
            return new FloatWritable[0];
        }
    }
}
