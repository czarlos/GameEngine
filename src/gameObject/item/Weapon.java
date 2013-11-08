package gameObject.item;

import action.CombatAction;
import grid.GameUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Weapon extends Equipment {
    private Map<String, Integer> myStatMap = new HashMap<String, Integer>();
    private List<CombatAction> myActionList;

    @Override
    public void statEffect (GameUnit unit) {
        for(String statName : myStatMap.keySet()) {
            int modifiedValue = getModifiers().getStatModifier(statName)+myStatMap.get(statName);
            unit.getUnitStats().setStatValue(statName, modifiedValue);
        }
    }
    
    public CombatAction selectAction (CombatAction action) {
        for(CombatAction a : myActionList) {
            if (a.equals(action)) {
                return a;
            }
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
