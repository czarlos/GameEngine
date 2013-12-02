package team;

import gameObject.UnitFactory;
import java.util.ArrayList;
import java.util.List;
import stage.Condition;
import stage.Stage;
import stage.WinCondition;
import view.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

@JsonAutoDetect
public class Team extends Customizable {
    private int myGold;
    private boolean isHuman;
    private int lastEditingID;

    @JsonProperty
    private WinCondition myWinCondition;

    public Team () {
    }

    public Team (String name) {
        myGold = 0;
        myName = name;
        myWinCondition = new WinCondition();
        lastEditingID = 0;
    }

    public Team (String teamName, boolean humanity) {
        this(teamName);
        setIsHuman(humanity);
    }

    public void setWinCondition (WinCondition wc) {
        myWinCondition = wc;
    }

    public WinCondition getWinCondition () {
        return myWinCondition;
    }

    public void addCondition (Condition c) {
        myWinCondition.addCondition(c);
    }

    public boolean hasWon (Stage stage) {
        return myWinCondition.isFulfilled(stage);
    }

    public String getName () {
        return myName;
    }

    // should ONLY be called by JSON deserializer and Stage
    public void setName (String name) {
        myName = name;
    }

    /**
     * Gets a list of the factories that the team has in its game units.
     * 
     * @return
     */
    @JsonIgnore
    public List<UnitFactory> getFactories () {
        List<UnitFactory> factoryList = new ArrayList<UnitFactory>();
        /*
         * for (GameObject obj : myGameUnits) {
         * if (obj instanceof UnitFactory) {
         * factoryList.add((UnitFactory) obj);
         * }
         * }
         */
        return factoryList;
    }

    public int getGold () {
        return myGold;
    }

    public void setGold (int myGold) {
        this.myGold = myGold;
    }
    
    @JsonProperty("humanity")
    public boolean isHuman () {
        return isHuman;
    }

    @JsonProperty("humanity")
    public void setIsHuman (boolean humanity) {
        isHuman = humanity;
    }

    public void setEditingID (int ID) {
        lastEditingID = ID;
    }

    public int getLastEditingID () {
        return lastEditingID;
    }

}
