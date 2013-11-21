package gameObject;

import grid.Coordinate;
import java.util.List;


public class CombatAction {
    private String myName;
    private StatModifier myAttackerStatsAndWeights;
    private StatModifier myDefenderStatsAndWeights;
    private List<Outcome> myAttackerOutcomes;
    private List<Outcome> myDefenderOutcomes;
    private List<Coordinate> myAOE;
    private boolean isAround;

    public CombatAction (String name, StatModifier offensiveStats,
                         StatModifier defensiveStats, List<Outcome> attackerOutcomes,
                         List<Outcome> defenderOutcomes, List<Coordinate> AOE, boolean around) {
        myName = name;
        myAttackerStatsAndWeights = offensiveStats;
        myDefenderStatsAndWeights = defensiveStats;
        myAttackerOutcomes = attackerOutcomes;
        myDefenderOutcomes = defenderOutcomes;
        myAOE = AOE;
        isAround = around;
    }

    public Double getNetEffectiveness (GameUnit attacker, GameUnit defender) {
        double offensiveStatSum = 0, defensiveStatSum = 0;
        double netStat = 0;

        for (String statName : myAttackerStatsAndWeights.getStatModifierMap()
                .keySet()) {
            offensiveStatSum += attacker.getStats().getStatValue(statName)
                                * myAttackerStatsAndWeights.getStatModifier(statName);
        }

        for (String statName : myDefenderStatsAndWeights.getStatModifierMap()
                .keySet()) {
            defensiveStatSum += defender.getStats().getStatValue(statName)
                                * myDefenderStatsAndWeights.getStatModifier(statName);
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
    public void execute (GameUnit attacker, GameUnit defender) {
        double effectiveness = getNetEffectiveness(attacker, defender);

        for (Outcome o : myAttackerOutcomes) {
            o.applyOutcome(attacker, effectiveness);
        }

        for (Outcome o : myDefenderOutcomes) {
            o.applyOutcome(defender, effectiveness);
        }

    }

    public boolean isValidAction (GameUnit attacker, GameUnit defender) {
        double effectiveness = getNetEffectiveness(attacker, defender);

        for (Outcome o : myAttackerOutcomes) {
            if (!o.checkVaildOutcome(attacker, effectiveness)) { return false; }
        }

        return true;
    }

    public String getName () {
        return myName;
    }

    public List<Coordinate> getAOE () {
        return myAOE;
    }

    public boolean isAround () {
        return isAround;
    }

}
