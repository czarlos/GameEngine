package action;

import gameObject.GameUnit;
import grid.Coordinate;
import java.util.List;
import java.util.Map;


public class CombatAction {
    private Map<String, Integer> myAttackerStatsAndWeights;
    private Map<String, Integer> myDefenderStatsAndWeights;
    private Map<String, Integer> myCosts;
    private Map<String, Integer> myAttackerOutcomes;
    private Map<String, Integer> myDefenderOutcomes;
    private List<Coordinate> myAOE;

    public CombatAction (Map<String, Integer> offensiveStats,
                         Map<String, Integer> defensiveStats,
                         Map<String, Integer> costs,
                         Map<String, Integer> attackerOutcomes,
                         Map<String, Integer> defenderOutcomes,
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

        for (String statName : myAttackerStatsAndWeights.keySet()) {
            offensiveStatSum +=
                    attacker.getStats().getStatValue(statName) *
                            myAttackerStatsAndWeights.get(statName);
        }

        for (String statName : myDefenderStatsAndWeights.keySet()) {
            defensiveStatSum +=
                    defender.getStats().getStatValue(statName) *
                            myDefenderStatsAndWeights.get(statName);
        }

        // Creates a normalized (0.0 - 1.0) output based on max possible
        // difference in favor of attacker
        netStat =
                ((offensiveStatSum - defensiveStatSum) >= 0 ? (offensiveStatSum - defensiveStatSum)
                                                           : 0) / (offensiveStatSum);

        return netStat;
    }

    public Map<String, Integer> getAttackerOutcomes () {
        return myAttackerOutcomes;
    }

    public Map<String, Integer> getDefenderOutcomes () {
        return myDefenderOutcomes;
    }

    public Map<String, Integer> getCosts () {
        return myCosts;
    }

    public List<Coordinate> getAOE () {
        return myAOE;
    }
}
