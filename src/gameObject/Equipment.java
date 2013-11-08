package gameObject;

public class Equipment extends Items {
    private StatModifiers modifiers;

    public StatModifiers getModifiers () {
        return modifiers;
    }

    public void setModifiers (StatModifiers modifiers) {
        this.modifiers = modifiers;
    }
}
