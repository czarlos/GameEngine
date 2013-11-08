package gameObject.item;

import action.CombatAction;
import grid.GameUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Weapon is any item that modifies stat modifiers
 * and by extention stats, the primary difference between a weapon and
 * armor is that armor does not have actions, whereas weapons do. Ex.
 * a weapon such as a sword can have an action called "stab" that does some damage
 * to an opponent.
 * 
 * @author carlosreyes
 * 
 */
public class Weapon extends Equipment {
    private Map<String, Integer> myStatMap = new HashMap<String, Integer>();
    private List<CombatAction> myActionList;

    public Weapon (String name, List<CombatAction> actionList, Map<String, Integer> statMap) {
        super.setName(name);
        myStatMap = statMap;
        myActionList = actionList;
    }

    @Override
    public void statEffect (GameUnit unit) {
        for (String statName : myStatMap.keySet()) {
            int modifiedValue = getModifiers().getStatModifier(statName) + myStatMap.get(statName);
            unit.getUnitStats().setStatValue(statName, modifiedValue);
        }
    }

    /**
     * Select action takes in a given action and returns it from the action list.
     * 
     * @param action
     * @return
     */
    public CombatAction selectAction (CombatAction action) {
        for (CombatAction a : myActionList) {
            if (a.equals(action)) { return a; }
        }
        return action;
    }

    public List<CombatAction> getActionList () {
        return myActionList;
    }

    public void setActionList (List<CombatAction> myActionList) {
        this.myActionList = myActionList;
    }

}
