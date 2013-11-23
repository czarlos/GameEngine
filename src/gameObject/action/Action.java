package gameObject.action;

import gameObject.GameUnit;
import grid.Coordinate;
import java.util.ArrayList;
import java.util.List;


public class Action {
    protected String myName;
    protected List<Outcome> myInitiatorOutcomes;
    protected List<Outcome> myReceiverOutcomes;
    protected List<Coordinate> myAOE;
    protected boolean isAround;

    public Action () {
    }

    public Action (String name, List<Outcome> initiatorOutcomes, List<Outcome> receiverOutcomes) {
        myName = name;
        myInitiatorOutcomes = initiatorOutcomes;
        myReceiverOutcomes = receiverOutcomes;
        isAround = false;
        List<Coordinate> AOE = new ArrayList<>();
        AOE.add(new Coordinate(0, -1));
        AOE.add(new Coordinate(-1, 0));
        AOE.add(new Coordinate(1, 0));
        AOE.add(new Coordinate(0, 1));
        myAOE = AOE;
    }

    public void execute (GameUnit initiator, GameUnit receiver) {
        for (Outcome o : myInitiatorOutcomes) {
            o.applyOutcome(initiator, 0);

        }

        for (Outcome o : myReceiverOutcomes) {
            o.applyOutcome(receiver, 0);
        }
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
