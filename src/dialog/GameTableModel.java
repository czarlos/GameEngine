package dialog;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import view.Customizable;


@SuppressWarnings("serial")
public abstract class GameTableModel extends AbstractTableModel {

    protected final List<Object[]> myList = new ArrayList<Object[]>();
    protected String[] myColumnNames;

    public void setColumnNames (String[] names) {
        this.myColumnNames = names;
    }

    public void addNewRow (Object[] row) {
        myList.add(row);
    }

    public void removeRow (int index) {
        myList.remove(index);
    }

    /**
     * 
     * @return a new ArrayList of the definitions
     */
    public List<Object[]> getData () {
        return new ArrayList<Object[]>(myList);
    }

    @Override
    public int getColumnCount () {
        return myColumnNames.length;
    }

    @Override
    public int getRowCount () {
        return myList.size();
    }

    @Override
    public String getColumnName (int col) {
        return myColumnNames[col];
    }

    @Override
    public Object getValueAt (int row, int col) {
        return myList.get(row)[col];
    }

    @Override
    public Class getColumnClass (int c) {
        return getValueAt(0, c).getClass();
    }

    public abstract void addPreviouslyDefined (List<Customizable> list);

}
