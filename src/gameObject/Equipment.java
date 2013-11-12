package gameObject;

import gameObject.item.Item;

public class Equipment extends Item {
    private StatModifier modifiers;

    public StatModifier getModifiers () {
        return modifiers;
    }

    public void setModifier (StatModifier modifiers) {
        this.modifiers = modifiers;
    }

    @Override
    public void statEffect (GameUnit gameUnit) {
        // TODO Auto-generated method stub
        
    }
}
