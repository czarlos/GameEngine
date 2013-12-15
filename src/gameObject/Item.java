package gameObject;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;


/**
 * Items have a name, items can have a wide range of effects including effecting
 * stats, which is evident in the statEffect abstract method. Alternatively they
 * can have an effect on the properties of a gameunit, such as reviving a units
 * health.
 * 
 * @author carlosreyes
 * 
 */
@JsonAutoDetect
public class Item extends Customizable implements IStats {
    private List<String> myActions;
    private Stats myStats;

    public Item () {
        myActions = new ArrayList<>();
        myStats = new Stats();
    }

    public List<String> getActions () {
        return myActions;
    }

    public void setActions (List<String> newActions) {
        myActions = newActions;
    }

    @JsonIgnore
    @Deprecated
    public void addAction (String action) {
        myActions.add(action);
    }

    public void removeAction (String action) {
        int removeIndex = myActions.indexOf(action);
        if (removeIndex > -1) {
            myActions.remove(removeIndex);
        }
    }

    public void addStat (Stat newStat) {
        myStats.addStat(newStat);
    }

    public void removeStat (String removeStat) {
        myStats.remove(removeStat);
    }

    public void changeStatName (String oldName, String newName) {
        myStats.changeName(oldName, newName);
    }

    @JsonIgnore
    public int getStat (String statName) {
        return myStats.getStatValue(statName);
    }

    public Stats getStats () {
        return myStats;
    }

    public void setStats (Stats myStats) {
        myStats = new Stats(myStats);
    }

    public boolean containsStat (String name) {
        return myStats.contains(name);
    }

    public void changeActionName (String oldName, String newName) {
        if (myActions.remove(oldName)) {
            myActions.add(newName);
        }
    }
}
