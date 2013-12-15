package dialog.dialogs.tableModels;

import java.util.ArrayList;
import java.util.List;
import controllers.EditorData;
import gameObject.Stat;
import grid.GridConstants;


/**
 * Allows users to edit the Master Stat list for the game
 * 
 * @author Leevi
 * 
 */
@SuppressWarnings("serial")
public class MasterStatsTableModel extends GameTableModel {

    public MasterStatsTableModel (EditorData ED) {
        super();
        String[] names = { "Stat" };
        setColumnNames(names);
        myName = GridConstants.MASTERSTATS;
        myED = ED;
    }

    @Override
    public boolean isCellEditable (int row, int column) {
        return row > 1;
    }

    @SuppressWarnings({ "unchecked" })
    public void loadObject (Object object) {
        List<Stat> list = (List<Stat>) object;
        myList.clear();
        for (int i = 0; i < list.size(); i++) {
            Stat s = list.get(i);
            Object[] row = new Object[myColumnNames.length + 1];
            row[0] = s.getName();
            row[1] = i;
            addNewRow(row);
        }
    }

    @Override
    public Object getObject () {
        List<Stat> ret = new ArrayList<Stat>();
        for (Object[] row : myList) {
            Stat s = new Stat((String) row[0]);
            s.setLastIndex((int) row[1]);
            ret.add(s);
        }

        return ret;
    }

    @Override
    public Object[] getNew () {
        Object[] array = new Object[myColumnNames.length + 1];
        array[0] = "Stat Name";
        array[1] = -1;
        return array;
    }
}
