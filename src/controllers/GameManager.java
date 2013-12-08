package controllers;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import stage.Stage;
import team.Team;
import view.player.PlayerView;
import game.AI;
import game.AI2;
import gameObject.Chest;
import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.action.Action;
import gameObject.action.ShopAction;
import gameObject.action.TradeAction;
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
        if (myPhaseCount == 0) {
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
        List<GameUnit> units = myActiveStage.getTeamUnits(getActiveTeamName());
        for (GameUnit unit : units) {
            unit.setActive(false);
        }

        isTurnCompleted = false;
        myPhaseCount++;
        myActiveTeam = myPhaseCount % myActiveStage.getNumberOfTeams();
        String teamName = getActiveTeamName();
        List<GameUnit> units2 = myActiveStage.getTeamUnits(teamName);

        for (GameUnit unit : units2) {
            unit.setActive(true);
        }
    }
    
    public void doAITurn () {
        AI ai = new AI(myActiveStage.getTeam(myActiveTeam), myActiveStage, this);
        ai.doTurn();
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
        if (index != myStages.size()) { //there is at least one more stage
            setActiveStage(index+1); //index++ doesn't work here...
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

    public boolean isTurnCompleted () {
        return isTurnCompleted;
    }

    private void setActiveActions (Coordinate coordinate) {
        List<String> myActiveActionNames = getActions(coordinate);
        if (myActiveActionNames != null) {
            List<Action> newActiveActions = new ArrayList<>();

            for (String action : myActiveActionNames) {
                newActiveActions.add(getAction(action));
            }
            myActiveActions = newActiveActions;
        }
    }

    @SuppressWarnings("unchecked")
    public Action getAction (String actionName) {
        List<Action> editorActions = (List<Action>) myEditorData.get(GridConstants.ACTION);
        String[] actionNameSplit = actionName.split(" ");
        if (actionNameSplit[0].equals(GridConstants.TRADE)) { return new TradeAction(
                                                                                     actionNameSplit[1]); }
        if (actionNameSplit[0].equals(GridConstants.SHOP)) { return new ShopAction(
                                                                                   actionNameSplit[1]); }
        for (Action action : GridConstants.COREACTIONS) {
            if (action.getName().equals(actionName)) { return action; }
        }
        for (Action action : editorActions) {
            if (action.getName().equals(actionName)) { return action; }
        }
        return null;
    }

    /**
     * Sets the tiles that an action affects to active
     * 
     * @param unitCoordinate Coordinate where the action originates
     * @param actionID int that represents the index of the action in myActiveActions
     */
    public void beginAction (Coordinate unitCoordinate, int actionID) {
        GameUnit initiator =
                (GameUnit) myActiveStage.getGrid()
                        .getObject(GridConstants.GAMEUNIT, unitCoordinate);
        setActiveActions(unitCoordinate);
        myActiveStage.getGrid().setAllTilesInactive();
        Action activeAction = myActiveActions.get(actionID);
        if (activeAction.getName().equals(GridConstants.MOVE)) {
            myActiveStage.getGrid().beginMove(unitCoordinate);
        }
        else if (activeAction.getName().equals(GridConstants.WAIT)) {
            activeAction.doAction(initiator, null);
        }
        else {
            myActiveStage.getGrid().findActionRange(unitCoordinate, activeAction.getActionRange(),
                                                    activeAction);
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
        setActiveActions(unitCoordinate);
        Action activeAction = myActiveActions.get(actionID);
        if (activeAction.getName().equals(GridConstants.MOVE) &&
            myActiveStage.getGrid().isActive(GridConstants.TILE, actionCoordinate)) {
            myActiveStage.getGrid().doMove(unitCoordinate, actionCoordinate);
            initiator.hasMoved();
        }
        else {
            GameObject receiver =
                    myActiveStage.getGrid().getObject(GridConstants.GAMEOBJECT, actionCoordinate);
            if (receiver != null &&
                myActiveStage.getGrid().isActive(GridConstants.TILE, actionCoordinate)) {
                activeAction.doAction(initiator, receiver);
                initiator.setActive(false);
                if (initiator.getTotalStat("health") == 0) {
                    myActiveStage.getGrid().removeObject(GridConstants.GAMEOBJECT, unitCoordinate);
                }
                if (receiver instanceof Chest) {
                    if (((Chest) receiver).isEmpty()) {
                        myActiveStage.getGrid().removeObject(GridConstants.GAMEOBJECT, actionCoordinate);
                    }
                }
                if (receiver instanceof GameUnit) {
                    if (((GameUnit) receiver).getTotalStat("health") == 0) {
                        myActiveStage.getGrid().removeObject(GridConstants.GAMEOBJECT,
                                                             actionCoordinate);
                    }
                }
            }

        }
        myActiveStage.getGrid().setAllTilesInactive();
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
