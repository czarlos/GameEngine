package dialog;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 * @author brooksmershon
 *
 * Allows a cell in a JTable representing an image to be edited in place by calling up a dialog
 * that allows for image editing
 */
public class ImageEditor extends AbstractCellEditor
                         implements TableCellEditor, ActionListener{
    
    JButton button;
    
    public ImageEditor () {
        button = new JButton();
        button.setActionCommand("edit");
        button.addActionListener(this);
        button.setBorderPainted(false);
    }

    @Override
    public Object getCellEditorValue () {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void actionPerformed (ActionEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public Component getTableCellEditorComponent (JTable arg0,
                                                  Object arg1,
                                                  boolean arg2,
                                                  int arg3,
                                                  int arg4) {
        // TODO Auto-generated method stub
        return null;
    }

}
