package gameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;


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

    public List<String> getStatNames (){
        List<String> statNames = new ArrayList<>();
        statNames.addAll(myStatMap.keySet());
        return statNames;
    }

    public void setStatList (Map<String, Integer> myStatMap) {
        this.myStatMap = myStatMap;
    }
    
}
