package team;

import gameObject.GameObject;
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
 * 
 */
public class Team {
    private List<GameUnit> myGameUnits;
    private List<GameObject> myGameObjects;
    private int myGold;
    private int myAffiliation;
    private boolean isHuman;

    public Team (List<GameUnit> gameUnits, int affliation, boolean human) {
        myGold = 0;
        myAffiliation = affliation;
        isHuman = human;

    }

    /**
     * Gets a list of the factories that the team has in its game units.
     * 
     * @return
     */
    public List<UnitFactory> getFactories () {
        List<UnitFactory> factoryList = new ArrayList<UnitFactory>();
        for (GameObject obj : myGameUnits) {
            if (obj instanceof UnitFactory) {
                factoryList.add((UnitFactory) obj);
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

    public boolean isHuman () {
        return isHuman;
    }

    public void setHuman (boolean isHuman) {
        this.isHuman = isHuman;
    }

    public List<GameUnit> getGameUnits () {
        return myGameUnits;
    }

    public void setGameUnits (List<GameUnit> myGameUnits) {
        this.myGameUnits = myGameUnits;
    }

    public List<GameObject> getGameObjects () {
        return myGameObjects;
    }

    public void setGameObjects (List<GameObject> myGameObjects) {
        this.myGameObjects = myGameObjects;
    }

}
