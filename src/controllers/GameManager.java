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


/**
 * 
 * @author kevinjian, leevi, whoever else
 * 
 */
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

    public void beginTurn () {
        clear();
        if (conditionsMet()) {
            if (!nextStage())
                // win
                myView.setTitle(getActiveTeamName() + " won!!");
            myView.displayWinDialog();
            return;
        }
        nextTurn();
        myView.setTitle(getActiveTitle());
    }

    private void clear () {
        myActiveActions = new ArrayList<Action>();
        isTurnCompleted = false;
    }

    public void doUntilHumanTurn () {
        int count = 0;
        while (!teamIsHuman()) {
            doAITurn();
            beginTurn();
            count++;
            if (count > 10)
                throw new RuntimeException("Count Max reached.");
        }
    }

    /**
     * Loops through all of the game units in the current team (whose turn it
     * is) and sets all of the units to active.
     * 
     * @param currentTeam
     */
    public void nextTurn () {
        isTurnCompleted = false;
        myPhaseCount++;
        myActiveTeam = myPhaseCount % myActiveStage.getNumberOfTeams();
        String teamName = getActiveTeamName();
        List<GameUnit> list = myActiveStage.getTeamUnits(teamName);

        for (GameUnit unit : list) {
            unit.setActive(true);
        }
    }

    private String getActiveTeamName () {
        return myActiveStage.getTeamNames().get(myActiveTeam);
    }

    private String getActiveTitle () {
        return getActiveTeamName() + " - " + getActiveStageName() + " - " + myGameName;
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
        // return false;
        //TODO: FIX THE PROBLEM
        return myActiveStage.conditionsMet();
    }

    public boolean teamIsHuman () {
        return myActiveStage.getTeam(myActiveTeam).isHuman();
    }

    public void doAITurn () {
        // pass in gamemanager to AI because need moveOn command
        AI ai = new AI(myActiveStage.getTeam(myActiveTeam), myActiveStage);
        //ai.doTurn();
    }

    public boolean turnCompleted () {
        return isTurnCompleted;
    }

    /**
     * Frontend communication
     */

    /**
     * Generates a list of information that a coordinate contains, including
     * tiles and objects
     * 
     * @param coordinate
     *        Coordinate that is being asked for
     * @return List of Strings that contain information about the coordinate
     */
    public List<String> generateTileInfoList (Coordinate coordinate) {
        return myActiveStage.getGrid().generateTileInfo(coordinate);
    }

    /**
     * Generates a list of information that a coordinate contains about a Game
     * Object
     * 
     * @param coordinate
     *        Coordinate that is being asked for
     * @return List of Strings that contain information about the coordinate.
     *         Null if there is no object at coordinate
     */
    public List<String> generateObjectInfo (Coordinate coordinate) {
        return myActiveStage.getGrid().generateObjectInfo(coordinate);
    }

    /**
     * Gets a list of actions that a unit at a coordinate can perform. Null if
     * there is no unit.
     * 
     * @param coordinate
     *        Coordinate that is being asked for
     * @return List of Strings that contain the action names
     */
    @SuppressWarnings("unchecked")
    public List<String> getActions (Coordinate coordinate) {
        List<String> myActiveActionNames = myActiveStage.getGrid()
                .generateActionList(coordinate);
        List<Action> editorActions = (List<Action>) myEditorData.getTableModel("ACTION");
        List<Action> newActiveActions = new ArrayList<>();

        if (myActiveActionNames != null) {
            for (String action : myActiveActionNames) {
                newActiveActions.add(editorActions.get(editorActions.indexOf(action)));
            }
            myActiveActions = newActiveActions;
            return myActiveActionNames;
        }
        return null;
    }

    /**
     * Sets the tiles that an action affects to active
     * 
     * @param unitCoordinate
     *        Coordinate where the action originates
     * @param actionID
     *        int that represents the index of the action in myActiveActions
     */
    public void beginAction (Coordinate unitCoordinate, int actionID) {
        myActiveStage.getGrid().setTilesInactive();
        if (myActiveActions.get(actionID).getName()
                .equals(MoveAction.MOVE_NAME)) {
            myActiveStage.getGrid().beginMove(unitCoordinate);
        }
        else if (myActiveActions.get(actionID).getName()
                .equals(WaitAction.WAIT_NAME)) {
            myActiveStage.getGrid().getUnit(unitCoordinate).setActive(false);
        }
        else {
            myActiveStage.getGrid().beginAction(unitCoordinate,
                                                myActiveActions.get(actionID).getActionRange());
        }
    }

    /**
     * Performs the selected action
     * 
     * @param unitCoordinate
     *        Coordinate where the action originates
     * @param actionCoordinate
     *        Coordinate where the action is targeting
     * @param actionID
     *        int that represents the index of the action in myActiveActions
     */
    public void doAction (Coordinate unitCoordinate,
                          Coordinate actionCoordinate, int actionID) {
        GameUnit initiator = myActiveStage.getGrid().getUnit(unitCoordinate);

        if (myActiveActions.get(actionID).getName()
                .equals(MoveAction.MOVE_NAME) && myActiveStage.getGrid().isActive(actionCoordinate)) {
            myActiveStage.getGrid().doMove(unitCoordinate, actionCoordinate);
            initiator.hasMoved();
        }
        else {
            GameUnit activeUnit = myActiveStage.getGrid().getUnit(
                                                                  unitCoordinate);
            GameUnit receiver = myActiveStage.getGrid().getUnit(
                                                                actionCoordinate);
            if (receiver != null && myActiveStage.getGrid().isActive(actionCoordinate)) {
                myActiveActions.get(actionID).doAction(activeUnit, receiver);
                initiator.setActive(false);
            }
        }

        myActiveStage.getGrid().setTilesInactive();
    }

    public void endTurn () {
        isTurnCompleted = true;
        myView.removeAll();
    }

    public String getWinningTeam () {
        return myActiveStage.getWinningTeam().getName();
    }

    public String getCurrentTeamName () {
        return myActiveStage.getTeam(myActiveTeam).getName();
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
