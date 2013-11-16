package gameObject.item;

import action.CombatAction;
import gameObject.StatModifier;
import java.util.List;


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
        super(name, modifiers);
        myActionList = actionList;
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

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((myActionList == null) ? 0 : myActionList.hashCode());
        return result;
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Weapon other = (Weapon) obj;
        if (myActionList == null) {
            if (other.myActionList != null) return false;
        }
        else if (!myActionList.equals(other.myActionList)) return false;
        return true;
    }

}
