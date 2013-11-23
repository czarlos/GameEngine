package stage;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import team.Team;
import unit_ai.PathFinding;
import utils.UnitUtilities;
import view.canvas.GridMouseListener;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import gameObject.GameUnit;
import gameObject.action.CombatAction;
import grid.Coordinate;
import grid.Grid;
import grid.Tile;


/**
 * Stage is responsible for managing how turns are distributed and progressing
 * the game when it is won. The turns progress when the player indicates they are
 * done and when the AI deactivates all of their units.
 * 
 * @author Andy Bradshaw
 * @author carlosreyes
 * @author Leevi
 * 
 */
@JsonAutoDetect
public class Stage implements GridMouseListener {

    private Grid myGrid;
    @JsonProperty
    private WinCondition myWinCondition;
    private String myName;
    private String preText;
    private String postText;
    private List<Team> myTeamList;

    // only for use by deserializer
    public Stage () {
    }

    public Stage (int x, int y, int tileID, String name) {
        myGrid = new Grid(x, y, tileID);
        myWinCondition = new WinCondition();
        myName = name;
    }

    /*
     * Returns true if unit was added to team, false if teamID was invalid
     * Note this logic works best if editor has a "team editor" tab that 
     * allows users to make teams and assign units to those teams.
     */
    
    public boolean addUnitToTeam(int teamID, GameUnit gu){
        if(teamID< myTeamList.size()){
            myTeamList.get(teamID).addGameUnit(gu);
            gu.setAffiliation(myTeamList.get(teamID).getName());
            return true;
        }
        return false;
    }
    
    public void addTeam(String teamName){
        myTeamList.add(new Team(teamName));
    }
    
    /*
     * Carlos's Code starts here. Don't delete!
     */

    /**
     * Runs the game, only stopping when the win condition has been satisfied,
     * continually loops through the players in the game, moving to the next player
     * when the spacebar is pressed, or if the player is an AI if all of the units
     * have been set to inactive.
     * 
     * @param event - Listens for spacebar
     */
    public void doInGame (KeyEvent event) {
        while (!myWinCondition.isFulfilled(this)) {

            for (int i = 0; i < myTeamList.size(); i++) {
                // TODO: Decrement the #turn counter on the units, or set them all to active
                if (myTeamList.get(i).isHuman()) {
                    boolean flag = true;
                    while (flag) {
                        if (event.getKeyCode() == KeyEvent.VK_SPACE) {
                            flag = false;
                        }
                        else {
                            // TODO: This is where a users turn happens
                        }
                    }
                }
                else {
                    List<GameUnit> opponentList = findAllEnemies(i);
                    for (GameUnit unit : myTeamList.get(i).getGameUnits()) {
                        doAIMove(unit, opponentList);
                    }
                }
            }

        }
    }

    /**
     * Sends enemy units to attack your units, uses the pathfinding algorithm from
     * the PathFinding class to find the shortest path and traverses as far as the unit can
     * move on that path, when it encounters an enemy unit it attacks that unit with a randomly
     * chosen attack from its active weapon.
     * 
     * @param unit - The game unit which is being moved by the AI
     * @param allEnemies - A list of all of the enemy units
     */
    public void doAIMove (GameUnit unit, List<GameUnit> allEnemies) {
        PathFinding.coordinatesToTiles(myGrid, unit);
        GameUnit other = findClosestOpponent(unit, allEnemies);

        Tile start = myGrid.getTile(myGrid.getUnitCoordinate(unit));
        Tile end = myGrid.getTile(myGrid.getUnitCoordinate(other));

        if (UnitUtilities.calculateLength(start.getCoordinate(), end.getCoordinate()) == 1) {
            Random r = new Random();
            int rand = r.nextInt(unit.getActiveWeapon().getActionList().size());
            CombatAction randomAction = unit.getActiveWeapon().getActionList().get(rand);
            String activeWeapon = unit.getActiveWeapon().toString();
            unit.attack(other, activeWeapon, randomAction);
        }
        else {
            PathFinding.autoMove(start, end, unit, myGrid);
        }

    }

    /**
     * Finds all units for a player (or AI) other than your own and adds them to a list
     * of units which contains all of the opponents of that affiliation.
     * 
     * @param teamList
     * @param thisAffiliation
     * @return
     */
    public List<GameUnit> findAllEnemies (int thisAffiliation) {
        List<GameUnit> opponentList = new ArrayList<GameUnit>();

        for (Team team : myTeamList) {
            if (!team.isHuman()) {
                opponentList.addAll(team.getGameUnits());
            }

        }
        return opponentList;
    }

    /**
     * This unit searches for the closest unit on the grid
     * 
     * @param opponents - List of opponents
     * @return
     */
      public GameUnit findClosestOpponent (GameUnit unit, List<GameUnit> opponents) {
          GameUnit closest = null;
          double distance = 0;
          for (GameUnit opponent : opponents) {
              if (closest == null) {
                  closest = opponent;
                  distance =
                          UnitUtilities.calculateLength(myGrid.getUnitCoordinate(unit),
                                                        myGrid.getUnitCoordinate(opponent));
              }
              else if (UnitUtilities.calculateLength(myGrid.getUnitCoordinate(unit),
                                                     myGrid.getUnitCoordinate(opponent)) < distance) {
                  closest = opponent;
                  distance =
                          UnitUtilities.calculateLength(myGrid.getUnitCoordinate(unit),
                                                        myGrid.getUnitCoordinate(opponent));
              }
          }
        
          return closest;
    }
    
