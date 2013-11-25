package dialog;

import gameObject.Stats;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
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
public class StatsRenderer extends DefaultTableCellRenderer {

    /**
     * 
     */
    private static final long serialVersionUID = -4561316688532828835L;
    private static final int DEFAULT_WIDTH = 52;;
    private static final int DEFAULT_HEIGHT = DEFAULT_WIDTH;

    public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected,
                                                    boolean hasFocus, int row, int column) {
        Component cell =
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                                                    column);

        ((JLabel) cell).setText("Stats");
        ((JLabel) cell).setHorizontalAlignment(JLabel.CENTER);

        if (isSelected) {
            cell.setBackground(Color.blue);
        }
        else {
            cell.setBackground(Color.green);
        }
        return cell;
    }

}
