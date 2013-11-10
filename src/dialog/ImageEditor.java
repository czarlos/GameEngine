package dialog;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
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
    
    BufferedImage currentImage;
    JButton button;
    ImageCreator imageCreator; // to be replaced with image editor dialog
    JDialog dialog; // image editor dialog
    protected static final String EDIT = "edit";

    
    public ImageEditor () {
        button = new JButton();
        button.setActionCommand("edit");
        button.addActionListener(this);
        button.setBorderPainted(false);
        
        imageCreator = new ImageCreator();
        dialog = ImageCreator.createDialog(button,
                                        "Pick a Color",
                                        true,  //modal
                                        imageCreator,
                                        this,  // this class handles OK button selection
                                        null); // nothing happens when 'cancel' is selected
    }

    @Override
    public Object getCellEditorValue () {
        return currentImage;
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (EDIT.equals(e.getActionCommand())) {
            //The user has clicked the cell, so
            //bring up the dialog.
            dialog.setVisible(true);

            //Make the renderer reappear.
            fireEditingStopped();
        }
    }

    @Override
    public Component getTableCellEditorComponent (JTable arg0,
                                                  Object arg1,
                                                  boolean arg2,
                                                  int arg3,
                                                  int arg4) {
        return button;
    }

}
