package gameObject;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonAutoDetect
public class Stat {
    @JsonProperty
    private Map<String, Integer> myStatMap = new HashMap<String, Integer>();

    public Stat () {
    }

    public Integer getStatValue (String statName) {
        return myStatMap.get(statName);
    }

    public void addStat (String statName, Integer value) {
        if (myStatMap.containsKey(statName)) {
            myStatMap.put(statName, value);
        }
    }

    public void setStatValue (String name, Integer value) {
        myStatMap.put(name, value);
    }

    public Map<String, Integer> getStatMap () {
        return myStatMap;
    }

    public void setStatList (Map<String, Integer> myStatMap) {
        this.myStatMap = myStatMap;
    }
}
