package controllers;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import stage.Team;
import view.player.PlayerView;
import game.AI2;
import game.AnimateAction;
import gameObject.Chest;
import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.InventoryObject;
import gameObject.action.Action;
import gameObject.action.ShopAction;
import gameObject.action.TradeAction;
import grid.Coordinate;
import grid.GridConstants;
import grid.Tile;


/**
 * The interface between the game playing frontend and the backend
 * 
 * @author Kevin, Leevi
 * 
 */
@JsonAutoDetect
public class GameManager extends Manager {
    private PlayerView myView;
    private boolean myGameOver = false;

    public GameManager () {
    }

    public GameManager (Manager m) {
        super(m);
    }

    public void setView (PlayerView view) {
        myView = view;
    }

    public void beginTurn () {
        if (myGameOver) { return; }
        clear();
        if (myPhaseCount == 0) {
            myView.showDialog(getPreStory());
        }

        myView.setTitle(getActiveTitle());
        setAllUnitsActive();
    }

    private void clear () {
        myActiveActions = new ArrayList<Action>();
    }

    public void doUntilHumanTurn () {
        if (conditionsMet()) {
            humanWin();
            return;
        }
        if (myGameOver) { return; }
        nextTurn();
        beginTurn();
        while (!teamIsHuman()) {
            doAITurn();
            if (conditionsMet()) {
                humanLoss();
                return;
            }

            nextTurn();
        }
    }

    public void humanWin () {
        myView.showDialog(getPostStory());
        myView.showDialog(getWinningTeam() + " won this stage!");
        if (!nextStage()) {
            myGameOver = true;
            myView.showDialog("Congratulations!You have completed the game!");
            myView.gameOver();
        }
    }

    public void humanLoss () {
        myGameOver = true;
        myView.showDialog("Game Over: AI Won...");
        myView.gameOver();
    }

    /**
     * Loops through all of the game units in the current team (whose turn it
     * is) and sets all of the units to active.
     * 
     * @param currentTeam
     */
    public void nextTurn () {
        setAllUnitsInactive();
        myActiveStage.getGrid().setAllTilesInactive();

        myPhaseCount++;
        myActiveStage.setPhaseCount(myPhaseCount);
        myActiveTeam = myPhaseCount % myActiveStage.getNumberOfTeams();
    }

    public void setAllUnitsInactive () {
        List<GameUnit> units = myActiveStage.getTeamUnits(getActiveTeamName());
        for (GameUnit unit : units) {
            unit.setActive(false);
        }
    }

    public void setAllUnitsActive () {
        List<GameUnit> units2 = myActiveStage.getTeamUnits(getActiveTeamName());
        for (GameUnit unit : units2) {
            unit.setActive(true);
        }
    }

    /**
     * Makes a new AI and calls the AI doTurn method to execute AI
     */
    public void doAITurn () {
        AI2 ai = new AI2(myActiveStage.getTeam(myActiveTeam), myActiveStage, this);
        ai.doTurn();
    }

    @JsonIgnore
    private String getActiveTeamName () {
        return myActiveStage.getTeam(myActiveTeam).getName();
    }

    @JsonIgnore
    private String getActiveTitle () {
        return getActiveTeamName() + " - " + myActiveStage.getName() + " - " + myGameName;
    }

    public boolean nextStage () {
        myPhaseCount = 0;
        int index = myStages.indexOf(myActiveStage);

        if (index < myStages.size() - 1) {
            setActiveStage(index + 1);
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

    private void setActiveActions (Coordinate coordinate) {
        List<String> myActiveActionNames = getActionNames(coordinate);
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
            initiator.setTotalStats(((Tile) myActiveStage.getGrid().getObject(GridConstants.TILE,
                                                                              unitCoordinate))
                    .getStats());
        }
        else {
            GameObject receiver =
                    myActiveStage.getGrid().getObject(GridConstants.GAMEOBJECT, actionCoordinate);
            if (receiver instanceof GameUnit) {
                receiver =
                        myActiveStage.getGrid().getObject(GridConstants.GAMEUNIT, actionCoordinate);
            }
            if (receiver != null &&
                myActiveStage.getGrid().isActive(GridConstants.TILE, actionCoordinate)) {
                new AnimateAction(initiator.getImagePath(), receiver.getImagePath());
                activeAction.doAction(initiator, receiver);
                endAction(unitCoordinate, actionCoordinate, initiator, receiver);
            }
        }
        myActiveStage.getGrid().setAllTilesInactive();
    }

    public void endAction (Coordinate unitCoordinate,
                           Coordinate actionCoordinate,
                           GameUnit initiator,
                           GameObject receiver) {
        initiator.setActive(false);
        if (initiator.getTotalStat("health") == 0) {
            myActiveStage.getGrid().removeObject(unitCoordinate);
        }
        if (receiver instanceof Chest) {
            if (((InventoryObject) receiver).isEmpty()) {
                myActiveStage.getGrid().removeObject(actionCoordinate);
            }
        }
        if (receiver instanceof GameUnit) {
            receiver = myActiveStage.getGrid().getObject(GridConstants.GAMEUNIT, actionCoordinate);
            if (((GameUnit) receiver).getTotalStat("health") == 0) {
                myActiveStage.getGrid().removeObject(actionCoordinate);
            }
        }
    }

    @JsonIgnore
    public String getWinningTeam () {
        Team winningTeam = myActiveStage.getWinningTeam();
        if (winningTeam == null) { return ""; }
        return winningTeam.getName();
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
