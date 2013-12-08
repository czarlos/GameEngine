package dialog.dialogs.tableModels;

import java.util.ArrayList;
import java.util.List;
import controllers.EditorData;
import gameObject.Stat;
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
        List<Stat> list = (List<Stat>) object;
        myList.clear();
        for (Stat s : list) {
            Object[] row = new Object[myColumnNames.length];
            row[0] = s.getName();
            addNewRow(row);
        }
    }

    public Object getObject () {
        List<Stat> ret = new ArrayList<Stat>();
        for (Object[] row : myList) {
            ret.add(new Stat((String) row[0]));
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
