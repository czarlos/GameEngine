package dialog;

import java.util.List;
import view.Customizable;


public class WeaponTableModel extends GameTableModel {

    public void addNewRow (Object[] row) {

        // Name, Statsmodifier list of actions
        Object[] rowToAdd = { row[0], row[1], row[2] };

        myList.add(rowToAdd);

        rowToAdd.clone();
    }

    @Override
    void addPreviouslyDefined (List<Customizable> list) {
        // TODO Auto-generated method stub

    }

    // TODO: handle StatsModifier Object

}
