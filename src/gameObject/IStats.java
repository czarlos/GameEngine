package gameObject;

/**
 * Interface to allow stat syncing among different types
 * 
 * @author Ken McAndrews
 * 
 */
public interface IStats {

    /**
     * Method to remove a stat from its Stats instance
     * 
     * @param statName Name of the stat to remove
     */
    public abstract void removeStat (String statName);

    /**
     * Changes the stat name of a stat in its Stats instance
     * 
     * @param oldName The original name of the stat to change
     * @param newName The new name for the stat to change
     */
    public abstract void changeStatName (String oldName, String newName);

    /**
     * Adds a stat to its Stats instance
     * 
     * @param newStat The new stat to add its Stats instance
     */
    public abstract void addStat (Stat newStat);

    /**
     * Checks whether it has the given stat in its Stats instance
     * 
     * @param name The name of the stat to check the existence of
     * @return Whether or not its Stats instance contains the given stat
     */
    public abstract boolean containsStat (String name);
}
