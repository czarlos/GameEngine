package gameObject.action;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import gameObject.GameUnit;
import gameObject.item.Item;


@JsonAutoDetect
public class ItemOutcome extends Outcome {
    private Item myItem;

    public ItemOutcome () {

    }

    public ItemOutcome (Item item, int amount, boolean fixed) {
        super(amount, fixed);
        myItem = item;
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

    public Item getItem () {
        return myItem;
    }

    public void setItem (Item item) {
        myItem = item;
    }
}
