package dialog.dialogs.tableModels;

import gameObject.Stats;
import grid.GridConstants;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import view.Customizable;


public class StatsTableModel extends GameTableModel {

    private static final long serialVersionUID = -3269629394841470934L;

    public StatsTableModel () {
        super();
        String[] names = { "Stat", "Value" };
        setColumnNames(names);
        myName = GridConstants.STATS;
    }

    @Override
    public boolean isCellEditable (int row, int col) {
        return col > 0;
    }

    // will never have list of stats
    @Override
    public void addPreviouslyDefined (List<Customizable> stats) {

    }

    // will never have list of stats
    public List<Customizable> getObjects () {
        return null;
    }

    public void loadStats (Object stats) {
        Stats s = (Stats) stats;
        Map<String, Integer> map = s.getStats();
        myList.clear();
        for (String stat : map.keySet()) {
            Object[] row = new Object[myColumnNames.length];
            row[0] = stat;
            row[1] = map.get(stat);
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

    @Override
    public Object[] getNew () {
        return null;
    }
}
