package stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import utils.UnitUtilities;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import gameObject.CombatAction;
import gameObject.GameUnit;
import grid.Grid;


/**
 * @author Andy Bradshaw
 * 
 */
@JsonAutoDetect
public class Stage {

    private Grid myGrid;
    private List<Integer> myAffiliateList;
    @JsonProperty
    private WinCondition myWinCondition;
    private String myName;
    private List<GameUnit> myCurrUnitList;
    private String preText;
    private String postText;
    private List<List<GameUnit>> myTeamUnitList;

    // only for use by deserializer
    public Stage () {
    }

    public Stage (int x, int y, int tileID, String name) {
        myGrid = new Grid(x, y, tileID);
        myAffiliateList = new ArrayList<Integer>();
        myWinCondition = new WinCondition();
        myName = name;
        myCurrUnitList = new ArrayList<GameUnit>();
    }

    /**
     * 
     */
    public void run () {
        while (!myWinCondition.hasWon(myGrid)) {
            for (int i : myAffiliateList) { // for each affiliation
                changeTurns(i);             // set those affiliations' units to active
                if (myCurrUnitList == null) // if there are no units skip that affiliation's turn
                    continue;
                if (myCurrUnitList.get(0).isControllable())
                    doPlayerMove();
                else doAIMove(1, 0);
                myCurrUnitList.clear();
            }
        }
    }

    private void doPlayerMove () {
        // TODO wait until all units are done
        for (GameUnit unit : myCurrUnitList) {
            while (unit.getActiveStatus())
                System.out.println("WE WAITIN UNTIL ALL UNITS ARE NOT ACTIVE");
        }
    }

    /**
     * The AI will move to your unit's positions and attack them.
     */
    public void doAIMove (int aiTeamIndex, int otherTeamIndex) {

        moveToOpponents(aiTeamIndex, otherTeamIndex);
    }

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
        for (GameUnit unit : myTeamUnitList.get(aiTeamIndex)) {
            if (counter > myTeamUnitList.get(otherTeamIndex).size()) {
                counter = 0;
            }
            List<GameUnit> opponentList =
                    makeSortedUnitList(unit, myTeamUnitList.get(otherTeamIndex));
            unit.snapToOpponent(opponentList.get(0));
            counter++;
        }
    }

    /**
     * A different AI option where the AI units all simply move to the closest
     * opponent's unit to their position.
     */
    public void moveToClosestOpponent (int aiTeamIndex, int otherTeamIndex) {
        int counter = 0;
        for (GameUnit unit : myTeamUnitList.get(aiTeamIndex)) {
            if (counter > myTeamUnitList.get(otherTeamIndex).size()) {
                counter = 0;
            }
            List<GameUnit> opponentList =
                    makeSortedUnitList(unit, myTeamUnitList.get(otherTeamIndex));
            unit.snapToOpponent(opponentList.get(0));
            counter++;
        }
    }

    /**
     * Makes a list of units sorted from closest to furthest.
     * 
     * @param unit
     * @param otherUnits
     * @return
     */
    public List<GameUnit> makeSortedUnitList (GameUnit unit, List<GameUnit> otherUnits) {
        Map<Double, GameUnit> unitDistance = new TreeMap<Double, GameUnit>();
        List<GameUnit> priorityUnitList = new ArrayList<GameUnit>();

        for (GameUnit other : otherUnits) {
            double distance =
                    UnitUtilities.calculateLength(unit.getGridPosition(), other.getGridPosition());
            unitDistance.put(distance, other);
        }
        for (Double distance : unitDistance.keySet()) {
            priorityUnitList.add(unitDistance.get(distance));
        }
        return priorityUnitList;
    }

    private void changeTurns (Integer currentTurnAffiliate) { // we are just going to be looping
                                                              // through affiliations and setting
                                                              // units to active
        for (ArrayList<GameUnit> unitList : myGrid.getGameUnits()) {
            for (GameUnit unit : unitList) {
                if (currentTurnAffiliate == unit.getAffiliation()) {
                    unit.setActive(true);
                    myCurrUnitList.add(unit);
                }
            }
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

    public List<List<GameUnit>> getTeamUnitList () {
        return myTeamUnitList;
    }

    public void setTeamUnitList (List<List<GameUnit>> myTeamUnitList) {
        this.myTeamUnitList = myTeamUnitList;
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

    public List<Integer> getAffiliateList () {
        return myAffiliateList;
    }

    public void setAffiliateList (List<Integer> affiliates) {
        myAffiliateList = affiliates;
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

}
