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
public class Equipment extends Item {
    private StatModifier myModifiers;

    public Equipment (String name, StatModifier modifier) {
        super.setName(name);
        setModifier(modifier);
    }

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

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((myModifiers == null) ? 0 : myModifiers.hashCode());
        return result;
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        Equipment other = (Equipment) obj;
        if (myModifiers == null) {
            if (other.myModifiers != null) return false;
        }
        else if (!myModifiers.equals(other.myModifiers)) return false;
        return true;
    }

    public StatModifier getModifiers () {
        return myModifiers;
    }

    public void setModifier (StatModifier modifiers) {
        this.myModifiers = modifiers;
    }

}
