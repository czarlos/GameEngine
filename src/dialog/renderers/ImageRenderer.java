package dialog.renderers;

import java.awt.Color;
import java.awt.Component;
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


/**
 * @author brooksmershon
 * 
 *         Allows for an image thumbnail to be rendered as a cell in a JTable
 */
@SuppressWarnings("serial")
public class ImageRenderer extends DefaultTableCellRenderer {

    private static final int DEFAULT_WIDTH = 52;
    private static final int DEFAULT_HEIGHT = DEFAULT_WIDTH;

    public Component getTableCellRendererComponent (JTable table, Object value, boolean isSelected,
                                                    boolean hasFocus, int row, int column) {
        Component cell =
                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
                                                    column);
        ImageIcon image;

        if (value instanceof File) {
            image = new ImageIcon((String) ((File) value).getAbsolutePath());
            value = new ImageIcon(getScaledImage(image.getImage(), DEFAULT_WIDTH, DEFAULT_HEIGHT));
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

    private Image getScaledImage (Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = resizedImg.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.drawImage(srcImg, 0, 0, w, h, null);
        graphics2D.dispose();
        
        return resizedImg;
    }
}
