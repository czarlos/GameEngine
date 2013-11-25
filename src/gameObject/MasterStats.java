package gameObject;

/**
 * Master stats class to store the master stats list in the world manager
 * 
 * @author Ken McAndrews
 * 
 */
public class MasterStats extends Stats {

    /**
     * Constructor for MasterStats which currently does nothing
     */
    public MasterStats () {
    }

    /**
     * Adds a stat value, or overwrites an existing stat, in the stat map.
     * 
     * @param name - The name of the stat to be changed/added
     * @param value - The default value of the stat to be changed to/added
     */
    public void setStatValue (String name, Integer value) {
        myStatMap.put(name, value);
    }
}
