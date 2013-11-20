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
public class UnitTableModel extends GameTableModel{
    

    private static final long serialVersionUID = 9110749927413795404L;
    
    private final boolean DEBUG = true;
    
    
    
    public UnitTableModel() {
        //super();
        
        String[] names = {"Type",
                         "Name",
                         "Image",
                         "Stats",
                         "Actions",
                         "Affiliation"};
       setColomnNames(names);
        
    }
    
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
    public Object[] addNewRow(Object[] row){
        
        Object[] rowToAdd = {row[0], row[1], row[2], row[3], row[4], row[5]};
        
        list.add(rowToAdd);
        
        return rowToAdd.clone();
        
    }
    /**
     * 
     * @param index
     * @return row removed as an Object[]
     */
    public Object[] removeRow(int index){
        return list.remove(index).clone();
    }

    
    /**
     * returns whether a cell can be edited by a CellEditor
     */
    public boolean isCellEditable(int row, int col) {
       return true;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        
        list.get(row)[col] = value;
        fireTableCellUpdated(row, col);

    }
    
    
    
    

}
