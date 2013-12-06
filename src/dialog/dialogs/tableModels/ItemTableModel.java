package dialog.dialogs.tableModels;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import gameObject.Stats;
import gameObject.action.Action;
import gameObject.item.Item;
import grid.GridConstants;


@SuppressWarnings("serial")
public class ItemTableModel extends GameTableModel {

    /**
     * Column names: Name, Graphic, Stats, Actions
     */
    public ItemTableModel () {
        String[] names = { "Name", "Graphic", "Stats", "Actions" };
        myName = GridConstants.ITEM;
        setColumnNames(names);
    }

    @Override
    public Object[] getNew () {
        Object[] ret = new Object[myColumnNames.length];
        ret[0] = "New Item";
        ret[1] = new File("resources/grass.png");
        ret[2] = new Stats();
        ret[3] = new ArrayList<Action>();

        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadObject (Object object) {
        List<Item> list = (List<Item>) object;
        for (Item i : list) {
            Object[] array = new Object[myColumnNames.length];
            array[0] = i.getName();
            array[1] = new File(i.getImagePath());
            array[2] = i.getStats();
            array[3] = i.getActions();
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
            ret.add(i);
        }
        return ret;
    }

}
