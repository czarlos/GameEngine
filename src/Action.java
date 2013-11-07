import java.util.List;
import java.util.Map;


public class Action {
    private Map<String, Integer> myOffensiveStatsAndWeights;
    private Map<String, Integer> myDefensiveStatsAndWeights;
    private List<Coordinate> myRange;

    public Action (Map<String, Integer> offensiveStats, Map<String, Integer> defensiveStats) {
        myOffensiveStatsAndWeights = offensiveStats;
        myDefensiveStatsAndWeights = defensiveStats;
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
