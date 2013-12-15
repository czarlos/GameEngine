package dialog.dialogs.tableModels;

import dialog.Selector;
import grid.GridConstants;
import java.util.HashMap;
import java.util.Map;
import controllers.EditorData;


/**
 * Allows users to edit a specific InventoryObject's items
 * 
 * @author Leevi
 * 
 */
@SuppressWarnings("serial")
public class ItemsTableModel extends GameTableModel {

    public ItemsTableModel (EditorData ed) {
        String[] names = { "Item", "Amount" };
        setColumnNames(names);
        myName = "Item";
        myED = ed;
    }

    @SuppressWarnings("unchecked")
    public void loadObject (Object object) {
        Map<String, Integer> map = (Map<String, Integer>) object;
        myList.clear();
        for (Object item : map.keySet()) {
            Object[] row = new Object[myColumnNames.length];
            row[0] = new Selector(myED.getNames(GridConstants.ITEM), item);
            row[1] = map.get(item);
            addNewRow(row);
        }
    }

    @Override
    public Object getObject () {
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        for (Object[] row : myList) {
            String key = (String) ((Selector) row[0]).getValue();
            if (myMap.containsKey(key)) {
                myMap.put(key, myMap.get(key) + (int) row[1]);
            }
            else {
                myMap.put(key, (int) row[1]);
            }
        }

        return myMap;
    }

    @Override
    public Object[] getNew () {
        Object[] array = new Object[myColumnNames.length];
        array[0] = new Selector(myED.getNames(GridConstants.ITEM));
        array[1] = 0;
        return array;
    }
}
