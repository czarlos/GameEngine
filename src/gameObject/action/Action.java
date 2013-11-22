package gameObject.action;

import gameObject.GameUnit;
import java.util.List;

public class Action {
    private String myName;
    private List<Outcome> myInitiatorOutcomes;
    private List<Outcome> myReceiverOutcomes;
    
    public Action () {
    }
    
    public Action (String name, List<Outcome> initiatorOutcomes, List<Outcome> receiverOutcomes) {
        myName = name;
        myInitiatorOutcomes = initiatorOutcomes;
        myReceiverOutcomes = receiverOutcomes;
    }
    
    public void execute(GameUnit initiator, GameUnit receiver) {
        for (Outcome o : myInitiatorOutcomes) {
            o.applyOutcome(initiator, 0);

        }

        for (Outcome o : myReceiverOutcomes) {
            o.applyOutcome(receiver, 0);
        }
    }
    
    public String getName() {
        return myName;
    }
}
