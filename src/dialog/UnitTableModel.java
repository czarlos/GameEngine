package dialog;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.table.AbstractTableModel;

/**
 * 
 * Maintains a state for a JTable
 * Holds rows of Unit definitions
 * 
 * graphic held in "Image column" is an ImageIcon, to preserve editing capabilities and allow for 
 * use as a label on a button in the cell of a JTable
 * 
 * @author brooksmershon
 *
 */
public class UnitTableModel extends AbstractTableModel{
    

    private static final long serialVersionUID = 9110749927413795404L;
    
    private final boolean DEBUG = true;

    private String[] columnNames = {"Type",
                                    "Name",
                                    "Image",
                                    "Stats",
                                    "Actions",
                                    "Affiliation"};
    
    private final List<Object[]> list = new ArrayList<Object[]>();
    
    /**
     * 
     * Add definition of a unit to table model
     * 
     * @param type
     * @param name
     * @param img
     * @param stats
     * @param actionList
     * @param affiliation
     * @return added definition array CLONE
     */
    public Object[] addNewUnit(String type, String name, ImageIcon img, StatsTestStub stats,
                          ArrayList<ActionTestStub> actionList, String affiliation){
        
        Object[] rowToAdd = {type, name, img, stats, actionList, affiliation};
        
        list.add(rowToAdd);
        
        return rowToAdd.clone();
        
    }
    /**
     * 
     * @param index
     * @return row removed as an Object[]
     */
    public Object[] removeUnit(int index){
        return list.remove(index).clone();
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
    
    /**
     * returns whether a cell can be edited by a CellEditor
     */
    public boolean isCellEditable(int row, int col) {
       return true;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        if (DEBUG) {
            System.out.println("Setting value at " + row + "," + col
                               + " to " + value
                               + " (an instance of "
                               + value.getClass() + ")");
        }

        list.get(row)[col] = value;
        fireTableCellUpdated(row, col);

    }
    
    
    
    

}
