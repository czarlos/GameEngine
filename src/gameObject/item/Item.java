package gameObject.item;

import java.util.List;
import gameObject.Stats;
import gameObject.action.Action;


/**
 * Items have a name and an quantity (amount), items can have a wide range of effects including
 * effecting stats, which is evident in the statEffect abstract method. Alternatively they can have
 * an effect on the properties of a gameunit, such as reviving a units health.
 * 
 * @author carlosreyes
 * 
 */
public class Item {
    private String myName;
    private List<Action> myActions;
    private Stats myStats;
    
    public Item (String name, List<Action> actions, Stats stats) {
        myName = name;
        myActions = actions;
        myStats = stats;
    }
    
    public String getName () {
        return myName;
    }

    public void setName (String name) {
        this.myName = name;
    }

    public List<Action> getActions () {
        return myActions;
    }

    public void setActions (List<Action> myActions) {
        this.myActions = myActions;
    }

    public int getStat (String statName) {
        return myStats.getStatValue(statName);
    }
    
    public Stats getStats () {
        return myStats;
    }

    public void setStats (Stats myStats) {
        this.myStats = myStats;
    }
}
