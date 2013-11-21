package dialog;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

public abstract class GameTableModel extends AbstractTableModel {
    protected String[] myColumnNames;
    protected final List<Object[]> myData = new ArrayList<Object[]>();

    public List<Object[]> getData () {
        return new ArrayList<Object[]>(myData);
    }


    public Object[] removeRow (int index) {
        return myData.remove(index).clone();
    }
    
    @Override
    public int getColumnCount () {
        return myColumnNames.length;
    }

    @Override
    public int getRowCount () {
        return myData.size();
    }

    @Override
    public String getColumnName (int col) {
        return myColumnNames[col];
    }

    @Override
    public Object getValueAt (int row, int col) {
        return myData.get(row)[col];
    }

    @Override
    public Class getColumnClass (int c) {
        return getValueAt(0, c).getClass();
    }
}
