package gameObject.action;

import gameObject.GameUnit;
import gameObject.StatModifier;
import java.util.List;


public class CombatAction extends Action {
    private StatModifier myAttackerStatsAndWeights;
    private StatModifier myDefenderStatsAndWeights;

    public CombatAction (String name, StatModifier offensiveStats,
                         StatModifier defensiveStats, List<Outcome> initiatorOutcomes,
                         List<Outcome> receiverOutcomes) {
        super(name, initiatorOutcomes, receiverOutcomes);
        myAttackerStatsAndWeights = offensiveStats;
        myDefenderStatsAndWeights = defensiveStats;
    }

    private Double getNetEffectiveness (GameUnit attacker, GameUnit defender) {
        double offensiveStatSum = 0, defensiveStatSum = 0;
        double netStat = 0;

        for (String statName : myAttackerStatsAndWeights.getStatModifierMap().keySet()) {
            offensiveStatSum +=
                    attacker.getStats().getStatValue(statName) *
                            myAttackerStatsAndWeights.getStatModifier(statName);
        }

        for (String statName : myDefenderStatsAndWeights.getStatModifierMap().keySet()) {
            defensiveStatSum +=
                    defender.getStats().getStatValue(statName) *
                            myDefenderStatsAndWeights.getStatModifier(statName);
        }

        // Creates a normalized output based on max possible difference in favor
        // of attacker
        netStat =
                ((offensiveStatSum - defensiveStatSum) >= 0 ? (offensiveStatSum - defensiveStatSum)
                                                           : 0)
                        / (offensiveStatSum);

        return netStat;
    }

    /**
     * Executes an attack by applying outcomes to attacker and defender
     * 
     * @param attacker
     * @param defender
     */
    @Override
    public void execute (GameUnit attacker, GameUnit defender) {
        double effectiveness = getNetEffectiveness(attacker, defender);

        for (Outcome o : myInitiatorOutcomes) {
            o.applyOutcome(attacker, effectiveness);

        }

        for (Outcome o : myReceiverOutcomes) {
            o.applyOutcome(defender, effectiveness);
        }

    }

    public boolean isValidAction (GameUnit attacker, GameUnit defender) {
        double effectiveness = getNetEffectiveness(attacker, defender);

        for (Outcome o : myInitiatorOutcomes) {
            if (!o.checkValidOutcome(attacker, effectiveness)) { return false; }
        }

        return true;
    }

}
