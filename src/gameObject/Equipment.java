package gameObject;

public class Equipment extends Items {
    private StatModifier modifiers;

    public StatModifier getModifiers () {
        return modifiers;
    }

    public void setModifier (StatModifier modifiers) {
        this.modifiers = modifiers;
    }
}
