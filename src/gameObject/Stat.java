package gameObject;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonAutoDetect
public class Stat {
    @JsonProperty
    private Map<String, Integer> myStatList = new HashMap<>();

    public Stat () {
    }

    public Integer getStatValue (String statName) {
        return myStatList.get(statName);
    }

    public void modExisting (String statName, Integer value) {
        if (myStatList.containsKey(statName)) {
            myStatList.put(statName, value);
        }
    }

    public void setStatValue (String name, Integer value) {
        myStatList.put(name, value);
    }

    public Map<String, Integer> getStatList () {
        return myStatList;
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((myStatList == null) ? 0 : myStatList.hashCode());
        return result;
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Stat other = (Stat) obj;
        if (myStatList == null) {
            if (other.myStatList != null) return false;
        }
        else if (!myStatList.equals(other.myStatList)) return false;
        return true;
    }

}
