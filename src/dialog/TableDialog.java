package dialog;

import gameObject.Stats;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;


public class TableDialog extends JDialog {
    GameTableModel myModel;
    
    public TableDialog (GameTableModel model, ActionListener okListener) {
        addTable(model);
        myModel = model;
    }

    private void addTable (GameTableModel model) {
        setLayout(new BorderLayout());

        JTable table = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(table);

        table.setRowHeight(52);
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        table.setFillsViewportHeight(true);

        setDefaultEditorsRenderers(table);

        add(scrollPane, BorderLayout.CENTER);

    }

    public void setDefaultEditorsRenderers (JTable table) {
        table.setDefaultRenderer(File.class,
                                 new ThumbnailRenderer());
        table.setDefaultEditor(File.class,
                               new ImagePathEditor());
        table.setDefaultEditor(Stats.class,
                               new StatsEditor());
        table.setDefaultRenderer(Stats.class,
                                 new StatsRenderer());

    }

    protected class DefaultCancelListener implements ActionListener {

        private JDialog dialog;

        public DefaultCancelListener (JDialog dialog) {
            super();
            this.dialog = dialog;

        }

        public void actionPerformed (ActionEvent e) {
            dialog.setVisible(false);
        }
    }


    public GameTableModel getModel(){
        return myModel;
    }
}
