package gameObject.item;

import java.util.HashMap;
import java.util.Map;
import gameObject.StatModifier;
import grid.GameUnit;


/**
 * Armor passively affects the stats of the player, it can affect any stats
 * in the GameUnit, the value of the armor stats goes to the stat modifier
 * 
 * @author carlosreyes
 * 
 */
public class Armor extends Equipment {

    public Armor (String name, StatModifier modifier) {
        super.setName(name);
        super.setModifier(modifier);
    }

}
