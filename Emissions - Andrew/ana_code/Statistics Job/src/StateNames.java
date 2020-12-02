import java.util.HashMap;
import java.util.Map;

public class StateNames {

    Map<String, String> states = new HashMap<String, String>();

    public StateNames(){
        states.put("alabama","al");
        states.put("alaska","ak");
        states.put("alberta","ab");
        states.put("arizona","az");
        states.put("arkansas","ar");
        states.put("california","ca");
        states.put("colorado","co");
        states.put("connecticut","ct");
        states.put("delaware","de");
        states.put("florida","fl");
        states.put("georgia","ga");
        states.put("hawaii","hi");
        states.put("idaho","id");
        states.put("illinois","il");
        states.put("indiana","in");
        states.put("iowa","ia");
        states.put("kansas","ks");
        states.put("kentucky","ky");
        states.put("louisiana","la");
        states.put("maine","me");
        states.put("maryland","md");
        states.put("massachusetts","ma");
        states.put("michigan","mi");
        states.put("minnesota","mn");
        states.put("mississippi","ms");
        states.put("missouri","mo");
        states.put("montana","mt");
        states.put("nebraska","ne");
        states.put("nevada","nv");
        states.put("new hampshire","nh");
        states.put("new jersey","nj");
        states.put("new mexico","nm");
        states.put("new york","ny");
        states.put("north carolina","nc");
        states.put("north dakota","nd");
        states.put("ohio","oh");
        states.put("oklahoma","ok");
        states.put("oregon","or");
        states.put("pennsylvania","pa");
        states.put("rhode island","ri");
        states.put("south carolina","sc");
        states.put("south dakota","sd");
        states.put("tennessee","tn");
        states.put("texas","tx");
        states.put("utah","ut");
        states.put("vermont","vt");
        states.put("virginia","va");
        states.put("washington","wa");
        states.put("west virginia","wv");
        states.put("wisconsin","wi");
        states.put("wyoming","wy");
    }

    public String getAbbr(String name){
        return states.get(name.toLowerCase()).toUpperCase();
    }
}
