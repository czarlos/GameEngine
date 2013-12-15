package dialog.dialogs.tableModels;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import controllers.EditorData;


/**
 * Abstraction of the models that deal with type specific data editing
 * 
 * @author Leevi, brooksmershon
 * 
 */
@SuppressWarnings("serial")
public abstract class GameTableModel extends AbstractTableModel {

    protected String myName;
    protected EditorData myED;

    protected final List<Object[]> myList = new ArrayList<Object[]>();
    protected String[] myColumnNames;

    public void setColumnNames (String[] names) {
        this.myColumnNames = names;
    }

    public void addNewRow (Object[] row) {
        myList.add(row);
        fireTableDataChanged();
    }

    public void removeRow (int index) {
        myList.remove(index);
        fireTableDataChanged();
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
    public Class<?> getColumnClass (int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    public void setValueAt (Object aValue, int row, int col) {
        myList.get(row)[col] = aValue;
    }

    public String getName () {
        return myName;
    }

    public EditorData getED () {
        return myED;
    }

    @Override
    public boolean isCellEditable (int row, int column) {
        return true;
    }

    /**
     * Builds a default row, returns null if table model should not have
     * "add new row" funcitonality
     * 
     * @return Object[] - default row
     */
    public abstract Object[] getNew ();

    /**
     * Unpacks a GameObject and builds a row in the table model
     * 
     * @param object
     */
    public abstract void loadObject (Object object);

    /**
     * Return list of Objects represented by all rows in the table model
     * 
     * @return List of Objects
     */
    public abstract Object getObject ();

}
