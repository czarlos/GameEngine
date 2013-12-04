package dialog.dialogs.tableModels;

import gameObject.action.Action;
import gameObject.action.CombatAction;
import gameObject.action.Outcome;
import java.util.ArrayList;
import java.util.List;

public class OutcomeTableModel extends GameTableModel {

    public OutcomeTableModel () {

        String[] names = { "Affectee", "Amount", "Fixed?"};
        myName = "Outcome";
        setColumnNames(names);
    }

    @Override
    public Object[] getNew () {
        Object[] ret = new Object[myColumnNames.length];
        ret[0] = "Health";
        ret[1] = -10;
        ret[2] = false;
        
        return ret;
    }

    @Override
    public void loadObject (Object object) {
        List<Outcome> list = (List<Outcome>) object;
        for (Outcome o : list) {
            Object[] array = new Object[myColumnNames.length];
            array[0] = o.getName();
            array[1] = o.getAmount();
            array[2] = o.isFixed();
            addNewRow(array);
        }
    }

    @Override
    public Object getObject () {
        List<Outcome> list = new ArrayList<Outcome>();
        for (Object[] row : myList) {
            Outcome o = new Outcome();
            o.setName((String) row[0]);
            o.setAmount((List<Outcome>) row[1]);
            o.setIsFixed((List<Outcome>) row[2]);
            list.add(o);
        }
        return list;
    }

}
