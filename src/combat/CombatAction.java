package combat;

import gameObject.GameUnit;
import grid.Coordinate;
import java.util.List;
import java.util.Map;


public class CombatAction {
    private Map<String, Integer> myOffensiveStatsAndWeights;
    private Map<String, Integer> myDefensiveStatsAndWeights;
    private List<Coordinate> myRange;

    public CombatAction (Map<String, Integer> offensiveStats,
                         Map<String, Integer> defensiveStats,
                         List<Coordinate> range) {
        myOffensiveStatsAndWeights = offensiveStats;
        myDefensiveStatsAndWeights = defensiveStats;
        myRange = range;
    }

    public Integer getNetStats (GameUnit attacker, GameUnit defender) {
        int offensiveStatSum = 0, defensiveStatSum = 0, netStat = 0;

        for (String statName : myOffensiveStatsAndWeights.keySet()) {
            offensiveStatSum +=
                    attacker.getStats().getStat(statName) *
                            myOffensiveStatsAndWeights.get(statName);
        }

        for (String statName : myDefensiveStatsAndWeights.keySet()) {
            defensiveStatSum +=
                    defender.getStats().getStat(statName) *
                            myDefensiveStatsAndWeights.get(statName);
        }

        netStat = offensiveStatSum - defensiveStatSum;

        return netStat;
    }

}
