package gameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * 
 * Stats class that keeps track of a group of stats and their values. Is used to store a game
 * unit's stat values.
 * 
 * @author Andy Bradshaw
 * @author Ken McAndrews
 */
@JsonAutoDetect
public class Stats {
    @JsonProperty
    protected Map<String, Integer> myStatMap = new HashMap<>();

    public Stats () {

    }

    @JsonIgnore
    public Integer getStatValue (String statName) {
        return myStatMap.get(statName);
    }

    public void modExisting (String statName, Integer value) {
        if (myStatMap.containsKey(statName)) {
            myStatMap.put(statName, value);
        }
    }

    @JsonIgnore
    public List<String> getStatNames () {
        List<String> statNames = new ArrayList<>();
        statNames.addAll(myStatMap.keySet());
        return statNames;
    }

    public void setStatList (Map<String, Integer> myStatMap) {
        this.myStatMap = myStatMap;
    }

    public Map<String, Integer> getStats () {
        return myStatMap;
    }

    public void setStats (Map<String, Integer> myStatMap) {
        this.myStatMap = myStatMap;
    }

    @Override
    public Stats clone () {
        Stats cloneStats = new Stats();
        Map<String, Integer> cloneMap = new HashMap<>();

        for (String stat : myStatMap.keySet()) {
            cloneMap.put(stat, myStatMap.get(stat));
        }

        cloneStats.setStatList(cloneMap);
        return cloneStats;
    }

}
