package controllers;

import java.util.ArrayList;
import java.util.List;
import game.AI;
import gameObject.GameUnit;
import gameObject.action.Action;
import grid.Coordinate;
import com.fasterxml.jackson.annotation.JsonProperty;


public class GameManager extends Manager {

    private int myPhaseNumber;
    private int myActiveTeam;
    private List<Action> myActiveActions;
    private boolean isTurnCompleted;

    public GameManager (WorldManager wm) {
        super(wm.getGameName());
        myActiveStage = wm.myActiveStage;
        myStages = wm.myStages;
        myGameName = wm.myGameName;
        myEditorData = wm.myEditorData;
    }

    private void doTurn () {
        clear();
        while(!conditionsMet()){
            nextTurn();
            if(teamIsHuman()){
                doHumanTurn();
            }
            else{
                doAITurn();
            }
        }
    }


    private void clear () {
        myPhaseNumber = -1;
        myActiveActions = new ArrayList<Action>();
        isTurnCompleted = false;
    }

    public void doHumanTurn(){
        while(!turnCompleted()){
            // make java go to sleep?
        }
    }
    /**
     * Loops through all of the game units in the current team (whose turn it is)
     * and sets all of the units to active.
     * 
     * @param currentTeam
     */
    public void nextTurn () {
        isTurnCompleted = false;
        myPhaseNumber++;
        myActiveTeam = myPhaseNumber % myActiveStage.getNumberOfTeams();

        List<GameUnit> list = myActiveStage.getTeamUnits(myActiveTeam);

        for (GameUnit unit : list) {
            unit.setActive(true);
        }
    }

    public boolean nextStage() {
        int index = myStages.indexOf(myActiveStage);
        if(!(index < myStages.size())){
            setActiveStage(index++);
            return true;
        }
        return false;
    }
    
    public boolean conditionsMet () {
        return myActiveStage.conditionsMet();
    }

    public boolean teamIsHuman () {
        return myActiveStage.getTeam(myActiveTeam).isHuman();
    }

    public void doAITurn () {
        // pass in gamemanager to AI because need moveOn command
        AI ai = new AI(myActiveStage.getTeam(myActiveTeam), myActiveStage);
        ai.doTurn();
    }

    public boolean turnCompleted () {
        return isTurnCompleted;
    }

    /**
     * Frontend communication
     */

    public List<String> getActions (Coordinate coordinate) {
        GameUnit gu = myActiveStage.getGrid().getUnit(coordinate);
        if (gu != null) {
            List<String> ret = new ArrayList<String>();
            for (Action a : gu.getActions()) {
                ret.add(a.getName());
            }
            myActiveActions = gu.getActions();
            return ret;
        }

        return null;
    }

    public void doAction (Coordinate attackerLocation, Coordinate defenderLocation, int actionID) {
        GameUnit attacker = myActiveStage.getGrid().getUnit(attackerLocation);
        
        if (myActiveActions.get(actionID).getName().equals("MoveAction")) {
            myActiveStage.getGrid().doMove(attackerLocation, defenderLocation);
            attacker.hasMoved();
        }
        else {
            GameUnit defender = myActiveStage.getGrid().getUnit(defenderLocation);
            myActiveActions.get(actionID).doAction(attacker, defender);
            attacker.setActive(false);
        }
    }

    public void endTurn () {
        isTurnCompleted = true;
    }

    public String getWinningTeam () {
        return myActiveStage.getWinningTeam().getName();
    }

    public String getPreStory () {
        return myActiveStage.getPreStory();
    }

    public String getPostStory () {
        return myActiveStage.getPostStory();
    }
    
    public boolean didHumanWin(){
        return myActiveStage.getWinningTeam().isHuman();
    }
}
