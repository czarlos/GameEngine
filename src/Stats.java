import java.util.Map;


public class Stats {
    private Map<String, Integer> myStatList;

    public Stats () {

    }

    public Integer getStat (String name) {
        return myStatList.get(name);
    }

    public void makeStat (String name, Integer value) {
        myStatList.put(name, value);
    }
}
