package team;

import gameObject.GameUnit;
import gameObject.UnitFactory;
import java.util.ArrayList;
import java.util.List;


/**
 * Team holds state about each team including the affiliation
 * number, the amount of gold a team, and the game units that
 * this team has.
 * 
 * @author carlosreyes
 * @author Leevi
 * 
 */
public class Team {
    private List<GameUnit> myGameUnits;
    private int myGold;
    private boolean isHuman;
    private String myName;

    public Team (String name) {
        myGold = 0;
        myName = name;
    }

    public String getName () {
        return myName;
    }

    /**
     * Gets a list of the factories that the team has in its game units.
     * 
     * @return
     */
    public List<UnitFactory> getFactories () {
        List<UnitFactory> factoryList = new ArrayList<UnitFactory>();
        for (GameUnit unit : myGameUnits) {
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

    public boolean isHuman () {
        return isHuman;
    }

    public void setIsHuman (boolean humanity) {
        isHuman = humanity;
    }

    public List<GameUnit> getGameUnits () {
        return myGameUnits;
    }

    public void setGameUnits (List<GameUnit> myGameUnits) {
        this.myGameUnits = myGameUnits;
    }

    public void addGameUnit (GameUnit gu) {
        myGameUnits.add(gu);
    }

}
