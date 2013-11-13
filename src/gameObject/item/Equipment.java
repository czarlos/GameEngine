package gameObject.item;

import gameObject.GameUnit;
import gameObject.StatModifier;


/**
 * Equipment is much like items however equipment always has stat modifiers whereas items do not
 * necessarily.
 * For example any armor or weaponry is going to up the stat modifiers (and in turn the stats) of
 * the player holding
 * them, however an item such as a healing potion would only affect something when it is used. Thus
 * equipment actively
 * modifies stats.
 * 
 * @author carlosreyes
 * 
 */
public abstract class Equipment extends Item {
    private StatModifier myModifiers;

    /**
     * Stat effect runs through the stats in the piece of equipment that the unit has and
     * uses stat modifiers to increase the stats of the unit holding them.
     */
    @Override
    public void statEffect (GameUnit unit) {
        for (String statName : getModifiers().getStatModifierMap().keySet()) {
            int modifiedValue =
                    getModifiers().getStatModifier(statName) +
                            unit.getStats().getStatValue(statName);
            unit.getStats().setStatValue(statName, modifiedValue);
        }
    }

    public StatModifier getModifiers () {
        return myModifiers;
    }

    public void setModifier (StatModifier modifiers) {
        this.myModifiers = modifiers;
    }

}
