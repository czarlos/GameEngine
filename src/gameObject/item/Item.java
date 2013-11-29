package gameObject.item;

import java.util.ArrayList;
import java.util.List;
import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import gameObject.Stats;
import gameObject.action.Action;


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
    private List<Action> myActions;
    @JsonProperty
    private Stats myStats;

    public Item () {
        myActions = new ArrayList<>();
        myStats = new Stats();
    }

    public List<Action> getActions () {
        return myActions;
    }

    public List<String> getActionNames () {
        List<String> actionNames = new ArrayList<>();

        for (Action action : myActions) {
            actionNames.add(action.getName());
        }

        return actionNames;
    }

    public void addAction (Action action) {
        myActions.add(action);
    }

    public void addAction (int index, Action action) {
        myActions.set(index, action);
    }

    public void removeAction (int index) {
        myActions.remove(index);
    }

    public void setActions (List<Action> actions) {
        myActions = actions;
    }

    public void setActionNames (List<String> actionNames) {
        // map these names to masteractions, guaranteed to be on the list.
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

    public void syncActionsWithMaster (List<Action> masterActionList) {
        int masterIndex = -1;
        for (int i = 0; i < myActions.size(); i++) {
            for (int j = 0; j < masterActionList.size(); j++) {
                if (masterActionList.get(j).getName().equals(myActions.get(i).getName())) {
                    masterIndex = j;
                    break;
                }
            }

            if (masterIndex == -1) {
                myActions.remove(i);
                i--;
            }
            else {
                myActions.set(i, masterActionList.get(masterIndex));
            }
        }
    }
}
