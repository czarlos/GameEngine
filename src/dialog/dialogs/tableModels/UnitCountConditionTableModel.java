package dialog.dialogs.tableModels;

import dialog.Selector;
import grid.GridConstants;
import javax.swing.JOptionPane;
import controllers.EditorData;
import stage.UnitCountCondition;


/**
 * Allows users to edit win conditions that are based upon how many individuals of
 * a certain team are present in the game
 * 
 * @author Leevi
 * 
 */
@SuppressWarnings("serial")
public class UnitCountConditionTableModel extends GameTableModel {

    public UnitCountConditionTableModel (EditorData ED) {
        String[] names = { "Number of Units", "Affiliation", "Win if count is greater?" };
        setColumnNames(names);
        myName = "Unit Count Condition";
        myED = ED;
    }

    @Override
    public void loadObject (Object object) {
        myList.clear();
        UnitCountCondition ucc = (UnitCountCondition) object;

        Object[] row = new Object[myColumnNames.length];
        row[0] = ucc.getCount();
        row[1] = new Selector(myED.getNames(GridConstants.TEAM), ucc.getAffiliation());
        row[2] = ucc.isGreater();
        addNewRow(row);
    }

    @Override
    public Object getObject () {
        Object[] row = myList.get(0);
        UnitCountCondition uc = new UnitCountCondition();
        uc.setCount((int) row[0]);
        uc.setAffiliation((String) ((Selector) row[1]).getValue());
        uc.setGreater((boolean) row[2]);
        return uc;
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
