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
        if (myStatModifiers.containsKey(statName)) {
            myStatModifiers.put(statName, value);
        }
    }

    public void makeStatModifier (String name, Integer value) {
        myStatModifiers.put(name, value);
    }

    @Override
    public int hashCode () {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((myStatModifiers == null) ? 0 : myStatModifiers.hashCode());
        return result;
    }

    @Override
    public boolean equals (Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        StatModifier other = (StatModifier) obj;
        if (myStatModifiers == null) {
            if (other.myStatModifiers != null) return false;
        }
        else if (!myStatModifiers.equals(other.myStatModifiers)) return false;
        return true;
    }

}
