package gameObject.item;

import gameObject.Stats;
import gameObject.action.Action;
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

    public Weapon () {
        super();
    }

}