      /**
       * One unit goes to another units side.
       * @param unit - The unit to move
       * @param opponenent - The unit to move to
       */
    public void goToOpponent (GameUnit unit, GameUnit opponenent) {
        Coordinate myUnitPosition = myGrid.getUnitCoordinate(unit);
        Coordinate myOpponentPosition = myGrid.getUnitCoordinate(opponenent);
        myGrid.placeObject(myOpponentPosition, unit);
        
    }
      
    private void doPlayerMove (int affliation) {
        // TODO wait until all units are done
        for (GameUnit unit : myTeamList.get(affliation).getGameUnits()) {
            while (unit.getActiveStatus())
                System.out.println("WE WAITIN UNTIL ALL UNITS ARE NOT ACTIVE");
        }
    }

    /**
     * The AI will move to your unit's positions and attack them.
     */
    // public void doAIMove (int aiTeamIndex, int otherTeamIndex) {
    //
    // moveToOpponents(aiTeamIndex, otherTeamIndex);
    // }

    /**
     * Moves all units possible from one team to opponents to another team.
     * Used to assist the AI in attacking players on another team.
     * Works by looking through all of the AIs units and assigning them to
     * attack the opponents units, starting with the closest ones. If there are
     * more AI units than opponent units, the extra AI units attack the closest
     * opponent units.
     * 
     * @param currentTurnAffiliate
     */
    private void moveToOpponents (int aiTeamIndex, int otherTeamIndex) {
        int counter = 0;
        for (GameUnit unit : myTeamList.get(aiTeamIndex).getGameUnits()) {
            if (counter > myTeamList.get(otherTeamIndex).getGameUnits().size()) {
                counter = 0;
            }
            List<GameUnit> opponentList =
                    makeSortedUnitList(unit, myTeamList.get(otherTeamIndex).getGameUnits());
            myGrid.doMove(myGrid.getUnitCoordinate(unit), myGrid.getUnitCoordinate(opponentList.get(0)));
            counter++;
        }
    }

    /**
     * A different AI option where the AI units all simply move to the closest
     * opponent's unit to their position.
     */
    public void moveToClosestOpponent (int aiTeamIndex, int otherTeamIndex) {
        int counter = 0;
        for (GameUnit unit : myTeamList.get(aiTeamIndex).getGameUnits()) {
            if (counter > myTeamList.get(otherTeamIndex).getGameUnits().size()) {
                counter = 0;
            }
            List<GameUnit> opponentList =
                    makeSortedUnitList(unit, myTeamList.get(otherTeamIndex).getGameUnits());
            myGrid.doMove(myGrid.getUnitCoordinate(unit), myGrid.getUnitCoordinate(opponentList.get(0)));
            counter++;
        }
    }

    /**
     * Makes a list of units sorted from closest to farthest.
     * 
     * @param unit - The active unit
     * @param otherUnits - All of the enemy units.
     * @return
     */
    public List<GameUnit> makeSortedUnitList (GameUnit unit, List<GameUnit> otherUnits) {
        Map<Double, GameUnit> unitDistance = new TreeMap<Double, GameUnit>();
        List<GameUnit> priorityUnitList = new ArrayList<GameUnit>();

        for (GameUnit other : otherUnits) {
            double distance =
                    UnitUtilities.calculateLength(myGrid.getUnitCoordinate(unit), myGrid.getUnitCoordinate(other));
            unitDistance.put(distance, other);
        }
        for (Double distance : unitDistance.keySet()) {
            priorityUnitList.add(unitDistance.get(distance));
        }
        return priorityUnitList;
    }

    /**
     * Loops through all of the game units in the current team (whose turn it is)
     * and sets all of the units to active.
     * @param currentTeam
     */
    private void changeTurns (Team currentTeam) {
        for (GameUnit unit : currentTeam.getGameUnits()) {
            unit.setActive(true);
        }
    }

    /**
     * doCombat executes combat between two units.
     * 
     * @param attacker - GameUnit that performs the action
     * @param defender - GameUnit that action is being performed on
     * @param action - Action that is being executed
     */
    private void doCombat (GameUnit attacker, GameUnit defender, CombatAction action) {
        // TODO: Figure out how much of combat is determined outside of stage
    }

    public Grid getGrid () {
        return myGrid;
    }

    public void setName (String name) {
        myName = name;
    }

    public String getName () {
        return myName;
    }

    public void setWinCondition (WinCondition wc) {
        myWinCondition = wc;
    }

    public void addCondition (Condition c) {
        myWinCondition.addCondition(c);
    }

    @JsonIgnore
    public List<String> getTeamNames () {
        List<String> ret = new ArrayList<String>();
        for (Team t : myTeamList) {
            ret.add(t.getName());
        }

        return ret;
    }

    public List<GameUnit> getTeamUnits (int ID) {
        if (ID < myTeamList.size()) {
            return myTeamList.get(ID).getGameUnits();
        }

        return null;
    }

    public void setPreStory (String pre) {
        preText = pre;
    }

    public void setPostStory (String post) {
        postText = post;
    }

    public String getPreStory () {
        return preText;
    }

    public String getPostStory () {
        return postText;
    }

    @Override
    public void gridClicked (Coordinate c) {
        System.out.println(c);
    }
}
