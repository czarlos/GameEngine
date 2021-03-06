package game;

import java.util.ArrayList;
import java.util.List;
import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.action.Action;
import grid.Coordinate;
import grid.Grid;
import grid.GridConstants;
import grid.Tile;
import stage.Stage;
import stage.Team;
import utils.UnitUtilities;
import controllers.GameManager;
import controllers.Manager;


/**
 * Second version of AI. Moves AI designated units towards closest enemies and uses a valid action
 * on the enemy if possible
 * 
 * @author kevinjian
 * 
 */
public class AI2 {

    private Team myTeam;
    private Grid myGrid;
    private Stage myStage;
    private Manager myManager;

    public AI2 (Team team, Stage stage, Manager manager) {
        myTeam = team;
        myManager = manager;
        myGrid = stage.getGrid();

        myStage = stage;
    }

    /**
     * Goes through all AI units, moves them towards enemies and performs actions
     */
    public void doTurn () {
        List<GameUnit> opponents = findAllOpponents();
        List<GameUnit> AIUnits = myStage.getTeamUnits(myTeam.getName());
        if (opponents.isEmpty()) { return; }
        doAIMove(AIUnits, opponents);
        for (GameUnit AIUnit : AIUnits) {
            doAIAction(AIUnit);
        }
    }

    /**
     * Moves the AI units towards opponents
     * 
     * @param AIUnits List of GameUnits of AI units
     * @param opponents List of GameUnits of opponents
     */
    private void doAIMove (List<GameUnit> AIUnits, List<GameUnit> opponents) {
        for (GameUnit unit : AIUnits) {
            Coordinate start = myGrid.getObjectCoordinate(GridConstants.GAMEUNIT, unit);
            myGrid.beginMove(start);
            List<Coordinate> activeCoordinates = myGrid.getActiveTileCoordinates();
            int min =
                    UnitUtilities.calculateDistance(start, myGrid
                            .getObjectCoordinate(GridConstants.GAMEUNIT, opponents.get(0)));
            Coordinate end = start;
            for (Coordinate activeCoordinate : activeCoordinates) {
                for (GameUnit opponent : opponents) {
                    int current =
                            UnitUtilities.calculateDistance(activeCoordinate, myGrid
                                    .getObjectCoordinate(GridConstants.GAMEUNIT, opponent));
                    if (current < min) {
                        min = current;
                        end = activeCoordinate;
                    }
                }
            }
            myGrid.doMove(start, end);
            unit.setTotalStats(((Tile) myGrid.getObject(GridConstants.TILE, end)).getStats());
            myGrid.setAllTilesInactive();
        }
    }

    /**
     * Makes the AI units perform actions if possible
     * 
     * @param AIUnits List of GameUnits of AI units
     */
    private void doAIAction (GameUnit unit) {
        Coordinate unitCoordinate = myGrid.getObjectCoordinate(GridConstants.GAMEUNIT, unit);
        List<String> unitActions = myManager.getActionNames(unitCoordinate);
        if (unitActions == null) return;
        for (String unitAction : unitActions) {
            Action currentAction = ((GameManager) myManager).getAction(unitAction);
            myGrid.findActionRange(unitCoordinate, currentAction.getActionRange(),
                                   currentAction);
            List<Coordinate> activeCoordinates = myGrid.getActiveTileCoordinates();
            for (Coordinate activeCoordinate : activeCoordinates) {
                GameObject receiver = myGrid.getObject(GridConstants.GAMEOBJECT, activeCoordinate);
                if (currentAction.isValid(unit, receiver)) {
                    if (receiver instanceof GameUnit) {
                        receiver = myGrid.getObject(GridConstants.GAMEUNIT, activeCoordinate);
                        if (unit.getAffiliation().equals(((GameUnit) receiver).getAffiliation())) {
                            continue;
                        }
                    }
                    new AnimateAction(unit.getImagePath(), myGrid
                            .getObject(GridConstants.GAMEOBJECT,
                                       activeCoordinate).getImagePath());
                    currentAction.doAction(unit, receiver);
                    ((GameManager) myManager)
                            .endAction(unitCoordinate, activeCoordinate, unit, myGrid
                                    .getObject(GridConstants.GAMEOBJECT, activeCoordinate));
                    myGrid.setAllTilesInactive();
                    return;
                }
            }
            myGrid.setAllTilesInactive();
        }
    }

    /**
     * Finds all opponents opposing the AI's team
     * 
     * @return List of GameUnits of opponents
     */
    private List<GameUnit> findAllOpponents () {
        List<GameUnit> opponents = new ArrayList<>();
        for (Team team : myStage.getTeams()) {
            if (!myTeam.getName().equals(team.getName())) {
                opponents.addAll(myStage.getTeamUnits(team.getName()));
            }
        }
        return opponents;
    }
}
