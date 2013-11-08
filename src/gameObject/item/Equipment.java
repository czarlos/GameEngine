package gameObject.item;

import gameObject.StatModifier;

public abstract class Equipment extends Items {
    private StatModifier myModifiers;
    
    public StatModifier getModifiers () {
        return myModifiers;
    }

    public void setModifier (StatModifier modifiers) {
        this.myModifiers = modifiers;
    }

}
