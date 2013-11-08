package stage;

import java.util.List;
import java.util.Map;
import combat.CombatAction;
import grid.GameUnit;
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

    public Stage () {
        run();
    }

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

    private void doCombat (GameUnit attacker, GameUnit defender, CombatAction action) {
        // netEffectiveness is a measurement of how effective an attacker is against a defender (0.0
        // - 1.0)
        double netEffectiveness = action.getNetEffectiveness(attacker, defender);

        applyCosts(attacker, action.getCosts());
        applyOutcomes(attacker, action.getAttackerOutcomes(), netEffectiveness);
        applyOutcomes(defender, action.getDefenderOutcomes(), netEffectiveness);

    }

    private void applyCosts (GameUnit unit, Map<String, Integer> costs) {
        for (String statAffected : costs.keySet()) {
            int oldStatValue = unit.getStats().getStat(statAffected);
            int newStatValue = (oldStatValue - costs.get(statAffected));

            unit.getStats().setStat(statAffected, newStatValue);
        }
    }

    private void applyOutcomes (GameUnit unit, Map<String, Integer> outcomes, double effectiveness) {
        for (String statAffected : outcomes.keySet()) {
            int oldStatValue = unit.getStats().getStat(statAffected);
            int newStatValue = (int) (oldStatValue + effectiveness * outcomes.get(statAffected));

            unit.getStats().setStat(statAffected, newStatValue);
        }
    }

}
