package unused;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Master stats class to store the master stats list in the world manager
 * 
 * @author Ken McAndrews
 * 
 */
@JsonAutoDetect
public class MasterStats {
    private static MasterStats masterStats = new MasterStats();
    protected Map<String, Integer> myStatMap;
    private List<String> defaultStatNames = new ArrayList<>(Arrays.asList(
                                                                          "movement", "health",
                                                                          "attack", "defense",
                                                                          "maxhealth",
                                                                          "experience"));
    private List<Integer> defaultStatValues = new ArrayList<>(Arrays.asList(3,
                                                                            10, 1, 1, 10, 0));

    /**
     * Constructor for MasterStats which currently does nothing
     */
    private MasterStats () {
        myStatMap = new HashMap<>();
        initializeDefaults();
    }

    public static MasterStats getInstance () {
        if (masterStats == null) {
            masterStats = new MasterStats();
        }

        return masterStats;
    }

    private void initializeDefaults () {
        for (int i = 0; i < defaultStatNames.size(); i++) {
            myStatMap.put(defaultStatNames.get(i), defaultStatValues.get(i));
        }
    }

    /**
     * Adds a stat value, or overwrites an existing stat, in the stat map.
     * 
     * @param name
     *        - The name of the stat to be changed/added
     * @param value
     *        - The default value of the stat to be changed to/added
     */
    @JsonIgnore
    public void setStatValue (String name, Integer value) {
        myStatMap.put(name, value);
    }

    /**
     * Gets the stat value for the given stat name
     * 
     * @param statName
     *        - The stat name to get the value for
     * @return The value of the stat name passed in
     */
    @JsonIgnore
    public Integer getStatValue (String statName) {
        return myStatMap.get(statName);
    }

    /**
     * Modifies the value of an existing stat. If the stat does not exist, does
     * nothing
     * 
     * @param statName
     *        - Name of the stat to modify
     * @param value
     *        - Value to modify the stat to
     */
    public void modExisting (String statName, Integer value) {
        if (myStatMap.containsKey(statName)) {
            myStatMap.put(statName, value);
        }
    }

    /**
     * Removes a stat
     * 
     * @param statName
     *        - The name of the stat to remove
     */
    public void remove (String statName) {
        myStatMap.remove(statName);
    }

    /**
     * Gets a list of all stat names
     * 
     * @return The list of all stat names
     */
    @JsonIgnore
    public List<String> getStatNames () {
        List<String> statNames = new ArrayList<>();
        statNames.addAll(myStatMap.keySet());
        return statNames;
    }

    /**
     * Returns the stat map of the Stats instance
     * 
     * @return The stat map of the Stats instance
     */
    public Map<String, Integer> getStats () {
        return myStatMap;
    }

    /**
     * Sets the map of stats
     * 
     * @param myStatMap
     *        - The map of stats to set to
     */
    public void setStats (Map<String, Integer> statMap) {
        Map<String, Integer> newStats = new HashMap<>();
        for (String statName : statMap.keySet()) {
            newStats.put(statName, statMap.get(statName));
        }
        myStatMap = newStats;
    }
}
