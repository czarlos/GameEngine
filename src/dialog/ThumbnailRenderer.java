package dialog;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;


/**
 * @author brooksmershon
 * 
 *         Allows for an image thumbnail to be rendered as a cell in a JTable
 */
public class ThumbnailRenderer extends DefaultTableCellRenderer {

    /**
     * 
     */
    private static final long serialVersionUID = -4561316688532828835L;

    public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected,
                                                    boolean hasFocus, int row, int column) {
        Component cell =
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                                                    column);

        /*
         * Image img = value.getImage() ;
         * Image newimg = img.getScaledInstance( NEW_WIDTH, NEW_HEIGHT, java.awt.Image.SCALE_SMOOTH
         * ) ;
         * icon = new ImageIcon( newimg );
         * 
         */
        
        ImageIcon image;
        
        if(value instanceof File){
            image = new ImageIcon((String) ((File) value).getAbsolutePath());
            value = image;
        }

        ((JLabel) cell).setIcon((Icon) value);
        ((JLabel) cell).setText("");
        ((JLabel) cell).setHorizontalAlignment(JLabel.CENTER);

        if (isSelected) {
            cell.setBackground(Color.green);
        }
        else {
            cell.setBackground(null);
        }
        return cell;
    }
}
