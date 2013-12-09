package gameObject.action;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import gameObject.GameUnit;
import gameObject.item.Item;
import grid.GridConstants;


@JsonAutoDetect
public class ItemOutcome extends Outcome {
    private Item myItem;

    public ItemOutcome () {
        myType = GridConstants.ITEM;
    }

    public void applyOutcome (GameUnit unit, double effectiveness) {
        int newAmount = getNewAmount(unit, effectiveness);

        newAmount = (newAmount > 0 ? newAmount : 0);

        unit.combatSetItemValue(myItem, newAmount);
    }

    public boolean checkValidOutcome (GameUnit unit, double effectiveness) {
        int newAmount = getNewAmount(unit, effectiveness);

        return !(newAmount < 0);
    }

    private int getNewAmount (GameUnit unit, double effectiveness) {
        int newAmount;
        if (isFixed) {
            newAmount = unit.combatGetItemValue(myItem) + myAmount;
        }
        else {
            newAmount =
                    Math.round((float) (unit.combatGetItemValue(myItem) + myAmount * effectiveness));
        }
        return newAmount;
    }

    public Object getAffectee () {
        return myItem;
    }

    public void setAffectee (Object object) {
        myItem = (Item) object;
    }

    public String toString () {
        return "Item Outcome";
    }
}
