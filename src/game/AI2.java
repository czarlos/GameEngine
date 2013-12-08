package game;

import java.util.ArrayList;
import java.util.List;
import gameObject.GameUnit;
import grid.Coordinate;
import grid.Grid;
import grid.GridConstants;
import stage.Stage;
import team.Team;
import utils.UnitUtilities;
import controllers.Manager;

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
     * Goes through all AI units, makes each one move to the closest enemy
     */
    public void doTurn() {
        List<GameUnit> opponents = findAllEnemies();
        if (opponents.isEmpty()) { return; }
        for (GameUnit unit : myStage.getTeamUnits(myTeam.getName())) {
            Coordinate unitCoordinate = myGrid.getObjectCoordinate(GridConstants.GAMEUNIT, unit);            
            myGrid.beginMove(unitCoordinate);
            List<Coordinate> activeCoordinates = myGrid.getActiveTileCoordinates();
            int min = UnitUtilities.calculateDistance(activeCoordinates.get(0), myGrid.getObjectCoordinate(GridConstants.GAMEUNIT, opponents.get(0)));
            Coordinate end = null;
            for (Coordinate c : activeCoordinates) {
                for (GameUnit opponent : opponents) {
                    int current = UnitUtilities.calculateDistance(c, myGrid.getObjectCoordinate(GridConstants.GAMEUNIT, opponent));
                    if (current <= min) {
                        min = current;
                        end = c;
                    }
                }
            }
            
            System.out.println("AI2 end: "+end.getX()+", "+end.getY());
            myGrid.doMove(unitCoordinate, end);
            myGrid.setAllTilesInactive();
            //TODO: doAction as well
        }       
    }

    private List<GameUnit> findAllEnemies() {
        List<GameUnit> opponents = new ArrayList<>();
        for (Team team: myStage.getTeams()) {
            if (!myTeam.getName().equals(team.getName())) {
                opponents.addAll(myStage.getTeamUnits(team.getName()));
            }
        }
        return opponents;
    }
}