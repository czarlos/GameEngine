package dialog.dialogs.tableModels;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import controllers.EditorData;
import gameObject.GameObject;
import grid.GridConstants;


@SuppressWarnings("serial")
public class ObjectTableModel extends GameTableModel {

    public ObjectTableModel (EditorData myED) {
        String[] names = { "Name", "Graphic", "Passables" };
        myName = GridConstants.GAMEOBJECT;
        setColumnNames(names);
    }

    @Override
    public Object[] getNew () {
        Object[] ret = new Object[myColumnNames.length];
        ret[0] = "New Object";
        ret[1] = new File("resources/grass.png");
        List<String> list = new ArrayList<String>();
        list.add("everything");
        ret[2] = list;

        return ret;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void loadObject (Object object) {
        List<GameObject> list = (List<GameObject>) object;
        for (GameObject go : list) {
            Object[] array = new Object[myColumnNames.length];
            array[0] = go.getName();
            array[1] = new File(go.getImagePath());
            array[2] = go.getPassableList();
            addNewRow(array);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getObject () {
        List<GameObject> ret = new ArrayList<GameObject>();
        for (Object[] row : myList) {
            GameObject go = new GameObject();
            go.setName((String) row[0]);
            go.setImagePath((String) ((File) row[1]).getPath());
            go.setPassableList((List<String>) row[2]);
            ret.add(go);
        }
        return ret;
    }

    @Override
    public String getRowType () {
        return getName();
    }
}
