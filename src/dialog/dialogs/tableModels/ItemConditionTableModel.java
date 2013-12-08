package dialog.dialogs.tableModels;

import grid.GridConstants;
import javax.swing.JOptionPane;
import controllers.EditorData;
import dialog.Selector;
import stage.ItemCondition;

/**
 * 
 * @author Leevi, brooksmershon
 *
 */
@SuppressWarnings("serial")
public class ItemConditionTableModel extends GameTableModel {

    public ItemConditionTableModel (EditorData ED) {
        String[] names = { "Item", "Amount", "Affiliation" };
        setColumnNames(names);
        myName = "Item Condition";
        myED = ED;
    }

    @Override
    public void loadObject (Object object) {
        myList.clear();
        ItemCondition ic = (ItemCondition) object;

        Object[] row = new Object[myColumnNames.length];
        row[0] = new Selector(myED.getNames(GridConstants.ITEM), ic.getItem());
        row[1] = ic.getAmount();
        row[2] = new Selector(myED.getNames(GridConstants.TEAM), ic.getAffiliation());
        addNewRow(row);
    }

    @Override
    public Object getObject () {
        Object[] row = myList.get(0);
        ItemCondition ic = new ItemCondition();
        ic.setItem((String) ((Selector) row[0]).getValue());
        ic.setAmount((int) row[1]);
        ic.setAffiliation((String) ((Selector) row[2]).getValue());
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
