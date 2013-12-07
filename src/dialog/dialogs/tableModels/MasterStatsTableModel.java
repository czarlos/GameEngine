package dialog.dialogs.tableModels;

import java.util.ArrayList;
import java.util.List;
import controllers.EditorData;
import grid.GridConstants;


@SuppressWarnings("serial")
public class MasterStatsTableModel extends GameTableModel {

    /**
     * Column names: Stat
     */

    public MasterStatsTableModel (EditorData myED) {
        super();
        String[] names = { "Stat" };
        setColumnNames(names);
        myName = GridConstants.MASTERSTATS;
    }

    @SuppressWarnings({ "unchecked" })
    public void loadObject (Object object) {
        List<String> list = (List<String>) object;
        myList.clear();
        for(String s: list){
            Object[] row = new Object[myColumnNames.length];
            row[0] = s;
            addNewRow(row);
        }
    }

    public Object getObject () {
        List<String> ret = new ArrayList<String>();
        for (Object[] row : myList) {
            ret.add((String) row[0]);
        }

        return ret;
    }

    @Override
    public Object[] getNew () {
        Object[] array = new Object[myColumnNames.length];
        array[0] = "Stat Name";
        return array;
    }
}
