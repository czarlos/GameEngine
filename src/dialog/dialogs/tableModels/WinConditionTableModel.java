package dialog.dialogs.tableModels;

import java.util.Map;
import stage.Condition;
import stage.PositionCondition;
import stage.WinCondition;
import grid.GridConstants;

@SuppressWarnings("serial")
public class WinConditionTableModel extends GameTableModel {

    public WinConditionTableModel () {
        String[] names = { "Conditions" , "Data"};
        setColumnNames(names);
        myName = GridConstants.CONDITION;
    }
    
    @Override
    public void loadObject (Object object) {
        myList.clear();
        WinCondition wc = (WinCondition) object;
        for(Condition c: wc.getConditions()){
            Object[] row = new Object[myColumnNames.length];
            row[0] = c;
            row[1] = c.getData();
            addNewRow(row);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getObject () {
        WinCondition wc = new WinCondition();
        for(Object[] row: myList){
            Condition c = (Condition) row[0];
            c.setData((Map<String, String>) row[1]);
            wc.addCondition(c);
        }
        return wc;
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
