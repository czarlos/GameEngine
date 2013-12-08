package game;

import java.util.ArrayList;
import java.util.List;
import gameObject.GameUnit;
import gameObject.action.Action;
import grid.Coordinate;
import grid.Grid;
import grid.GridConstants;
import stage.Stage;
import team.Team;
import utils.UnitUtilities;
import controllers.GameManager;
import controllers.Manager;

/**
 * Second version of AI. Moves AI designated units towards closest enemies and uses a valid action on the enemy if possible
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
        doAIAction(AIUnits);
    }

    /**
     * Moves the AI units towards opponents
     * @param AIUnits List of GameUnits of AI units
     * @param opponents List of GameUnits of opponents
     */
    private void doAIMove (List<GameUnit> AIUnits, List<GameUnit> opponents) {
        for (GameUnit unit : AIUnits) {
            Coordinate unitCoordinate = myGrid.getObjectCoordinate(GridConstants.GAMEUNIT, unit);
            myGrid.beginMove(unitCoordinate);
            
            List<Coordinate> activeCoordinates = myGrid.getActiveTileCoordinates();
            int min =
                    UnitUtilities.calculateDistance(activeCoordinates.get(0), myGrid
                            .getObjectCoordinate(GridConstants.GAMEUNIT, opponents.get(0)));
            Coordinate end = null;
            for (Coordinate activeCoordinate : activeCoordinates) {
                for (GameUnit opponent : opponents) {
                    int current =
                            UnitUtilities.calculateDistance(activeCoordinate, myGrid
                                    .getObjectCoordinate(GridConstants.GAMEUNIT, opponent));
                    if (current <= min) {
                        min = current;
                        end = activeCoordinate;
                    }
                }
            }

            myGrid.doMove(unitCoordinate, end);
            myGrid.setAllTilesInactive();
        }
    }

    /**
     * Makes the AI units perform actions if possible
     * @param AIUnits List of GameUnits of AI units
     */
    private void doAIAction (List<GameUnit> AIUnits) {
        for (GameUnit unit : AIUnits) {
            Coordinate unitCoordinate = myGrid.getObjectCoordinate(GridConstants.GAMEUNIT, unit);
            List<String> unitActions = myManager.getActionNames(unitCoordinate);
            for (String unitAction : unitActions) {
                Action currentAction = ((GameManager) myManager).getAction(unitAction);
                myGrid.findActionRange(unitCoordinate, currentAction.getActionRange(),
                                       currentAction);
                List<Coordinate> activeCoordinates = myGrid.getActiveTileCoordinates();
                for (Coordinate activeCoordinate : activeCoordinates) {
                    if (currentAction.isValid(unit, myGrid.getObject(GridConstants.GAMEOBJECT,
                                                                     activeCoordinate))) {
                        System.out.println("AI actionName: "+currentAction.getName());
                        currentAction.doAction(unit, myGrid.getObject(GridConstants.GAMEOBJECT,
                                                                      activeCoordinate));
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
    }

    /**
     * Finds all opponents opposing the AI's team
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
