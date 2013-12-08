package game;

import gameObject.GameUnit;
import gameObject.action.Action;
import grid.Coordinate;
import grid.Grid;
import grid.GridConstants;
import grid.Tile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import controllers.GameManager;
import stage.Stage;
import team.Team;
import unit_ai.PathFinding;
import utils.UnitUtilities;


public class AI {

    private Team myTeam;
    private Grid myGrid;
    private Stage myStage;
    private GameManager myGM;

    public AI (Team team, Stage stage, GameManager gm) {
        myTeam = team;
        myGrid = stage.getGrid();
        myStage = stage;
        myGM = gm;
    }

    public void doTurn () {
        Set<GameUnit> opponentList = findAllEnemies();
        for (GameUnit unit : myStage.getTeamUnits(myTeam.getName())) {
            List<Tile> tileList = PathFinding.coordinatesToTiles(myGrid, unit);
            List<Tile> validTiles = removeInvalidTiles(tileList, unit, opponentList);
            PathFinding.addNeighbors(validTiles, myGrid);
            doAIMove(unit, opponentList);
        }
    }

    /**
     * Removes tiles from the possible tiles in a path that cannot be passed through,
     * thus avoiding all objects that the unit can't walk through.
     * 
     * @param tileList
     * @param unit
     * @param opponentList
     * @return
     */
    public List<Tile> removeInvalidTiles (List<Tile> tileList,
                                          GameUnit unit,
                                          Set<GameUnit> opponentList) {
        List<Tile> removalList = new ArrayList<Tile>();
        for (Tile tile : tileList) {
            Coordinate location = myGrid.getObjectCoordinate(GridConstants.TILE, tile);
            if (myGrid.getObject(GridConstants.GAMEOBJECT, location) != null &&
                !myGrid.getObject(GridConstants.GAMEOBJECT, location).equals(unit) &&
                !myGrid.getObject(GridConstants.GAMEOBJECT, location).isPassable(unit) &&
                !opponentList.contains(myGrid.getObject(GridConstants.GAMEOBJECT, location))) {
                removalList.add(tile);
            }
        }

        if (!removalList.isEmpty()) {
            for (Tile t : removalList) {
                if (myGrid.getObject(GridConstants.GAMEUNIT,
                                     myGrid.getObjectCoordinate(GridConstants.TILE, t)) instanceof GameUnit) {
                    continue;
                }
                else {
                    tileList.remove(t);
                }
            }
        }
        return tileList;
    }

    /**
     * Sends enemy units to attack your units, uses dijkstra's path finding algorithm
     * from the PathFinding class to find the shortest path and traverses as far
     * as the unit can move on that path, when it encounters an enemy unit it
     * attacks that unit with a randomly chosen attack from a weapon that it contains.
     * Attacks when it's randomly chosen attack is in range of an enemy.
     * 
     * @param unit
     *        - The game unit which is being moved by the AI
     * @param allEnemies
     *        - A list of all of the enemy units
     */
    public void doAIMove (GameUnit unit, Set<GameUnit> allEnemies) {

        Coordinate other = findClosestOpponent(unit, allEnemies);
        Tile start =
                (Tile) myGrid.getObject(GridConstants.TILE,
                                        myGrid.getObjectCoordinate(GridConstants.GAMEUNIT, unit));
        Tile end = (Tile) myGrid.getObject(GridConstants.TILE, other);

        Random r = new Random();
        int rand = r.nextInt(unit.getActionNames().size());
        String randomAction = unit.getActionNames().get(rand);

        Action currentAction = ((GameManager) myGM).getAction(randomAction);

        if (unit.getStat("movement") > 0) {
            if (UnitUtilities
                    .calculateLength(myGrid.getObjectCoordinate(GridConstants.TILE, start),
                                     myGrid.getObjectCoordinate(GridConstants.TILE, end)) <= currentAction
                    .getActionRange()) {
                
                new AnimateAction(unit.getImagePath(), myGrid.getObject(GridConstants.GAMEUNIT,
                                                                        other).getImagePath());
                
                currentAction.doAction(unit, myGrid.getObject(GridConstants.GAMEOBJECT,
                                                              other));
                ((GameManager) myGM)
                        .endAction(myGrid.getObjectCoordinate(GridConstants.GAMEUNIT, unit), other,
                                   unit, myGrid
                                           .getObject(GridConstants.GAMEOBJECT, other));
                myGrid.setAllTilesInactive();

            }
            else {
                PathFinding.autoMove(start, end, unit, myGrid);
            }
        }
    }

    /**
     * Finds all units for a player (or AI) other than your own and adds them to
     * a list of units which contains all of the opponents of that affiliation.
     * 
     * @param thisAffiliation
     * @return
     */
    public Set<GameUnit> findAllEnemies () {
        Set<GameUnit> opponentList = new HashSet<GameUnit>();

        for (int i = 0; i < myStage.getNumberOfTeams(); i++) {
            Team team = myStage.getTeam(i);
            if (!team.getName().equals(myTeam.getName()))
                opponentList.addAll(myStage.getTeamUnits(team.getName()));
        }
        // System.out.println(opponentList);
        return opponentList;
    }

    /**
     * This unit searches for the closest unit on the grid
     * 
     * @param opponents
     *        - List of opponents
     * @return
     */
    public Coordinate findClosestOpponent (GameUnit unit,
                                           Set<GameUnit> opponents) {
        GameUnit closest = null;
        double distance = 0;
        for (GameUnit opponent : opponents) {
            if (closest == null) {
                closest = opponent;
                distance =
                        UnitUtilities
                                .calculateLength(
                                                 myGrid.getObjectCoordinate(GridConstants.GAMEUNIT,
                                                                            unit),
                                                 myGrid.getObjectCoordinate(GridConstants.GAMEUNIT,
                                                                            opponent));
            }
            else if (UnitUtilities
                    .calculateLength(
                                     myGrid.getObjectCoordinate(GridConstants.GAMEUNIT, unit),
                                     myGrid.getObjectCoordinate(GridConstants.GAMEUNIT, opponent)) < distance) {
                closest = opponent;
                distance =
                        UnitUtilities
                                .calculateLength(
                                                 myGrid.getObjectCoordinate(GridConstants.GAMEUNIT,
                                                                            unit),
                                                 myGrid.getObjectCoordinate(GridConstants.GAMEUNIT,
                                                                            opponent));
            }
        }

        return myGrid.getObjectCoordinate(GridConstants.GAMEUNIT, closest);
    }

    /**
     * Makes a list of units sorted from closest to farthest.
     * 
     * @param unit
     *        - The active unit
     * @param otherUnits
     *        - All of the enemy units.
     * @return
     */
    public List<GameUnit> makeSortedUnitList (GameUnit unit,
                                              List<GameUnit> otherUnits) {
        Map<Double, GameUnit> unitDistance = new TreeMap<Double, GameUnit>();
        List<GameUnit> priorityUnitList = new ArrayList<GameUnit>();

        for (GameUnit other : otherUnits) {
            double distance =
                    UnitUtilities
                            .calculateLength(
                                             myGrid.getObjectCoordinate(GridConstants.GAMEUNIT,
                                                                        unit),
                                             myGrid.getObjectCoordinate(GridConstants.GAMEUNIT,
                                                                        other));
            unitDistance.put(distance, other);
        }
        for (Double distance : unitDistance.keySet()) {
            priorityUnitList.add(unitDistance.get(distance));
        }
        return priorityUnitList;
    }
}
