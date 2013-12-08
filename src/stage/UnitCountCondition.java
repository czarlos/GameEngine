package stage;

import gameObject.GameUnit;
import java.util.List;


/**
 * Is fulfilled when the number of units of a certain affiliation are greater
 * than or less than a certain amount
 * 
 * @author Leevi
 * 
 */
public class UnitCountCondition extends Condition {

    private int myCount;
    private String myAffiliation;
    private boolean isGreater;

    public UnitCountCondition () {
        myCount = 0;
        myAffiliation = "enemy";
        isGreater = false;
    }

    public int getCount () {
        return myCount;
    }

    public String getAffiliation () {
        return myAffiliation;
    }

    public boolean isGreater () {
        return isGreater;
    }

    public void setCount (int count) {
        this.myCount = count;
    }

    public void setAffiliation (String affiliation) {
        this.myAffiliation = affiliation;
    }

    public void setGreater (boolean isGreater) {
        this.isGreater = isGreater;
    }

    @Override
    boolean isFulfilled (Stage stage) {
        List<GameUnit> theTeam = stage.getTeamUnits(myAffiliation);
        return isGreater != (myCount >= theTeam.size());
    }

    @Override
    public String toString () {
        return "Unit Count Condition";
    }
}
