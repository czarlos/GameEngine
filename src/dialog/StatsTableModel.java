package dialog;

import gameObject.Stats;
import grid.Tile;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.Customizable;


public class StatsTableModel extends GameTableModel {

    /**
     * 
     */

    private static final long serialVersionUID = -3269629394841470934L;

    public StatsTableModel () {
        super();
        String[] names = { "Stat", "Value" };
        setColumnNames(names);
        myName = "Stats";
    }

    @Override
    public boolean isCellEditable (int row, int col) {
        return col > 0;
    }

    @Override
    public void addPreviouslyDefined (List<Customizable> stats) {

    }

    public List<Customizable> getObjects () {
        return null;
    }

    public void loadStats (Stats stats) {
        Map<String, Integer> map = stats.getStats();
        myList.clear();
        for (String s : map.keySet()) {
            Object[] row = new Object[myColumnNames.length];
            row[0] = s;
            row[1] = map.get(s);
            addNewRow(row);
        }

    }

    public Stats getStats () {
        Stats ret = new Stats();
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        for (Object[] row : myList) {
            myMap.put((String) row[0], (Integer) row[1]);
        }
        ret.setStats(myMap);

        return ret;
    }
}
