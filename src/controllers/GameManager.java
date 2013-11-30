package controllers;

import java.util.ArrayList;
import java.util.List;
import view.player.PlayerView;
import game.AI;
import gameObject.GameUnit;
import gameObject.action.Action;
import gameObject.action.MoveAction;
import gameObject.action.WaitAction;
import grid.Coordinate;


public class GameManager extends Manager {

    private int myPhaseCount;
    private int myActiveTeam;
    private List<Action> myActiveActions;
    private boolean isTurnCompleted;
    private PlayerView myView;

    public GameManager (WorldManager wm, PlayerView view) {
        super();
        myActiveStage = wm.myActiveStage;
        myStages = wm.myStages;
        myGameName = wm.myGameName;
        myEditorData = wm.myEditorData;
        myView = view;
    }

    public void doTurn () {
        clear();
        while (!conditionsMet()) {
            nextTurn();
            if (teamIsHuman()) {
                doHumanTurn();
            }
            else {
                doAITurn();
            }
           System.out.println("End turn");
        }

    }

    private void clear () {
        myActiveActions = new ArrayList<Action>();
        isTurnCompleted = false;
    }

    public void doHumanTurn () {
        myView.doTurn();
    }

    /**
     * Loops through all of the game units in the current team (whose turn it is)
     * and sets all of the units to active.
     * 
     * @param currentTeam
     */
    public void nextTurn () {
        isTurnCompleted = false;
        myPhaseCount++;
        myActiveTeam = myPhaseCount % myActiveStage.getNumberOfTeams();
        String teamName = myActiveStage.getTeamNames().get(myActiveTeam);
        List<GameUnit> list = myActiveStage.getTeamUnits(teamName);

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
        if (myActiveActions.get(actionID).getName().equals(MoveAction.MOVE_NAME)) {
            myActiveStage.getGrid().beginMove(unitCoordinate);
        }
        else {
            myActiveStage.getGrid().beginAction(unitCoordinate, myActiveActions.get(actionID));
        }
    }

    public void doAction (Coordinate unitCoordinate, Coordinate actionCoordinate, int actionID) {
        GameUnit initiator = myActiveStage.getGrid().getUnit(unitCoordinate);
        if (myActiveActions.get(actionID).getName().equals(MoveAction.MOVE_NAME)) {
            myActiveStage.getGrid().doMove(unitCoordinate, actionCoordinate);
            initiator.hasMoved();
        }
        else if (myActiveActions.get(actionID).getName().equals(WaitAction.WAIT_NAME)) {
            initiator.setActive(false);
        }
        else {
            GameUnit activeUnit = myActiveStage.getGrid().getUnit(unitCoordinate);
            GameUnit receiver = myActiveStage.getGrid().getUnit(actionCoordinate);
            myActiveActions.get(actionID).doAction(activeUnit, receiver);
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
