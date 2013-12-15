package gameObject;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * 
 * Stats class that keeps track of a group of stats and their values. Is used to
 * store a game unit's stat values.
 * 
 * @author Andy Bradshaw
 * @author Ken McAndrews
 */
@JsonAutoDetect
public class Stats {
    protected List<Stat> myStatList;

    /**
     * Constructor for stats which currently does nothing
     */
    public Stats () {
        myStatList = new ArrayList<>();
    }

    public Stats (Stats newStats) {
        myStatList = new ArrayList<>();

        for (Stat stat : newStats.getStats()) {
            myStatList.add(stat.clone());
        }
    }

    public void addStat (Stat newStat) {
        myStatList.add(newStat);
    }

    /**
     * Modifies the value of an existing stat. If the stat does not exist, does
     * nothing
     * 
     * @param statName Name of the stat to modify
     * @param value Value to modify the stat to
     */
    public void modExisting (String statName, Integer value) {
        int statIndex = getStatNames().indexOf(statName);
        if (statIndex != -1) {
            myStatList.get(statIndex).setValue(value);
        }
    }

    /**
     * Removes a stat
     * 
     * @param statName The name of the stat to remove
     */
    public void remove (String statName) {
        int statIndex = getStatNames().indexOf(statName);
        if (statIndex != -1) {
            myStatList.remove(statIndex);
        }
    }

    public boolean contains (String statName) {
        return getStatNames().contains(statName);
    }

    public void changeName (String oldName, String newName) {
        for (Stat stat : myStatList) {
            if (stat.getName().equals(oldName)) {
                stat.setName(newName);
            }
        }
    }

    /**
     * Gets the stat value for the given stat name
     * 
     * @param statName The stat name to get the value for
     * @return The value of the stat name passed in
     */
    @JsonIgnore
    public Integer getStatValue (String statName) {
        int statIndex = getStatNames().indexOf(statName);
        if (statIndex != -1) { return myStatList.get(statIndex).getValue(); }
        return 0;
    }

    /**
     * Gets a list of all stat names
     * 
     * @return The list of all stat names
     */
    @JsonIgnore
    public List<String> getStatNames () {
        List<String> statNames = new ArrayList<>();
        for (Stat stat : myStatList) {
            statNames.add(stat.getName());
        }
        return statNames;
    }

    /**
     * Returns the stat map of the Stats instance
     * 
     * @return The stat map of the Stats instance
     */
    public List<Stat> getStats () {
        return myStatList;
    }

    /**
     * Sets the map of stats
     * 
     * @param myStatMap xzThe map of stats to set to
     */
    public void setStats (List<Stat> newStatList) {
        List<Stat> newStats = new ArrayList<>();
        for (Stat stat : newStatList) {
            newStats.add(stat.clone());
        }
        myStatList = newStatList;
    }

    public String toString () {
        return "Stats";
    }
}
