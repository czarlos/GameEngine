package gameObject.action;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import gameObject.GameUnit;


@JsonAutoDetect
public class StatOutcome extends Outcome {
    private String myStatName;

    public StatOutcome () {
    }

    public StatOutcome (String statName, int amount, boolean fixed) {
        super(amount, fixed);
        myStatName = statName;
    }

    public void applyOutcome (GameUnit unit, double effectiveness) {
        int newAmount = getNewAmount(unit, effectiveness);
        System.out.println("statoucome applyoutcome newAmount: " + newAmount);
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
        System.out.println("isFixed: " + isFixed);
        if (isFixed) {
            System.out.println("getNewamourn myAMountasdfasdf: " + myAmount);
            newAmount = unit.combatGetStatValue(myStatName) + myAmount;
        }
        else {
            newAmount =
                    Math.round((float) (unit.combatGetStatValue(myStatName) + myAmount *
                                                                              effectiveness));
        }
        return newAmount;
    }

    public String getStatName () {
        return myStatName;
    }

    public void setStatName (String statName) {
        myStatName = statName;
    }
}
