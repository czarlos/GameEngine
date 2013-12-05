package dialog.dialogs.tableModels;

import gameObject.action.Outcome;
import gameObject.action.Outcomes;
import java.util.ArrayList;
import java.util.List;


public class OutcomesTableModel extends GameTableModel {

    public OutcomesTableModel () {
        String[] names = { "Affectee", "Amount", "Fixed?" };
        myName = "Outcome";
        setColumnNames(names);
    }

    @Override
    public Object[] getNew () {
        Object[] ret = new Object[myColumnNames.length];
        ret[0] = new ComboString("Health");
        ret[1] = -10;
        ret[2] = false;

        return ret;
    }

    @Override
    public void loadObject (Object object) {
        myList.clear();
        Outcomes outcomes = (Outcomes) object;
        List<Outcome> list = outcomes.getOutcomes();
        for (Outcome o : list) {
            Object[] array = new Object[myColumnNames.length];
            array[0] = new ComboString(o.getName());
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
            o.setName(((ComboString) row[0]).toString());
            o.setAmount((int) row[1]);
            o.setIsFixed((boolean) row[2]);
            list.add(o);
        }
        Outcomes outcomes = new Outcomes();
        outcomes.setOutcomes(list);
        
        return outcomes;
    }

}
