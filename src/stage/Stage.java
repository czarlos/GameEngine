package stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;

import action.CombatAction;
import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.Stat;
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
        while (!myWinCondition.hasWon()) {
            for (int i : myAffiliateList) { // for each affiliation
                changeTurns(i);             // set those affiliations' units to active
                if (myCurrUnitList == null) // if there are no units skip that affiliation's turn
                    continue;
                if (myCurrUnitList.get(0).isControllable())
                    doPlayerMove();
                else doAIMove();
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

    private void doAIMove () {

    }
    
    /**
     * Moves all units possible from one team to opponents to another team.
     * Used to assist the AI in attacking players on another team.
     * @param currentTurnAffiliate
     */
    private void moveToOpponents() {
        int aiTeamIndex = 1;
        int otherTeamIndex = 0;
        for (GameUnit unit : myTeamUnitList.get(aiTeamIndex)) {
        	int otherTeamSize = myTeamUnitList.get(otherTeamIndex).size();
        	Random rand = new Random();
        	int randomNum = rand.nextInt((otherTeamSize - 0) + 1) + 0;
        	GameUnit opponent = myTeamUnitList.get(otherTeamIndex).get(randomNum);
        	unit.snapToOpponent(opponent);
        	
        }
    }
    

    private void changeTurns (Integer currentTurnAffiliate) { // we are just going to be looping
                                                              // through affiliations and setting
                                                              // units to active
        for (GameUnit unit : myGrid.getGameUnits().keySet()) {
            if (currentTurnAffiliate == unit.getAffiliation()) {
                unit.setActive(true);
                myCurrUnitList.add(unit);
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
        // netEffectiveness is a measurement of how effective an attacker is against a defender (0.0
        // - 1.0)
        double netEffectiveness = action.getNetEffectiveness(attacker, defender);

        applyCosts(attacker, action.getCosts());
        applyOutcomes(attacker, action.getAttackerOutcomesMap(), netEffectiveness);
        applyOutcomes(defender, action.getDefenderOutcomesMap(), netEffectiveness);

    }

    /**
     * applyCosts is a way of making a unit have a cost for an action.
     * This is a way of having fixed outcomes that aren't affected by
     * stat differences (effectiveness).
     * 
     * (e.g. Mana cost for using a spell, Health cost for reckless action)
     * 
     * @param unit - GameUnit where stats are being edited
     * @param costs - Map of which stats are affected and by how much
     */
    private void applyCosts (GameUnit unit, Map<String, Integer> costs) {
        for (String statAffected : costs.keySet()) {
            int oldStatValue = unit.getStats().getStatValue(statAffected);
            int newStatValue = (oldStatValue - costs.get(statAffected));

            unit.getStats().setStatValue(statAffected, newStatValue);
        }
    }

    /**
     * applyOutcomes edits a units stats based on user specified
     * stats and weights. These outcomes are affected by stat differences
     * between units (effectiveness).
     * 
     * (e.g. Unit loses Health and Mana from getting hit by spell)
     * 
     * @param unit - GameUnit where stats are being edited
     * @param outcomes - Map of which stats are affected and by how much
     * @param effectiveness - A measurement of how much of an outcome should occur
     */
    private void applyOutcomes (GameUnit unit, Map<String, Integer> outcomes, double effectiveness) {
        for (String statAffected : outcomes.keySet()) {
            int oldStatValue = unit.getStats().getStatValue(statAffected);
            int newStatValue = (int) (oldStatValue + effectiveness * outcomes.get(statAffected));

            unit.getStats().setStatValue(statAffected, newStatValue);
        }
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
