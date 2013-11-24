package gameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
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

    public Integer getStatValue (String statName) {
        return myStatMap.get(statName);
    }

    public void modExisting (String statName, Integer value) {
        if (myStatMap.containsKey(statName)) {
            myStatMap.put(statName, value);
        }
    }

    public List<String> getStatNames () {
        List<String> statNames = new ArrayList<>();
        statNames.addAll(myStatMap.keySet());
        return statNames;
    }

    public void setStatList (Map<String, Integer> myStatMap) {
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

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((myStatMap == null) ? 0 : myStatMap.hashCode());
        return result;
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Stats other = (Stats) obj;
        if (myStatMap == null) {
            if (other.myStatMap != null) return false;
        }
        else if (!myStatMap.equals(other.myStatMap)) return false;
        return true;
    }
}
