package controllers;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import team.Team;
import view.player.PlayerView;
import game.AI;
import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.action.Action;
import grid.Coordinate;
import grid.GridConstants;


/**
 * 
 * @author kevinjian, leevi, whoever else
 * 
 */
@JsonAutoDetect
public class GameManager extends Manager {
    private PlayerView myView;

    public GameManager () {
    }

    public GameManager (Manager m) {
        super(m);
    }

    public void setView (PlayerView view) {
        myView = view;
    }

    public void beginTurn () {
        clear();
        if (myPhaseCount == 0){
            myView.showDialog(getPreStory());
        }
        if (conditionsMet()) {
            myView.showDialog(getPostStory());
            if (!nextStage()) { // final stage
                // win
                myView.setTitle(getActiveTeamName() + " won!!");
                return;
            }
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
        List<GameUnit> list = myActiveStage.getTeamUnits(getActiveTeamName());
        for (GameUnit unit : list) {
            unit.setActive(false);
        }

        isTurnCompleted = false;
        myPhaseCount++;
        myActiveTeam = myPhaseCount % myActiveStage.getNumberOfTeams();
        String teamName = getActiveTeamName();
        List<GameUnit> list2 = myActiveStage.getTeamUnits(teamName);

        for (GameUnit unit : list2) {
            unit.setActive(true);
        }
    }

    @JsonIgnore
    private String getActiveTeamName () {
        return myActiveStage.getTeamNames().get(myActiveTeam);
    }

    @JsonIgnore
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
        return myActiveStage.conditionsMet();
    }

    public boolean teamIsHuman () {
        return myActiveStage.getTeam(myActiveTeam).isHuman();
    }

    public void doAITurn () {
        // pass in gamemanager to AI because need moveOn command
        AI ai = new AI(myActiveStage.getTeam(myActiveTeam), myActiveStage);
        ai.doTurn();
        // ai.doTurn();
    }

    public boolean turnCompleted () {
        return isTurnCompleted;
    }
    
    @SuppressWarnings("unchecked")
    private Action getAction (String actionName) {
        List<Action> editorActions = (List<Action>) myEditorData.get(GridConstants.ACTION);

        // check first to see if it's one of the core actions so users can't override
        for (Action action : GridConstants.COREACTIONS) {
            if (action.getName().equals(actionName)) { return action; }
        }

        for (Action action : editorActions) {
            if (action.getName().equals(actionName)) { return action; }
        }
        return null;
    }

    private List<String> getInteractions (Coordinate coordinate) {
        List<String> interactions = new ArrayList<>();
        return interactions;
    }

    private void setActiveActions (Coordinate coordinate) {
        List<String> myActiveActionNames = getActions(coordinate);
        // TODO: fix AI action handling (pass in gameManager and then make a method to call to get
        // action from name

        if (myActiveActionNames != null) {
            List<Action> newActiveActions = new ArrayList<>();

            for (String action : myActiveActionNames) {
                newActiveActions.add(getAction(action));
            }
            myActiveActions = newActiveActions;
        }
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
        setActiveActions(unitCoordinate);
        myActiveStage.getGrid().setTilesInactive();

        if (myActiveActions.get(actionID).getName().equals(GridConstants.MOVE)) {
            myActiveStage.getGrid().beginMove(unitCoordinate);
        }
        else if (myActiveActions.get(actionID).getName().equals(GridConstants.WAIT)) {
            myActiveStage.getGrid().getObject(GridConstants.GAMEOBJECT, unitCoordinate)
                    .setActive(false);
        }
        else {
            myActiveStage.getGrid().beginAction(unitCoordinate,
                                                myActiveActions.get(actionID).getActionRange());
        }
    }

    /**
     * Performs the selected action
     * 
     * @param unitCoordinate Coordinate where the action originates
     * @param actionCoordinate Coordinate where the action is targeting
     * @param actionID int that represents the index of the action in myActiveActions
     */
    public void doAction (Coordinate unitCoordinate, Coordinate actionCoordinate, int actionID) {
        GameUnit initiator =
                (GameUnit) myActiveStage.getGrid()
                        .getObject(GridConstants.GAMEUNIT, unitCoordinate);
        Action activeAction = myActiveActions.get(actionID);

        if (activeAction.getName().equals(GridConstants.MOVE) &&
            myActiveStage.getGrid().isActive(actionCoordinate)) {
            myActiveStage.getGrid().doMove(unitCoordinate, actionCoordinate);
            initiator.hasMoved();
        }
        else {
            GameObject receiver =
                    myActiveStage.getGrid().getObject(GridConstants.GAMEOBJECT, actionCoordinate);
            if (receiver != null && myActiveStage.getGrid().isActive(actionCoordinate)) {
                activeAction.doAction(initiator, receiver);
                initiator.setActive(false);
            }
        }
        myActiveStage.getGrid().setTilesInactive();
    }

    public void endTurn () {
        isTurnCompleted = true;
        myView.removeAll();
    }

    @JsonIgnore
    public String getWinningTeam () {
        Team winningTeam = myActiveStage.getWinningTeam();
        if (winningTeam == null) { return ""; }
        return winningTeam.getName();
    }

    @JsonIgnore
    public String getCurrentTeamName () {
        return myActiveStage.getTeam(myActiveTeam).getName();
    }

    @JsonIgnore
    public String getPreStory () {
        return myActiveStage.getPreStory();
    }

    @JsonIgnore
    public String getPostStory () {
        return myActiveStage.getPostStory();
    }

    public boolean didHumanWin () {
        return myActiveStage.getWinningTeam().isHuman();
    }
}
