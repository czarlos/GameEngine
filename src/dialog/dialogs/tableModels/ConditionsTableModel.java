package dialog.dialogs.tableModels;

import grid.GridConstants;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import stage.Condition;
import stage.PositionCondition;


@SuppressWarnings("serial")
public class ConditionsTableModel extends GameTableModel {

    public ConditionsTableModel () {
        String[] names = { "Current Conditions", "Data" };
        setColumnNames(names);
        myName = GridConstants.CONDITION;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public void loadObject (Object object) {
        myList.clear();
        List<Condition> list = (List<Condition>) object;
        for (Object o : list) {
            Object[] array = new Object[myColumnNames.length];
            Condition c = (Condition) o;
            array[0] = c;
            array[1] = c.getData();
            addNewRow(array);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<?> getObject () {
        List<Condition> ret = new ArrayList<Condition>();
        for (Object[] row : myList) {
            Condition c = (Condition) row[0];
            c.setData((Map<String, String>) row[1]);
            ret.add(c);
        }

        return ret;
    }

    @Override
    public void setValueAt (Object aValue, int row, int col) {
        myList.get(row)[col] = aValue;
        if (col == 0) {
            Condition c = (Condition) aValue;
            myList.get(row)[1] = c.getData();
        }

        fireTableDataChanged();
    }

    @Override
    public Object[] getNew () {
        Object[] ret = new Object[myColumnNames.length];
        Condition c = new PositionCondition();
        ret[0] = c;
        ret[1] = c.getData();
        return ret;
    }
}
