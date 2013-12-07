package gameObject.action;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import gameObject.GameUnit;

@JsonAutoDetect
public class ItemOutcome extends Outcome {
    private String myItem;
    private int myAmount;
    private boolean isFixed;
    
    public ItemOutcome () {
        
    }
    
    public ItemOutcome (String item, int amount, boolean fixed) {
        myItem = item;
        myAmount = amount;
        isFixed = fixed;
    }
    
    public void applyOutcome (GameUnit unit, double effectiveness) {
        int newAmount = getNewAmount(unit, effectiveness);
        
        newAmount = (newAmount > 0 ? newAmount : 0);
        
        unit.combatSetItemValue(myItem, newAmount);
    }
    
    public boolean checkValidOutcome (GameUnit unit, double effectiveness) {
        int oldAmount = unit.combatGetItemValue(myItem);
        int newAmount = getNewAmount(unit, effectiveness);
        
        return !(oldAmount - newAmount < 0);
    }
    
    private int getNewAmount(GameUnit unit, double effectiveness) {
        int newAmount;
        if(isFixed) {
            newAmount = unit.combatGetItemValue(myItem) + myAmount;
        }
        else {
            newAmount = Math.round((float) (unit.combatGetItemValue(myItem) + myAmount * effectiveness));
        }
        return newAmount;
    }
    
    public String getItem() {
        return myItem;
    }
    
    public void setItem(String item) {
        myItem = item;
    }
}
