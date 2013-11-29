package dialog.dialogs.tableModels;

import grid.GridConstants;
import java.util.ArrayList;
import java.util.List;
import stage.Condition;
import stage.PositionCondition;


@SuppressWarnings("serial")
public class ConditionsTableModel extends GameTableModel {

    public ConditionsTableModel () {
        String[] names = { "Current Conditions" };
        setColumnNames(names);
        myName = GridConstants.CONDITION;
    }

    @Override
    public boolean isCellEditable (int row, int col) {
        return true;
    }
    
    @Override
    public void loadObject (Object object) {
        myList.clear();
        List<Condition> list = (List<Condition>) object;
        Object[] array = new Object[myColumnNames.length];
        for (Object o : list) {
            array[0] = (Condition) o;
            addNewRow(array);
        }
    }

    @Override
    public List<?> getObject () {
        List<Condition> ret = new ArrayList<Condition>();
        for (Object[] row : myList) {
            ret.add((Condition) row[0]);
        }

        return ret;
    }

    @Override
    public Object[] getNew () {
        Object[] ret = new Object[myColumnNames.length];
        ret[0] = new PositionCondition();

        return ret;
    }

}
