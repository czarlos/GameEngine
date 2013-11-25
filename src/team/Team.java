package team;

import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.UnitFactory;
import java.util.ArrayList;
import java.util.List;
import stage.Condition;
import stage.Stage;
import stage.WinCondition;
import com.fasterxml.jackson.annotation.JsonProperty;


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

    @JsonProperty
    private WinCondition myWinCondition;

    public Team (String name) {
        myGold = 0;
        myName = name;
        myWinCondition = new WinCondition();
    }

    public Team (String teamName, boolean humanity) {
        this(teamName);
        setIsHuman(humanity);
        
    }

    public void setWinCondition (WinCondition wc) {
        myWinCondition = wc;
    }

    public void addCondition (Condition c) {
        myWinCondition.addCondition(c);
    }
    
    public boolean hasWon(Stage stage){
        return myWinCondition.isFulfilled(stage);
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
