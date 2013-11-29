package dialog.dialogs.tableModels;

import stage.Condition;
import stage.PositionCondition;
import grid.GridConstants;

public class ConditionTableModel extends GameTableModel {

    public ConditionTableModel () {
        String[] names = { "Needed Data", "Data" };
        setColumnNames(names);
        myName = GridConstants.CONDITION;
    }

    @Override
    public Object[] getNew () {
        return null;
    }

    @Override
    public boolean isCellEditable (int row, int col) {
        return col == 1;
    }
    
    @Override
    public void loadObject (Object object) {
        myList.clear();
        Condition c = (Condition) object;
        for(String s: c.getNeededData()){
            Object[] array = new Object[myColumnNames.length];
            array[0] = s;
            array[1] = "";
            myList.add(array);
        }
    }

    @Override
    public Object getObject () {
        Condition c = new PositionCondition();
        for(Object[] row : myList){
            c.addData((String) row[0], (String) row[1]);
        }
        return c;
    }

}
