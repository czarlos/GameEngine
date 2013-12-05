package gameObject.action;

import java.util.ArrayList;
import java.util.List;
import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.Stats;


public class CombatAction extends Action {

    private Stats myInitiatorStats;
    private Stats myReceiverStats;
    private Outcomes myInitiatorOutcomes;
    private Outcomes myReceiverOutcomes;

    public CombatAction () {
        myInitiatorOutcomes = new Outcomes();
        myReceiverOutcomes = new Outcomes();
    }
    
    private double getNetEffectiveness (GameUnit initiator, GameUnit receiver) {
        myInitiatorStats = initiator.getStats();
        myReceiverStats = receiver.getStats();

        double offensiveStatSum = 0, defensiveStatSum = 0;
        double netStat = 0;

        for (String statName : myInitiatorStats.getStatNames()) {
            offensiveStatSum += initiator.getTotalStat(statName)
                                * myInitiatorStats.getStatValue(statName);
        }

        for (String statName : myReceiverStats.getStatNames()) {
            defensiveStatSum += receiver.getTotalStat(statName)
                                * myReceiverStats.getStatValue(statName);
        }

        // Creates a normalized output based on max possible difference in favor
        // of attacker
        netStat =
                ((offensiveStatSum - defensiveStatSum) >= 0 ? (offensiveStatSum - defensiveStatSum)
                                                           : 0)
                        / (offensiveStatSum);

        return netStat;
    }

    @Override
    public void doAction (GameUnit initiator, GameObject receiver) {
        double effectiveness = getNetEffectiveness(initiator,
                                                   (GameUnit) receiver);

        myInitiatorOutcomes.applyOutcomes(initiator, effectiveness);

        myReceiverOutcomes.applyOutcomes((GameUnit) receiver, effectiveness);
    }

    @Override
    public boolean isValidAction (GameUnit initiator, GameObject receiver) {
        double effectiveness = getNetEffectiveness(initiator,
                                                   (GameUnit) receiver);

        return myInitiatorOutcomes.checkValid(initiator, effectiveness);
    }
    
    public Outcomes getInitiatorOutcomes() {
        return myInitiatorOutcomes;
    }
    
    public void setInitiatorOutcomes(Outcomes outcomes) {
        myInitiatorOutcomes = outcomes;
    }
    
    public Outcomes getReceiverOutcomes() {
        return myReceiverOutcomes;
    }
    
    public void setReceiverOutcomes(Outcomes outcomes) {
        myReceiverOutcomes = outcomes;
    }
}
