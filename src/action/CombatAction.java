package action;

import gameObject.GameUnit;
import gameObject.StatModifier;
import grid.Coordinate;
import java.util.List;
import java.util.Map;


public class CombatAction {
    private StatModifier myAttackerStatsAndWeights;
    private StatModifier myDefenderStatsAndWeights;
    private double myBaseDamage;
    private Map<String, Integer> myCosts;
    private StatModifier myAttackerOutcomes;
    private StatModifier myDefenderOutcomes;
    private List<Coordinate> myAOE;
    private boolean isAround;

    public CombatAction (StatModifier offensiveStats,
                         StatModifier defensiveStats,
                         double baseDamage,
                         Map<String, Integer> costs,
                         StatModifier attackerOutcomes,
                         StatModifier defenderOutcomes,
                         List<Coordinate> range,
                         boolean around) {
        myAttackerStatsAndWeights = offensiveStats;
        myDefenderStatsAndWeights = defensiveStats;
        myBaseDamage = baseDamage;
        myCosts = costs;
        myAttackerOutcomes = attackerOutcomes;
        myDefenderOutcomes = defenderOutcomes;
        myAOE = range;
        isAround = around;
    }

    public Double getNetEffectiveness (GameUnit attacker, GameUnit defender) {
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
        double damage = myBaseDamage * effectiveness;
        defender.getProperties().setHealth(defender.getProperties().getHealth() - damage);

        applyOutcomes(attacker, myAttackerOutcomes, effectiveness);
        applyOutcomes(defender, myDefenderOutcomes, effectiveness);
    }
    
    /**
     * applyOutcomes edits a units stats based on user specified
     * stats and weights. These outcomes are affected by stat differences
     * between units (effectiveness).
     * 
     * @param unit - GameUnit where stats are being edited
     * @param outcomes - Map of which stats are affected and by how much
     * @param effectiveness - A measurement of how much of an outcome should occur
     */
    private void applyOutcomes (GameUnit unit, StatModifier outcomes, double effectiveness) {
        for (String statAffected : outcomes.getStatModifierMap().keySet()) {
            int oldStatValue = unit.getStats().getStatValue(statAffected);
            int newStatValue = (int) (oldStatValue + effectiveness * outcomes.getStatModifierMap().get(statAffected));

            unit.getStats().setStatValue(statAffected, newStatValue);
        }
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

    public boolean isAround () {
        return isAround;
    }

}
