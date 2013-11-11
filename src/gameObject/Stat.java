package gameObject;

import java.util.Map;


public class Stat {
    private Map<String, Integer> myStatList;

    public Stat () {

    }

    public Integer getStatValue (String statName) {
        return myStatList.get(statName);
    }

    public void setStatValue (String statName, Integer value) {
        if (myStatList.containsKey(statName)) {
            myStatList.put(statName, value);
        }
    }

    public void makeStat (String name, Integer value) {
        myStatList.put(name, value);
    }
}
