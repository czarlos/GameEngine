package team;

import gameObject.GameUnit;
import gameObject.UnitFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Team holds state about each team including the affiliation
 * number, the amount of gold a team, and the game units that 
 * this team has.
 * @author carlosreyes
 *
 */
public class Team {
    private List<GameUnit> myGameUnits;
    private int myGold;
    private int myAffiliation;
    
    /**
     * Gets a list of the factories that the team has in its game units.
     * @return
     */
    public List<UnitFactory> getFactories () {
        List<UnitFactory> factoryList = new ArrayList<UnitFactory>();
        for(GameUnit unit : myGameUnits) {
            if (unit instanceof UnitFactory) {
                factoryList.add((UnitFactory) unit);
            }
        }
        return factoryList;
    }

    public int getGold () {
        return myGold;
    }

    public void setGold (int myGold) {
        this.myGold = myGold;
    }

    public int getAffiliation () {
        return myAffiliation;
    }

    public void setAffiliation (int myAffiliation) {
        this.myAffiliation = myAffiliation;
    }
}
