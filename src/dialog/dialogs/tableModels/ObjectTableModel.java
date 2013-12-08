package dialog.dialogs.tableModels;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import controllers.EditorData;
import gameObject.GameObject;
import gameObject.InventoryObject;
import grid.GridConstants;


@SuppressWarnings("serial")
public class ObjectTableModel extends GameTableModel {

    public ObjectTableModel (EditorData ED) {
        String[] names = { "Name", "Graphic", "Passables" };
        myName = GridConstants.GAMEOBJECT;
        setColumnNames(names);
        myED = ED;
    }

    @Override
    public void removeRow (int index) {
        if (index > 1)
            super.removeRow(index);
    }

    @Override
    public Object[] getNew () {
        Object[] ret = new Object[myColumnNames.length + 1];
        ret[0] = "New Object";
        ret[1] = new File("resources/grass.png");
        List<String> list = new ArrayList<String>();
        list.add("everything");
        ret[2] = list;
        ret[3] = -1;

        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadObject (Object object) {
        List<GameObject> list = (List<GameObject>) object;
        for (int i = 0; i < list.size(); i++) {
            GameObject go = list.get(i);
            Object[] array = new Object[myColumnNames.length + 1];
            array[0] = go.getName();
            array[1] = new File(go.getImagePath());
            array[2] = go.getPassableList();
            array[3] = i;
            addNewRow(array);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getObject () {
        List<GameObject> ret = new ArrayList<GameObject>();

        for (int i = 0; i < 2; i++) {
            Object[] row = myList.get(i);
            InventoryObject io = new InventoryObject();
            io.setName((String) row[0]);
            io.setImagePath((String) ((File) row[1]).getPath());
            io.setPassableList((List<String>) row[2]);
            io.setLastIndex((int) row[3]);
            ret.add(io);
        }
        for (int i = 2; i < myList.size(); i++) {
            GameObject go = new GameObject();
            Object[] row = myList.get(i);
            
            go.setName((String) row[0]);
            go.setImagePath((String) ((File) row[1]).getPath());
            go.setPassableList((List<String>) row[2]);
            go.setLastIndex((int) row[3]);
            ret.add(go);
        }
        return ret;
    }
}
