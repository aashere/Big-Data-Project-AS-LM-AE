import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Region implements Serializable {

    String name;

    List<GeographicPosition> coords; //Points are ordered such that the nth and n+1th point form a side, the last vertex and the first also form a side

    public Region(String name){
        this.name = name;
        this.coords = new ArrayList<>();
    }

    public void addPoint(GeographicPosition a){
        this.coords.add(a);
    }


}
