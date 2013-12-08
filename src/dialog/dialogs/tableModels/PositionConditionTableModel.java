package dialog.dialogs.tableModels;

import javax.swing.JOptionPane;
import stage.PositionCondition;


@SuppressWarnings("serial")
public class PositionConditionTableModel extends GameTableModel {

    public PositionConditionTableModel () {
        String[] names = { "X", "Y", "Affiliation" };
        setColumnNames(names);
        myName = "Position Condition";
    }

    @Override
    public void loadObject (Object object) {
        myList.clear();
        PositionCondition pc = (PositionCondition) object;
        
        Object[] row = new Object[myColumnNames.length];
        row[0] = pc.getX();
        row[1] = pc.getY();
        row[2] = pc.getAffiliation();
        addNewRow(row);

    }

    @Override
    public Object getObject () {
        Object[] row = myList.get(0);
        PositionCondition pc = new PositionCondition();
        pc.setX((int) row[0]);
        pc.setY((int) row[1]);
        pc.setAffiliation((String) row[2]);
        return pc;
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
