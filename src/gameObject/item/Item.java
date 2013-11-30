package gameObject.item;

import java.util.ArrayList;
import java.util.List;
import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import gameObject.Stats;
import gameObject.action.Action;
import gameObject.action.MasterActions;


/**
 * Items have a name, items can have a wide range of effects including
 * effecting stats, which is evident in the statEffect abstract method. Alternatively they can have
 * an effect on the properties of a gameunit, such as reviving a units health.
 * 
 * @author carlosreyes
 * 
 */
@JsonAutoDetect
public class Item extends Customizable {
    @JsonProperty
    private List<Integer> myActions;
    private Stats myStats;
   
    public Item () {
        myActions = new ArrayList<>();
        myStats = new Stats();
    }

    @JsonIgnore
    public List<Action> getActions () {
        List<Action> actionList = new ArrayList<>();

        for (int actionIndex : myActions) {
            actionList.add(MasterActions.getInstance().getAction(actionIndex));
        }

        return actionList;
    }

    public List<Integer> getActionIndices () {
        return myActions;
    }

    public void setActionIndices (List<Integer> newIndices) {
        myActions = newIndices;
    }

    @JsonIgnore
    public List<String> getActionNames () {
        List<String> actionNames = new ArrayList<>();

        for (int actionIndex : myActions) {
            actionNames.add(MasterActions.getInstance().getAction(actionIndex).getName());
        }

        return actionNames;
    }

    @JsonIgnore
    public void addAction (int actionIndex) {
        myActions.add(actionIndex);
    }

    public void removeAction (int actionIndex) {
        for (int i = 0; i < myActions.size(); i++) {
            if (myActions.get(i) == actionIndex) {
                myActions.remove(i);
            }
        }
    }
/*
    public void setActions (List<Integer> actions) {
        myActions = actions;
    }*/

    @JsonIgnore
    public void setActionNames (List<String> actionNames) {
        List<Integer> actionIDs = new ArrayList<Integer>();
        for(String name: actionNames){
           actionIDs.add(MasterActions.getInstance().getActionID(name));
        }
        myActions = actionIDs;
    }
    
    @JsonIgnore
    public int getStat (String statName) {
        if (myStats.getStats().containsKey(statName))
            return myStats.getStatValue(statName);
        return 0;
    }

    public Stats getStats () {
        return myStats;
    }

    public void setStats (Stats myStats) {
        this.myStats = new Stats(myStats);
    }
}
