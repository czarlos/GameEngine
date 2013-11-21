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
public class UnitTableModel extends GameTableModel {

    private static final long serialVersionUID = 9110749927413795404L;

    public UnitTableModel(){
       String[] columns = { "Type",
            "Name",
            "Image",
            "Stats",
            "Actions",
            "Affiliation" };
       myColumnNames = columns;
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
    
    public Object[] addNewUnit (String type, String name, ImageIcon img, StatsTestStub stats,
                                ArrayList<ActionTestStub> actionList, String affiliation) {

        Object[] rowToAdd = { type, name, img, stats, actionList, affiliation };

        myData.add(rowToAdd);

        return rowToAdd.clone();

    }


    public boolean isCellEditable (int row, int col) {
        // everything editable
        return true;
    }

    @Override
    public void setValueAt (Object value, int row, int col) {
        myData.get(row)[col] = value;
        fireTableCellUpdated(row, col);

    }

}
