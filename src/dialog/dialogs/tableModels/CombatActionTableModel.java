package dialog.dialogs.tableModels;

import gameObject.Stats;
import gameObject.action.CombatAction;
import gameObject.action.Outcomes;
import java.util.ArrayList;
import java.util.List;
import controllers.EditorData;


@SuppressWarnings("serial")
public class CombatActionTableModel extends GameTableModel {

    /**
     * column names: Name, Action Range, Initiator Outcomes, Init. Stat Weights, Receiver Outcomes,
     * Rec. Stat Weights
     */

    public CombatActionTableModel (EditorData ED) {
        String[] names =
        { "Name", "Action Range", "Initiator Outcomes", "Init. Stat Weights",
         "Receiver Outcomes", "Rec. Stat Weights" };
        myName = "Action";
        setColumnNames(names);
        myED = ED;
    }

    @Override
    public Object[] getNew () {
        Object[] ret = new Object[myColumnNames.length + 1];
        ret[0] = "New Action";
        ret[1] = 1;
        ret[2] = new Outcomes();
        ret[3] = new Stats();
        ret[4] = new Outcomes();
        ret[5] = new Stats();
        ret[6] = -1;
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadObject (Object object) {
        myList.clear();
        List<CombatAction> list = (List<CombatAction>) object;
        for (int i = 0; i < list.size(); i++){
            CombatAction a = list.get(i);
            Object[] array = new Object[myColumnNames.length + 1];
            array[0] = a.getName();
            array[1] = a.getActionRange();
            array[2] = a.getInitiatorOutcomes();
            array[3] = a.getInitiatorStatWeights();
            array[4] = a.getReceiverOutcomes();
            array[5] = a.getReceiverStatWeights();
            array[6] = i;
            addNewRow(array);
        }
    }

    @Override
    public Object getObject () {
        List<CombatAction> list = new ArrayList<CombatAction>();
        for (Object[] row : myList) {
            CombatAction a = new CombatAction();
            a.setName((String) row[0]);
            a.setActionRange((int) row[1]);
            a.setInitiatorOutcomes((Outcomes) row[2]);
            a.setInitiatorStatWeights((Stats) row[3]);
            a.setReceiverOutcomes((Outcomes) row[4]);
            a.setReceiverStatWeights((Stats) row[5]);
            a.setImagePath("resources/weapon.png");
            a.setLastIndex((int) row[6]);
            list.add(a);
        }
        return list;
    }
}
