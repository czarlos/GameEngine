package gameObject.item;

import action.CombatAction;
import gameObject.StatModifier;
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
    private List<CombatAction> myActionList;

    public Weapon (String name, List<CombatAction> actionList, StatModifier modifiers) {
        super.setName(name);
        super.setModifier(modifiers);
        myActionList = actionList;
    }

    /**
     * Select action takes in a given action and returns it from the action list.
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
