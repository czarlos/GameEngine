package game;

import gameObject.GameUnit;
import gameObject.action.Action;
import grid.Coordinate;
import grid.Grid;
import grid.Tile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import stage.Stage;
import team.Team;
import unit_ai.PathFinding;
import utils.UnitUtilities;


public class AI {

    private Team myTeam;
    private Grid myGrid;
    private Stage myStage;

    public AI (Team team, Stage stage) {
        myTeam = team;
        myGrid = stage.getGrid();
        myStage = stage;
    }

    public void doTurn () {
        Set<GameUnit> opponentList = findAllEnemies();        
        for (GameUnit unit : myStage.getTeamUnits(myTeam.getName())) {
            PathFinding.addNeighbors(PathFinding.coordinatesToTiles(myGrid, unit), myGrid);
            // delay?
            doAIMove(unit, opponentList);
            // Sleep?
        }
    }

    /**
     * Sends enemy units to attack your units, uses the pathfinding algorithm
     * from the PathFinding class to find the shortest path and traverses as far
     * as the unit can move on that path, when it encounters an enemy unit it
     * attacks that unit with a randomly chosen attack from its active weapon.
     * 
     * @param unit
     *        - The game unit which is being moved by the AI
     * @param allEnemies
     *        - A list of all of the enemy units
     */
    public void doAIMove (GameUnit unit, Set<GameUnit> allEnemies) {
        Coordinate other = findClosestOpponent(unit, allEnemies);
        System.out.println(other.getX() + " x " + other.getY());
        Tile start = myGrid.getTile(myGrid.getUnitCoordinate(unit));
        Tile end = myGrid.getTile(other);
        if (UnitUtilities.calculateLength(myGrid.getTileCoordinate(start),
                                          myGrid.getTileCoordinate(end)) == 1) {
            /*
            Random r = new Random();
            int rand = r.nextInt(unit.getActions().size());
            String randomAction = unit.getActions().get(rand);
            String activeWeapon = unit.getActiveWeapon().toString();
        //    randomAction.doAction(unit, myGrid.getUnit(other));
            // unit.attack(myGrid.getUnit(other), activeWeapon, randomAction);
        
*/        }
        else {
//            System.out.println(start + " " + end);
            PathFinding.autoMove(start, end, unit, myGrid);
//            System.out.println("unit pos2: " + myGrid.getTile(myGrid.getUnitCoordinate(unit)) + "\n");
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
//        System.out.println(opponentList);
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
                distance = UnitUtilities.calculateLength(
                                                         myGrid.getUnitCoordinate(unit),
                                                         myGrid.getUnitCoordinate(opponent));
            }
            else if (UnitUtilities.calculateLength(
                                                   myGrid.getUnitCoordinate(unit),
                                                   myGrid.getUnitCoordinate(opponent)) < distance) {
                closest = opponent;
                distance = UnitUtilities.calculateLength(
                                                         myGrid.getUnitCoordinate(unit),
                                                         myGrid.getUnitCoordinate(opponent));
            }
        }

        return myGrid.getUnitCoordinate(closest);
    }

    /**
     * The AI will move to your unit's positions and attack them.
     */
    // public void doAIMove (int aiTeamIndex, int otherTeamIndex) {
    //
    // moveToOpponents(aiTeamIndex, otherTeamIndex);
    // }

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
            double distance = UnitUtilities.calculateLength(
                                                            myGrid.getUnitCoordinate(unit),
                                                            myGrid.getUnitCoordinate(other));
            unitDistance.put(distance, other);
        }
        for (Double distance : unitDistance.keySet()) {
            priorityUnitList.add(unitDistance.get(distance));
        }
        return priorityUnitList;
    }

}
