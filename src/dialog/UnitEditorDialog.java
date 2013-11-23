package dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;


/**
 * @author brooksmershon
 * 
 *         Presents a view of current units with the ability to add, remove operations for a
 *         scrolling
 *         table view of row definitions for units
 * 
 * 
 */
public class UnitEditorDialog extends JDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 370838418473171385L;

    /**
     * 
     * @param model - a TableModel class which provides getter and setter methods
     *        for cell rendering and editing
     */
    public UnitEditorDialog (GameTableModel model) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTable table = new JTable(model);
        table.setDefaultRenderer(ImageIcon.class,
                                 new ThumbnailRenderer());
        table.setDefaultEditor(ImageIcon.class,
                               new ImageEditor());
        table.setRowHeight(52);
        table.setPreferredScrollableViewportSize(new Dimension(500, 500));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);

        panel.add(scrollPane);
        add(panel);
        setSize(new Dimension(500, 500));
    }

}
