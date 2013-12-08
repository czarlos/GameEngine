package dialog.dialogs.tableModels;

import java.util.Arrays;
import editor.Selector;
import stage.Condition;
import stage.WinCondition;
import grid.GridConstants;


@SuppressWarnings("serial")
public class WinConditionTableModel extends GameTableModel {

    public WinConditionTableModel () {
        String[] names = { "Condition Type", "Edit Data" };
        setColumnNames(names);
        myName = GridConstants.CONDITION;
    }

    @Override
    public void loadObject (Object object) {
        myList.clear();
        WinCondition wc = (WinCondition) object;
        for (Condition c : wc.getConditions()) {
            Object[] row = new Object[myColumnNames.length];
            row[0] = new Selector(Arrays.asList(GridConstants.CORECONDITIONS), c);
            row[1] = c;
            System.out.println(c.getClass());
            addNewRow(row);
        }
    }

    @Override
    public Object getObject () {
        WinCondition wc = new WinCondition();
        for (Object[] row : myList) {
            wc.addCondition((Condition) row[1]);
        }
        return wc;
    }

    @Override
    public void setValueAt (Object aValue, int row, int col) {
        myList.get(row)[col] = aValue;
        if (col == 0) {
            Condition c = (Condition) ((Selector) aValue).getValue();
            System.out.println(aValue.getClass());
            System.out.println(c.getClass());
            myList.get(row)[1] = c;
        }

        fireTableDataChanged();
    }

    @Override
    public Object[] getNew () {
        Object[] ret = new Object[myColumnNames.length];
        ret[0] = new Selector(Arrays.asList(GridConstants.CORECONDITIONS));
        ret[1] = GridConstants.CORECONDITIONS[0];
        return ret;
    }
}
