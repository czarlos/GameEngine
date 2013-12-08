package dialog.dialogs.tableModels;

import javax.swing.JOptionPane;
import stage.TurnCondition;


@SuppressWarnings("serial")
public class TurnConditionTableModel extends GameTableModel {

    public TurnConditionTableModel () {
        String[] names = { "Number of turns to survive" };
        setColumnNames(names);
        myName = "Turn Count Condition";
    }

    @Override
    public void loadObject (Object object) {
        myList.clear();
        TurnCondition tc = (TurnCondition) object;

        Object[] row = new Object[myColumnNames.length];
        row[0] = tc.getCount();
        addNewRow(row);

    }

    @Override
    public Object getObject () {
        Object[] row = myList.get(0);
        TurnCondition tc = new TurnCondition();
        tc.setCount((int) row[0]);
        return tc;
    }

    @Override
    public Object[] getNew () {
        JOptionPane.showMessageDialog(null, "Click save to go back and add more win conditions.");
        return null;
    }

    @Override
    public void removeRow (int index) {
        JOptionPane.showMessageDialog(null, "Click save to go back and remove win conditions.");
    }

}
