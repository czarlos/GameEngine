package dialog.dialogs.tableModels;

import grid.GridConstants;
import javax.swing.JOptionPane;
import controllers.EditorData;
import dialog.Selector;
import stage.PositionCondition;


/**
 * Allows users to edit win conditions that require units to be at a certain position
 * 
 * @author Leevi
 * 
 */
@SuppressWarnings("serial")
public class PositionConditionTableModel extends GameTableModel {

    public PositionConditionTableModel (EditorData ED) {
        String[] names = { "X", "Y", "Affiliation" };
        setColumnNames(names);
        myName = "Position Condition";
        myED = ED;
    }

    @Override
    public void loadObject (Object object) {
        myList.clear();
        PositionCondition pc = (PositionCondition) object;

        Object[] row = new Object[myColumnNames.length];
        row[0] = pc.getX();
        row[1] = pc.getY();
        row[2] = new Selector(myED.getNames(GridConstants.TEAM), pc.getAffiliation());
        addNewRow(row);

    }

    @Override
    public Object getObject () {
        Object[] row = myList.get(0);
        PositionCondition pc = new PositionCondition();
        pc.setX((int) row[0]);
        pc.setY((int) row[1]);
        pc.setAffiliation((String) ((Selector) row[2]).getValue());
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
