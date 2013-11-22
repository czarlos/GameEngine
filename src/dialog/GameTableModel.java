package dialog;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public abstract class GameTableModel extends AbstractTableModel{
    
    protected final List<Object[]> list = new ArrayList<Object[]>();
    private List<String> columnNames;
    
    
    
    public String[] setColomnNames(String[] names){
        
        this.columnNames = names;
        return names.clone();
    }
     
    public abstract Object[] addNewRow(Object[] row);
    
    public Object[] removeRow(int index){
        return list.remove(index);
        
    }

    /**
     * 
     * @return a new ArrayList of the definitions
     */
    public List<Object[]> getData() {
        return new ArrayList<Object[]>(list);
    }
    
    @Override
    public int getColumnCount () {
        return columnNames.length;
    }

    @Override
    public int getRowCount () {
        return list.size();
    }
    
    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
    

    @Override
    public Object getValueAt (int row, int col) {
        return list.get(row)[col];
    }
    
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    

}
