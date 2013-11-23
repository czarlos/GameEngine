package controllers;

import java.util.List;
import game.AI;
import gameObject.GameUnit;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GameManager extends Manager {

    int myPhaseNumber;
    int myActiveTeam;
    
    public GameManager (@JsonProperty("myGameName") String gameName) {
        super(gameName);
        myPhaseNumber = -1;
    }
    
    /**
     * Loops through all of the game units in the current team (whose turn it is)
     * and sets all of the units to active.
     * 
     * @param currentTeam
     */
    public void nextTurn(){  
        myPhaseNumber++;
        myActiveTeam = myPhaseNumber % myActiveStage.getNumberOfTeams();
        
        List<GameUnit> list =  myActiveStage.getTeamUnits(myActiveTeam);
        
        for (GameUnit unit : list) {
            unit.setActive(true);
        }
    }

    public boolean conditionsMet(){
        return myActiveStage.conditionsMet();
    }
    
    public boolean teamIsHuman(){
        return myActiveStage.getTeam(myActiveTeam).isHuman();
    }
    
    public void doAITurn(){
        AI ai = new AI(myActiveStage.getTeam(myActiveTeam), myActiveStage);
        ai.doTurn();
    }
}
