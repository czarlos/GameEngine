package stage;

import gameObject.Customizable;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Team holds state about each team including the affiliation number, the amount
 * of gold a team, and the game units that this team has.
 * 
 * @author carlosreyes
 * @author Leevi
 * 
 */

@JsonAutoDetect
public class Team extends Customizable {
    private int myGold;
    private boolean isHuman;
    private WinCondition myWinCondition;

    public Team () {
    }

    public void setWinCondition (WinCondition wc) {
        myWinCondition = wc;
    }

    public WinCondition getWinCondition () {
        return myWinCondition;
    }

    public boolean hasWon (Stage stage) {
        return myWinCondition.isFulfilled(stage);
    }

    public String getName () {
        return myName;
    }

    public void setName (String name) {
        myName = name;
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
}
