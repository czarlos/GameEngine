package dialog.dialogs.tableModels;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import controllers.EditorData;
import gameObject.Item;
import gameObject.Stats;
import gameObject.action.Action;
import grid.GridConstants;


/**
 * Allows users to edit the editor list of items
 * 
 * @author Leevi
 * 
 */
@SuppressWarnings("serial")
public class ItemTableModel extends GameTableModel {

    public ItemTableModel (EditorData ED) {
        String[] names = { "Name", "Graphic", "Stats", "Actions" };
        myName = GridConstants.ITEM;
        setColumnNames(names);
        myED = ED;
    }

    @Override
    public Object[] getNew () {
        Object[] ret = new Object[myColumnNames.length + 1];
        ret[0] = "New Item";
        ret[1] = new File("resources/amor.png");
        ret[2] = new Stats();
        ret[3] = new ArrayList<Action>();
        ret[4] = -1;

        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadObject (Object object) {
        List<Item> list = (List<Item>) object;
        for (int j = 0; j < list.size(); j++) {
            Item i = list.get(j);
            Object[] array = new Object[myColumnNames.length + 1];
            array[0] = i.getName();
            array[1] = new File(i.getImagePath());
            array[2] = i.getStats();
            array[3] = i.getActions();
            array[4] = j;
            addNewRow(array);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getObject () {
        List<Item> ret = new ArrayList<Item>();
        for (Object[] row : myList) {
            Item i = new Item();
            i.setName((String) row[0]);
            i.setImagePath((String) ((File) row[1]).getPath());
            i.setStats((Stats) row[2]);
            i.setActions((List<String>) row[3]);
            i.setLastIndex((int) row[4]);
            ret.add(i);
        }
        return ret;
    }
}
