package gameObject.action;

import gameObject.GameUnit;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonAutoDetect;


@JsonAutoDetect
public class Outcomes {
    private List<Outcome> myOutcomes;

    public Outcomes () {
        myOutcomes = new ArrayList<Outcome>();
    }

    public void applyOutcomes (GameUnit unit, double effectiveness) {
        for (Outcome o : myOutcomes) {
            o.applyOutcome(unit, effectiveness);
        }
    }

    public boolean checkValid (GameUnit unit, double effectiveness) {
        for (Outcome o : myOutcomes) {
            if (!o.checkValidOutcome(unit, effectiveness)) { return false; }
        }
        return true;
    }

    public List<Outcome> getOutcomes () {
        return myOutcomes;
    }

    public void setOutcomes (List<Outcome> outcomes) {
        myOutcomes = outcomes;
    }

    public String toString () {
        return "Outcomes";
    }
}
