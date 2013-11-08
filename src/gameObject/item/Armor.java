package gameObject.item;

import java.util.HashMap;
import java.util.Map;
import grid.GameUnit;

/**
 * Armor passively affects the stats of the player, it can affect any stats
 * in the GameUnit, the value of the armor stats goes to the stat modifier
 * @author carlosreyes
 *
 */
public class Armor extends Equipment {
    /**
     * The statMap holds the stats that the armor affects.
     */
    private Map<String, Integer> myStatMap = new HashMap<String, Integer>();
    
    public Armor() {
    }

    /**
     * Weapons are level independent, thus stat modifiers are added to
     */
    @Override
    public void statEffect (GameUnit unit) {
        for(String statName : myStatMap.keySet()) {
            int modifiedValue = getModifiers().getStatModifier(statName)+myStatMap.get(statName);
            unit.getUnitStats().setStatValue(statName, modifiedValue);
        }
    }
    
    public Map<String, Integer> getStatMap () {
        return myStatMap;
    }

    public void setStatMap (Map<String, Integer> myStatMap) {
        this.myStatMap = myStatMap;
    }
}
