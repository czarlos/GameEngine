package gameObject.action;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import gameObject.GameUnit;
import gameObject.Stat;
import grid.GridConstants;


@JsonAutoDetect
public class StatOutcome extends Outcome {
    private String myStatName;
    private Stat myStat;
    
    public StatOutcome () {
        myType = GridConstants.MASTERSTATS;
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

    private int getNewAmount (GameUnit unit, double effectiveness) {
        int newAmount;
        if (isFixed) {
            newAmount = unit.combatGetStatValue(myStatName) + myAmount;
        }
        else {
            newAmount =
                    Math.round((float) (unit.combatGetStatValue(myStatName) + myAmount *
                                                                              effectiveness));
        }
        return newAmount;
    }

    public Object getAffectee () {
        return myStat;
    }

    public void setAffectee (Object object) {
        myStat = (Stat) object;
        myStatName = myStat.getName();
    }

    public String toString () {
        return "Stat Outcome";
    }
}
