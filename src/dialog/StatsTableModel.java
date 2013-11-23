package dialog;

import java.util.List;
import view.Customizable;


public class StatsTableModel extends GameTableModel {

    @Override
    public void addNewRow (Object[] row) {

        Object[] rowToAdd = { row[0], row[1] };

        myList.add(rowToAdd);

    }

    @Override
    public boolean isCellEditable (int row, int col) {
        return col < 1 ? false : true;
    }

    @Override
    void addPreviouslyDefined (List<Customizable> list) {
        // TODO Auto-generated method stub

    }

}
