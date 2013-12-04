package gameObject.action;

import java.util.ArrayList;
import java.util.List;
import gameObject.GameObject;
import gameObject.GameUnit;
import gameObject.Stats;


public class CombatAction extends Action {

    private Stats myInitiatorStats;
    private Stats myReceiverStats;
    private List<Outcome> myInitiatorOutcomes;
    private List<Outcome> myReceiverOutcomes;

    public CombatAction () {
        myInitiatorOutcomes = new ArrayList<Outcome>();
        myReceiverOutcomes = new ArrayList<Outcome>();
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

        for (Outcome o : myInitiatorOutcomes) {
            o.applyOutcome(initiator, effectiveness);
        }

        for (Outcome o : myReceiverOutcomes) {
            o.applyOutcome((GameUnit) receiver, effectiveness);
        }
    }

    @Override
    public boolean isValidAction (GameUnit initiator, GameObject receiver) {
        double effectiveness = getNetEffectiveness(initiator,
                                                   (GameUnit) receiver);

        for (Outcome o : myInitiatorOutcomes) {
            if (!o.checkValidOutcome(initiator, effectiveness)) { return false; }
        }
        return true;
    }
}
