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
    protected Map<String, Integer> myStatMap;
    MasterStats masterStatsMap;

    /**
     * Constructor for stats which currently does nothing
     */
    public Stats () {
        myStatMap = new HashMap<>();
        masterStatsMap = MasterStats.getInstance();
        for (String stat : masterStatsMap.getStatNames()) {
            myStatMap.put(stat, masterStatsMap.getStatValue(stat));
        }
    }

    public Stats (Stats stat) {
        myStatMap = new HashMap<>();
        for (String statName : stat.getStatNames()) {
            myStatMap.put(statName, stat.getStatValue(statName));
        }
        updateFromMaster();

    }

    /**
     * Gets the stat value for the given stat name
     * 
     * @param statName - The stat name to get the value for
     * @return The value of the stat name passed in
     */
    @JsonIgnore
    public Integer getStatValue (String statName) {
        return myStatMap.get(statName);
    }

    /**
     * Modifies the value of an existing stat. If the stat does not exist, does nothing
     * 
     * @param statName - Name of the stat to modify
     * @param value - Value to modify the stat to
     */
    public void modExisting (String statName, Integer value) {
        if (myStatMap.containsKey(statName)) {
            myStatMap.put(statName, value);
        }
    }

    /**
     * Removes a stat
     * 
     * @param statName - The name of the stat to remove
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
     * @param myStatMap - The map of stats to set to
     */
    public void setStats (Map<String, Integer> statMap) {
        Map<String, Integer> newStats = new HashMap<>();
        for (String statName : statMap.keySet()) {
            newStats.put(statName, statMap.get(statName));
        }
        myStatMap = newStats;
    }

    /**
     * Updates the stats maps of the current Stats instance and the master stats map. If a stat
     * exists in the master stats map, but not in the current Stats instance map, then it adds it to
     * the current Stats map instance. If a stat exists in the current Stats instance map, but not
     * in the master stats map, it removes it from the current Stats map instance
     * 
     * @param masterStatMap The master stat map from the world manager
     */
    public void updateFromMaster () {
        for (String stat : MasterStats.getInstance().getStatNames()) {
            if (!getStatNames().contains(stat)) {
                myStatMap.put(stat, MasterStats.getInstance().getStatValue(stat));
            }
        }

        for (String stat : getStatNames()) {
            if (!MasterStats.getInstance().getStatNames().contains(stat)) {
                remove(stat);
            }
        }
    }
}
