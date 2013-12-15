package stage;

import gameObject.GameUnit;
import java.util.List;


/**
 * Is fulfilled when somebody from a specified team has a certain item.
 * 
 * @author Leevi
 */
public class ItemCondition extends Condition {

    private String myAffiliation;
    private String myItem;
    private int myAmount;

    public ItemCondition () {
        myAffiliation = "player";
        myItem = "milk";
        myAmount = 0;
    }

    public String getAffiliation () {
        return myAffiliation;
    }

    public String getItem () {
        return myItem;
    }

    public int getAmount () {
        return myAmount;
    }

    public void setAffiliation (String affiliation) {
        this.myAffiliation = affiliation;
    }

    public void setItem (String item) {
        this.myItem = item;
    }

    public void setAmount (int amount) {
        this.myAmount = amount;
    }

    @Override
    boolean isFulfilled (Stage stage) {
        List<GameUnit> theTeam = stage.getTeamUnits(myAffiliation);
        for (GameUnit gu : theTeam) {
            if (gu.getItemAmount(myItem) > myAmount) { return true; }
        }
        return false;
    }

    @Override
    public String toString () {
        return "Item Condition";
    }
}
