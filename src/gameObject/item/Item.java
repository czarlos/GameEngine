package gameObject.item;

import java.util.ArrayList;
import java.util.List;
import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonIgnore
    public int getStat (String statName) {
        if (myStats.getStats().containsKey(statName)) { return myStats.getStatValue(statName); }
        return 0;
    }

    public Stats getStats () {
        return myStats;
    }

    public void setStats (Stats myStats) {
        this.myStats = new Stats(myStats);
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
