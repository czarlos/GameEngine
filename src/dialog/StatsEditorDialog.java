package dialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
public class StatsEditorDialog extends TableDialog {

    /**
     * 
     */
    private static final long serialVersionUID = 370838418473171385L;
    private final int DEFAULT_WIDTH = 300;
    private final int DEFAULT_HEIGHT = 500;

    /**
     * 
     * @param model - a TableModel class which provides getter and setter methods
     *        for cell rendering and editing
     * @param statsEditor
     */
    public StatsEditorDialog (GameTableModel model, StatsEditor statsEditor) {

        super(model, statsEditor);

        JTable table = new JTable(model);
        table.setDefaultRenderer(File.class,
                                 new ThumbnailRenderer());
        table.setDefaultEditor(File.class,
                               new ImagePathEditor());

        JScrollPane scrollPane = new JScrollPane(table);

        table.setRowHeight(52);
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        table.setFillsViewportHeight(true);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton ok = new JButton("Save");
        ok.addActionListener(statsEditor);

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new DefaultCancelListener(this));

        buttonPanel.add(ok);
        buttonPanel.add(cancel);

        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        pack();
    }

}
