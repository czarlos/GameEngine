package stage;

import java.util.List;
import java.util.Map;
import action.CombatAction;
import gameObject.GameUnit;
import gameObject.Stat;
import grid.Grid;


/**
 * @author Andy Bradshaw
 * 
 */
public class Stage {

    private Grid myGrid;
    // Affiliation subject to change (int/string/?)
    private List<String> myAffiliateList;
    private WinCondition myWinCondition;
    private Stat myMasterStats;

    public Stage () {
        run();
    }

    /**
     * 
     */
    public void run () {
        while (!myWinCondition.isWinConditionMet()) {
            for (String affiliate : myAffiliateList) {
                // Do Player Moves
                doPlayerMove();
                // Do AI Moves
                doAIMove();
            }
        }
    }

    private void doPlayerMove () {
        // TODO Add logic for movement during a players turn

    }

    private void doAIMove () {
        // TODO Add logic for AI movement

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
        applyOutcomes(attacker, action.getAttackerOutcomes(), netEffectiveness);
        applyOutcomes(defender, action.getDefenderOutcomes(), netEffectiveness);

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

}
