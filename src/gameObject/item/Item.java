package gameObject.item;

import java.util.List;
import gameObject.GameUnit;
import gameObject.Stat;
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
    private Stat myStats;
    
    public Item (String name, List<Action> actions, Stat stats) {
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
    
    public Stat getStats () {
        return myStats;
    }

    public void setStats (Stat myStats) {
        this.myStats = myStats;
    }
}
