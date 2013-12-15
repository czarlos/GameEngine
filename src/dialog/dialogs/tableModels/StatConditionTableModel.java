package dialog.dialogs.tableModels;

import grid.GridConstants;
import javax.swing.JOptionPane;
import controllers.EditorData;
import dialog.Selector;
import stage.StatCondition;


/**
 * Allows users to edit win conditions that require a team to have
 * cumulative totals of some stat
 * 
 * @author Leevi
 * 
 */
@SuppressWarnings("serial")
public class StatConditionTableModel extends GameTableModel {

    public StatConditionTableModel (EditorData ED) {
        String[] names = { "Stat Type", "Minimum Value", "Affiliation" };
        setColumnNames(names);
        myName = "Stat Condition";
        myED = ED;
    }

    @Override
    public void loadObject (Object object) {
        myList.clear();
        StatCondition sc = (StatCondition) object;

        Object[] row = new Object[myColumnNames.length];
        row[0] = new Selector(myED.getNames(GridConstants.MASTERSTATS), sc.getStatType());
        row[1] = sc.getValue();
        row[2] = new Selector(myED.getNames(GridConstants.TEAM), sc.getAffiliation());
        addNewRow(row);

    }

    @Override
    public Object getObject () {
        Object[] row = myList.get(0);
        StatCondition pc = new StatCondition();
        pc.setStatType((String) ((Selector) row[0]).getValue());
        pc.setValue((int) row[1]);
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
