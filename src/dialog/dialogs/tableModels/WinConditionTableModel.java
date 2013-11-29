package dialog.dialogs.tableModels;

import java.util.List;
import stage.Condition;
import stage.WinCondition;
import grid.GridConstants;

@SuppressWarnings("serial")
public class WinConditionTableModel extends GameTableModel {

    public WinConditionTableModel () {
        String[] names = { "How many are needed to win?", "Conditions" };
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
        WinCondition wc = (WinCondition) object;
        Object[] row = new Object[myColumnNames.length];
        
        row[0] = wc.getConditionsNeeded();
        row[1] = wc.getConditions();
                
        addNewRow(row);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getObject () {
        WinCondition wc = new WinCondition();
        wc.setConditionsNeeded((int) myList.get(0)[0]);
        wc.setConditions((List<Condition>) myList.get(0)[0]);
        return wc;
    }

    // only need one win condition per team
    @Override
    public Object[] getNew () {
        return null;
    }
}
