package dialog;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
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
public class UnitEditorPanel extends JPanel{
    


    /**
     * 
     */
    private static final long serialVersionUID = 370838418473171385L;
    
    /**
     * 
     * @param model - a TableModel class which provides getter and setter methods
     * for cell rendering and editing
     */
    public UnitEditorPanel(GameTableModel model) {
        super(new GridLayout(1,0));

        JTable table = new JTable(model);
        table.setDefaultRenderer(ImageIcon.class,
                                 new ThumbnailRenderer());
        table.setDefaultEditor(ImageIcon.class,
                               new ImageEditor());
        table.setRowHeight(52);
        table.setPreferredScrollableViewportSize(new Dimension(500, 500));
        table.setFillsViewportHeight(true);
        
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
    }

}
