package gameObject.action;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import gameObject.GameUnit;

@JsonAutoDetect
public class StatOutcome extends Outcome {
    private String myStatName;
    private int myAmount;
    private boolean isFixed;
    
    public StatOutcome () {
        
    }
    
    public StatOutcome (String statName, int amount, boolean fixed) {
        myStatName = statName;
        myAmount = amount;
        isFixed = fixed;
    }
    
    public void applyOutcome (GameUnit unit, double effectiveness) {
        int newAmount = getNewAmount(unit, effectiveness);
        
        newAmount = (newAmount > 0 ? newAmount : 0);
        
        unit.combatSetStatValue(myStatName, newAmount);
    }
    
    public boolean checkValidOutcome (GameUnit unit, double effectiveness) {
        int oldAmount = unit.combatGetStatValue(myStatName);
        int newAmount = getNewAmount(unit, effectiveness);
        
        return !(oldAmount - newAmount < 0);
    }
    
    private int getNewAmount(GameUnit unit, double effectiveness) {
        int newAmount;
        if(isFixed) {
            newAmount = unit.combatGetStatValue(myStatName) + myAmount;
        }
        else {
            newAmount = Math.round((float) (unit.combatGetStatValue(myStatName) + myAmount * effectiveness));
        }
        return newAmount;
    }
    
    public String getStatName() {
        return myStatName;
    }
    
    public void setStatName(String statName) {
        myStatName = statName;
    }
}
