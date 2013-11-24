package gameObject.item;

import gameObject.Stat;
import gameObject.StatModifier;
import gameObject.action.Action;
import gameObject.action.CombatAction;
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
public class Weapon extends Item {
    
    public Weapon (String name, List<Action> actionList, Stat stats) {
        super(name, actionList, stats);
    }

}
