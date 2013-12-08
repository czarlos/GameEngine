package gameObject.item;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import gameObject.Stat;
import gameObject.Stats;


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
public class Item extends Customizable {
    @JsonProperty
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
    public void addAction (String action) {
        myActions.add(action);
    }

    public void removeAction (String action) {
        for (int i = 0; i < myActions.size(); i++) {
            if (myActions.get(i).equals(action)) {
                myActions.remove(i);
            }
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

    @Override
    public boolean equals (Object other) {
        if (other instanceof Customizable) {
            return this.getName().equals(((Customizable) other).getName());
        }
        else return false;
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        return myName.length() * prime;
    }
}
