package dialog.dialogs.tableModels;

import javax.swing.JOptionPane;
import stage.StatCondition;


@SuppressWarnings("serial")
public class StatConditionTableModel extends GameTableModel {

    public StatConditionTableModel () {
        String[] names = { "Stat Type", "Minimum Value", "Affiliation" };
        setColumnNames(names);
        myName = "Stat Condition";
    }

    @Override
    public void loadObject (Object object) {
        myList.clear();
        StatCondition sc = (StatCondition) object;

        Object[] row = new Object[myColumnNames.length];
        row[0] = sc.getStatType();
        row[1] = sc.getValue();
        row[2] = sc.getAffiliation();
        addNewRow(row);

    }

    @Override
    public Object getObject () {
        Object[] row = myList.get(0);
        StatCondition pc = new StatCondition();
        pc.setStatType((String) row[0]);
        pc.setValue((int) row[1]);
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
