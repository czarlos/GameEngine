package controllers;

import java.util.ArrayList;
import java.util.List;
import game.AI;
import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.action.Action;
import gameObject.action.MoveAction;
import gameObject.action.WaitAction;
import grid.Coordinate;


public class GameManager extends Manager {

    private int myPhaseNumber;
    private int myActiveTeam;
    private List<Action> myActiveActions;
    private boolean isTurnCompleted;

    public GameManager (WorldManager wm) {
        super();
        myActiveStage = wm.myActiveStage;
        myStages = wm.myStages;
        myGameName = wm.myGameName;
        myEditorData = wm.myEditorData;
    }

    private void doTurn () {
        clear();
        while (!conditionsMet()) {
            nextTurn();
            if (teamIsHuman()) {
                doHumanTurn();
            }
            else {
                doAITurn();
            }
        }
    }

    private void clear () {
        myPhaseNumber = -1;
        myActiveActions = new ArrayList<Action>();
        isTurnCompleted = false;
    }

    public void doHumanTurn () {
        // TODO: needs to wait until !turnCompleted();
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

    public boolean nextStage () {
        int index = myStages.indexOf(myActiveStage);
        if (!(index < myStages.size())) {
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

    /**
     * Generates a list of information that a coordinate contains, including tiles and objects
     * 
     * @param coordinate Coordinate that is being asked for
     * @return List of Strings that contain information about the coordinate
     */
    public List<String> generateTileInfoList (Coordinate coordinate) {
        return myActiveStage.getGrid().generateTileInfo(coordinate);
    }

    /**
     * Generates a list of information that a coordinate contains about a Game Object
     * 
     * @param coordinate Coordinate that is being asked for
     * @return List of Strings that contain information about the coordinate. Null if there is no
     *         object at coordinate
     */
    public List<String> generateObjectInfo (Coordinate coordinate) {
        return myActiveStage.getGrid().generateObjectInfo(coordinate);
    }

    public List<String> getActions (Coordinate coordinate) {
        myActiveActions = myActiveStage.getGrid().generateActionList(coordinate);
        if (myActiveActions != null) {
            List<String> actionNames = new ArrayList<>();
            for (Action action : myActiveActions) {
                actionNames.add(action.getName());
            }
            return actionNames;
        }
        return null;
    }

    public void beginAction (Coordinate unitCoordinate, int actionID) {
        myActiveStage.getGrid().setTilesInactive();
        if (myActiveActions.get(actionID).getName().equals(MoveAction.NAME)) {
            myActiveStage.getGrid().beginMove(unitCoordinate);
        }
        else {
            myActiveStage.getGrid().beginAction(unitCoordinate, myActiveActions.get(actionID));
        }
    }

    public void doAction (Coordinate unitCoordinate, Coordinate actionCoordinate, int actionID) {
        GameUnit initiator = myActiveStage.getGrid().getUnit(unitCoordinate);

        if (myActiveActions.get(actionID).getName().equals(MoveAction.NAME)) {
            myActiveStage.getGrid().doMove(unitCoordinate, actionCoordinate);
            initiator.hasMoved();
        }
        else if (myActiveActions.get(actionID).getName().equals(WaitAction.NAME)) {
            initiator.setActive(false);
        }
        else {
            List<GameObject> receivers =
                    myActiveStage.getGrid().doAction(unitCoordinate, actionCoordinate,
                                                     myActiveActions.get(actionID));
            for (GameObject receiver : receivers) {
                myActiveActions.get(actionID).doAction(initiator, receiver);
            }
            initiator.setActive(false);
        }

        myActiveStage.getGrid().setTilesInactive();
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

    public boolean didHumanWin () {
        return myActiveStage.getWinningTeam().isHuman();
    }
}
