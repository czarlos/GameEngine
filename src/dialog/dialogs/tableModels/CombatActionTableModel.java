package dialog.dialogs.tableModels;

import gameObject.action.Action;
import gameObject.action.CombatAction;
import gameObject.action.Outcome;
import java.util.ArrayList;
import java.util.List;


@SuppressWarnings("serial")
public class CombatActionTableModel extends GameTableModel {

    public CombatActionTableModel () {
        String[] names = { "Name", "Action Range", "Initiator Outcomes", "Receiver Outcomes"};
        myName = "Action";
        setColumnNames(names);
    }

    @Override
    public Object[] getNew () {
        Object[] ret = new Object[myColumnNames.length];
        ret[0] = "New Action";
        ret[1] = 1;
        List<Outcome> initiator = new ArrayList<Outcome>();
        initiator.add(new Outcome());
        ret[2] = initiator;
        List<Outcome> receiver = new ArrayList<Outcome>();
        receiver.add(new Outcome());
        ret[3] = receiver;
        
        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadObject (Object object) {
        List<CombatAction> list = (List<CombatAction>) object;
        for (CombatAction a : list) {
            Object[] array = new Object[myColumnNames.length];
            array[0] = a.getName();
            array[1] = a.getActionRange();
            array[2] = a.getInitiatorOutcomes();
            array[3] = a.getReceiverOutcomes();
            addNewRow(array);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getObject () {
        List<CombatAction> list = new ArrayList<CombatAction>();
        for (Object[] row : myList) {
            CombatAction a = new CombatAction();
            a.setName((String) row[0]);
            a.setActionRange((int) row[1]);
            a.setInitiatorOutcomes((List<Outcome>) row[2]);
            a.setReceiverOutcomes((List<Outcome>) row[3]);
            list.add(a);
        }
        return list;
    }

}
