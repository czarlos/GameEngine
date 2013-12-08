package stage;

import gameObject.GameUnit;
import java.util.List;


/**
 * Returns true if a unit of a certain "affiliation" has achieved a stat of
 * "statType" higher than "value"
 * 
 * @author Leevi
 * 
 */
public class StatCondition extends Condition {

    private String myStatType;
    private int myValue;
    private String myAffiliation;
    
    public StatCondition () {
        super();
        myStatType = "attack";
        myValue = 100;
        myAffiliation = "player";
    }

    public String getStatType () {
        return myStatType;
    }

    public int getValue () {
        return myValue;
    }

    public String getAffiliation () {
        return myAffiliation;
    }

    public void setStatType (String statType) {
        this.myStatType = statType;
    }

    public void setValue (int value) {
        this.myValue = value;
    }

    public void setAffiliation (String affiliation) {
        this.myAffiliation = affiliation;
    }

    @Override
    boolean isFulfilled (Stage stage) {
        List<GameUnit> theTeam = stage.getTeamUnits(myAffiliation);
        for (GameUnit gu : theTeam) {
            if (gu.getStat(myStatType) > myValue) { return true; }
        }
        return false;
    }

    @Override
    public String toString () {
        return "Stat Condition";
    }
}
