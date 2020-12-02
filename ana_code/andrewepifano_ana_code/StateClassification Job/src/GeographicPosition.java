import java.io.Serializable;

public class GeographicPosition implements Serializable {

    double longitude; //x
    double latitude; //y

    public GeographicPosition(double latitude, double longitude){
        this.longitude = longitude;
        this.latitude = latitude;
    }


    /**

        Check if point is in region by ray-casting algorithm
        https://en.wikipedia.org/wiki/Point_in_polygon
        * we need an arbitrary direction for our ray: I picked west (which is increasing longitude)
        * Longitude range (-180 to 180) Latitude range (-90 to 90)
     *  * Basic algorithm:
     *      1. let l = the west line that goes through the point to check
     *      2. let n = 0
     *      3. For every side
     *         3b. let e = the line between the two points
     *         3c. find the intersection between e and l
     *             - if the intersection does not exist: continue
     *             - if the intersection exists and occurs between the two points of the side
     *                  n++
     *      4. if n is odd, the point is in the region, else it's out of the region
     */


    public boolean isInsideRegion(Region region){
       int n = 0; //Number of intersections
       double l = this.latitude; //y = latitude (horizontal line intersecting our point)
        for(int i = 0; i < region.coords.size(); i++){
            //Go from 0, 1 all the way to n - 0 side (use modulo for the second point
            GeographicPosition a = region.coords.get(i);
            GeographicPosition b = region.coords.get((i + 1) % region.coords.size());

            //There are two special cases, the side is vertical or horizontal
            //if side is horizontal, point must be between the two sides for intersection
            if(a.latitude == b.latitude){
                if((this.longitude < a.longitude && this.longitude > b.longitude) || (this.longitude > a.longitude && this.longitude < b.longitude)){
                    n++;
                }
                continue;
            }

            //The line between a and b
            boolean specialCase = false; //The sides are in vertical line
            double slope;
            if(a.longitude == b.longitude){
                slope = Double.POSITIVE_INFINITY; //x = a.longitude
                specialCase = true;
            }else{
                slope = (a.latitude - b.latitude)/(a.longitude - b.longitude); //y = mx + b
            }

            double intercept;

            //non special case
            if(!specialCase){
                intercept = a.latitude - slope*a.longitude; //y = mx + b, b = y - mx (solve for y-intercept)
            }else{
                intercept = a.longitude; //in this case it's the x intercept
            }

            double intersectY, intersectX;
            //We know there is an intersection because the only parallel case is when the side is horizontal, which we check for above
            if(!specialCase){
                intersectX = (l - intercept)/slope; //set the two lines equal to each other and solve for x
                intersectY = slope*(intersectX) + intercept; //Plug x back into equation;
            }else{
                intersectY = l;
                intersectX = intercept;
            }

            //Is the intersection within the two side points
            if((intersectY < a.latitude && intersectY > b.latitude) || (intersectY > a.latitude && intersectY < b.latitude)){
                //Is the intersection to the west of the point (it has to be for it to count)
                if(intersectX > this.longitude){
                    n++;
                }
            }

        }

        return n % 2 == 1;
    }

    public String toString(){
        return this.latitude + " " + this.longitude;
    }



}
