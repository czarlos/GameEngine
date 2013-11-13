package gameObject;

import java.util.Map;


public class StatModifier {
    private Map<String, Integer> myStatModifiers;

    public StatModifier (Map<String, Integer> stats) {
        myStatModifiers = stats;
    }

    public Map<String, Integer> getStatModifierMap () {
        return myStatModifiers;
    }

    public Integer getStatModifier (String statName) {
        return myStatModifiers.get(statName);
    }

    public void setStatModifier (String statName, Integer value) {
//        if (myStatModifiers.containsKey(statName)) {
            myStatModifiers.put(statName, value);
//        }
    }

    public void makeStatModifier (String name, Integer value) {
        myStatModifiers.put(name, value);
    }
}
