package dialog.dialogs.tableModels;

import gameObject.action.Outcome;
import gameObject.action.Outcomes;
import grid.GridConstants;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import controllers.EditorData;
import dialog.Selector;


/**
 * Allows users to edit Outcomes that are applied to actions
 * 
 * @author Leevi
 * 
 */
@SuppressWarnings("serial")
public class OutcomesTableModel extends GameTableModel {

    public OutcomesTableModel (EditorData ED) {
        String[] names = { "Type", "Affectee", "Amount", "Fixed?" };
        myName = "Outcome";
        setColumnNames(names);
        myED = ED;
    }

    @Override
    public Object[] getNew () {
        Object[] ret = new Object[myColumnNames.length];
        ret[0] = new Selector(Arrays.asList(GridConstants.COREOUTCOMES));
        ret[1] = new Selector(myED.get(GridConstants.COREOUTCOMES[0].getType()));
        ret[2] = -10;
        ret[3] = false;

        return ret;
    }

    @Override
    public void setValueAt (Object aValue, int row, int col) {
        myList.get(row)[col] = aValue;
        if (col == 0) {
            Outcome o = (Outcome) ((Selector) aValue).getValue();
            myList.get(row)[1] = new Selector(myED.get(o.getType()));
        }

        fireTableDataChanged();
    }

    @Override
    public void loadObject (Object object) {
        myList.clear();
        Outcomes outcomes = (Outcomes) object;
        List<Outcome> list = outcomes.getOutcomes();
        for (Outcome o : list) {
            Object[] array = new Object[myColumnNames.length];
            array[0] = new Selector(Arrays.asList(GridConstants.COREOUTCOMES), o);
            array[1] = new Selector(myED.get(o.getType()), o.getAffectee());
            array[2] = o.getAmount();
            array[3] = o.isFixed();
            addNewRow(array);
        }
    }

    @Override
    public Object getObject () {
        List<Outcome> list = new ArrayList<Outcome>();
        for (Object[] row : myList) {
            Outcome o = (Outcome) (((Selector) row[0]).getValue());
            o.setAffectee(((Selector) row[1]).getValue());
            o.setAmount((int) row[2]);
            o.setIsFixed((boolean) row[3]);
            list.add(o);
        }
        Outcomes outcomes = new Outcomes();
        outcomes.setOutcomes(list);

        return outcomes;
    }
}
