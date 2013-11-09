package action;

import grid.GameUnit;
import gameObject.StatModifier;
import grid.Coordinate;
import java.util.List;
import java.util.Map;


public class CombatAction {
    private StatModifier myAttackerStatsAndWeights;
    private StatModifier myDefenderStatsAndWeights;
    private Map<String, Integer> myCosts;
    private StatModifier myAttackerOutcomes;
    private StatModifier myDefenderOutcomes;
    private List<Coordinate> myAOE;

    public CombatAction (StatModifier offensiveStats,
                         StatModifier defensiveStats,
                         Map<String, Integer> costs,
                         StatModifier attackerOutcomes,
                         StatModifier defenderOutcomes,
                         List<Coordinate> range) {
        myAttackerStatsAndWeights = offensiveStats;
        myDefenderStatsAndWeights = defensiveStats;
        myCosts = costs;
        myAttackerOutcomes = attackerOutcomes;
        myDefenderOutcomes = defenderOutcomes;
        myAOE = range;
    }

    public Double getNetEffectiveness (GameUnit attacker, GameUnit defender) {
        int offensiveStatSum = 0, defensiveStatSum = 0;
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

        // Creates a normalized (0.0 - 1.0) output based on max possible
        // difference in favor of attacker
        netStat =
                ((offensiveStatSum - defensiveStatSum) >= 0 ? (offensiveStatSum - defensiveStatSum)
                                                           : 0) / (offensiveStatSum);

        return netStat;
    }

    /**
     * Executes this action, and lowers the health of the defender based
     * on how much damage was done.
     */
    public void execute (GameUnit attacker, GameUnit defender) {
        double effectiveness = getNetEffectiveness(attacker, defender);
        double damage = defender.getHealth() * effectiveness;
        defender.setHealth(defender.getHealth() - damage);
    }

    public Map<String, Integer> getAttackerOutcomesMap () {
        return myAttackerOutcomes.getStatModifierMap();
    }

    public Map<String, Integer> getDefenderOutcomesMap () {
        return myDefenderOutcomes.getStatModifierMap();
    }

    public Map<String, Integer> getCosts () {
        return myCosts;
    }

    public List<Coordinate> getAOE () {
        return myAOE;
    }

}
