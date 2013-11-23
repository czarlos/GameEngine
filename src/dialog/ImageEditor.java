package dialog;

import java.awt.Component;
import java.awt.Graphics2D;
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
 *         Allows a cell in a JTable representing an image to be edited in place by calling up a
 *         dialog
 *         that allows for image editing
 */
public class ImageEditor extends AbstractCellEditor
                         implements TableCellEditor, ActionListener{
    
    BufferedImage currentImage;
    BufferedImage savedImage;

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
                                           true,  // modal
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
        
        // a cell is chosen for editing
        if (EDIT.equals(e.getActionCommand())) {
            imageCreator.setImage(copyImage(currentImage));
            dialog.setVisible(true);
            
            fireEditingStopped();
            
        // OK has been clicked in the ImageCreator
        } else {
            currentImage = (BufferedImage) imageCreator.getImage();
            dialog.setVisible(false);

        }
    }

    @Override
    public Component getTableCellEditorComponent (JTable table,
                                                  Object value,
                                                  boolean isSelected,
                                                  int row,
                                                  int col) {
        currentImage = (BufferedImage) ((ImageIcon)value).getImage();
        return button;
    }
    
    public static BufferedImage copyImage(BufferedImage source){
        BufferedImage copy = new BufferedImage(source.getWidth(), source.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = (Graphics2D) copy.getGraphics();
        graphics.drawImage(source, 0, 0, null);
        graphics.dispose();
        
        return copy;
    }

}
