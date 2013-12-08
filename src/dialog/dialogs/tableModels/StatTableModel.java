package dialog.dialogs.tableModels;

import gameObject.Stat;
import gameObject.Stats;
import grid.GridConstants;
import java.util.List;
import javax.swing.JOptionPane;


@SuppressWarnings("serial")
public class StatTableModel extends GameTableModel {

    /**
     * Column names: Stat, Value
     */
    public StatTableModel () {
        super();
        String[] names = { "Stat", "Value" };
        setColumnNames(names);
        myName = GridConstants.STATS;
    }

    @Override
    public boolean isCellEditable (int row, int col) {
        return col > 0;
    }

    public void loadObject (Object stats) {
        Stats s = (Stats) stats;
        List<Stat> list = s.getStats();
        myList.clear();
        for (Stat stat : list) {
            Object[] row = new Object[myColumnNames.length];
            row[0] = stat.getName();
            row[1] = stat.getValue();
            addNewRow(row);
        }
    }
    
    @Override
    public Object getObject () {
        Stats ret = new Stats();
        for (Object[] row : myList) {
            Stat stat = new Stat((String) row[0]);
            stat.setValue((int) row[1]);
            ret.addStat(stat);
        }
        return ret;
    }

    @Override
    public Object[] getNew () {
        JOptionPane.showMessageDialog(null,
                                      "Use Master Stats Editor to add a new stat for all objects.");
        return null;
    }

    @Override
    public void removeRow (int index) {
        JOptionPane.showMessageDialog(null,
                                      "Use Master Stats Editor to remove a stat from all objects.");
    }
}
