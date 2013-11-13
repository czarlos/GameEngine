package dialog;

import java.awt.Component;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.AbstractCellEditor;
import javax.swing.ImageIcon;
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
    
    Image currentImage;
    JButton button;
    ImageCreator imageCreator; // to be replaced with image editor dialog
    JDialog dialog; // image editor dialog
    protected static final String EDIT = "edit";

    
    public ImageEditor () {
        button = new JButton();
        button.setActionCommand(EDIT);
        button.addActionListener(this);
        button.setBorderPainted(false);
        
        imageCreator = new ImageCreator();
        dialog = ImageCreator.createDialog(button,
                                        "Image Editor",
                                        true,  //modal
                                        imageCreator,
                                        this,  // this class handles OK button selection
                                        null); // nothing happens when 'cancel' is selected
    }

    @Override
    public Object getCellEditorValue () {
        return new ImageIcon(currentImage);
    }

    @Override
    public void actionPerformed (ActionEvent e) {
        if (EDIT.equals(e.getActionCommand())) {
            imageCreator.setImage(currentImage);
            dialog.setVisible(true);

            fireEditingStopped();
        } else {
            currentImage = imageCreator.getImage();
        }
    }

    @Override
    public Component getTableCellEditorComponent (JTable table,
                                                  Object value,
                                                  boolean isSelected,
                                                  int row,
                                                  int col) {
        currentImage = ((ImageIcon)value).getImage();
        return button;
    }

}
