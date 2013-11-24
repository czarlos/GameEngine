package gameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


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
    public List<String> getStatNames (){
        List<String> statNames = new ArrayList<>();
        statNames.addAll(myStatMap.keySet());
        return statNames;
    }

    public Map<String, Integer> getStats(){
        return myStatMap;
    }
    
    public void setStats (Map<String, Integer> myStatMap) {
        this.myStatMap = myStatMap;
    }
    
}
