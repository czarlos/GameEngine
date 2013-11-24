package dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


/**
 * @author brooksmershon
 *
 * Presents a view of current units with the ability to add, remove operations for a scrolling
 * table view of row definitions for units
 * 
 * 
 */
public class TileEditorDialog extends JDialog{
    

    /**
     * 
     */
    private static final long serialVersionUID = 370838418473171385L;
    
    private final int DEFAULT_WIDTH = 52;
    private final int DEFAULT_HEIGHT = 500;
    
    /**
     * 
     * @param model - a TableModel class which provides getter and setter methods
     * for cell rendering and editing
     */
    public TileEditorDialog(GameTableModel model) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        

        JTable table = new JTable(model);
        table.setDefaultRenderer(File.class,
                                 new ThumbnailRenderer());
        table.setDefaultEditor(File.class,
                               new ImagePathEditor());
        table.setRowHeight(52);
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        table.setFillsViewportHeight(true);        
        
       panel.add(table);
       add(panel);
       setSize(new Dimension(500, 300));
    }

}
