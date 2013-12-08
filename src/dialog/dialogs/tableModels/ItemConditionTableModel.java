package dialog.dialogs.tableModels;

import javax.swing.JOptionPane;
import stage.ItemCondition;


@SuppressWarnings("serial")
public class ItemConditionTableModel extends GameTableModel {

    public ItemConditionTableModel () {
        String[] names = { "Item", "Amount", "Affiliation" };
        setColumnNames(names);
        myName = "Item Condition";
    }

    @Override
    public void loadObject (Object object) {
        myList.clear();
        ItemCondition ic = (ItemCondition) object;

        Object[] row = new Object[myColumnNames.length];
        row[0] = ic.getItem();
        row[1] = ic.getAmount();
        row[2] = ic.getAffiliation();
        addNewRow(row);
    }

    @Override
    public Object getObject () {
        Object[] row = myList.get(0);
        ItemCondition ic = new ItemCondition();
        ic.setItem((String) row[0]);
        ic.setAmount((int) row[1]);
        ic.setAffiliation((String) row[2]);
        return ic;
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
