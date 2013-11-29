package dialog.dialogs.tableModels;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
public abstract class HashTableModel extends SingleTableModel {

    @SuppressWarnings("rawtypes")
    public void loadObject (Object hashMap) {
        HashMap map = (HashMap) hashMap;
        myList.clear();
        for (Object key : map.keySet()) {
            Object[] row = new Object[myColumnNames.length];
            row[0] = key;
            row[1] = map.get(key);
            addNewRow(row);
        }
    }

    public Object getObject () {
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        for (Object[] row : myList) {
            myMap.put((String) row[0], (Integer) row[1]);
        }

        return myMap;
    }

    @Override
    public Object[] getNew () {
        Object[] array = new Object[myColumnNames.length];
        array[0] = "key";
        array[1] = 0;
        return array;
    }
}
