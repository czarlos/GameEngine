package gameObject.item;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@JsonAutoDetect
public class Item {
    @JsonProperty
    private String myName;
    @JsonProperty
    private List<Action> myActions;
    @JsonProperty
    private Stats myStats;

    public Item () {
        myActions = new ArrayList<Action>();
        myStats = new Stats();
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
    
    public void addAction(Action action) {
        myActions.add(action);
    }

    public void setActions (List<Action> actions) {
        myActions = actions;
    }

    @JsonIgnore
    public int getStat (String statName) {
        if(myStats.getStats().containsKey(statName))
            return myStats.getStatValue(statName);
        return 0;
    }

    public Stats getStats () {
        return myStats;
    }

    public void setStats (Stats myStats) {
        this.myStats = myStats;
    }
}
