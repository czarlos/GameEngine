package dialog.dialogs;

import gameObject.Stats;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import dialog.dialogs.tableModels.GameTableModel;
import dialog.editors.ImagePathEditor;
import dialog.editors.StatsEditor;
import dialog.renderers.StatsRenderer;
import dialog.renderers.ImageRenderer;


@SuppressWarnings("serial")
public class TableDialog extends JDialog {
    GameTableModel myModel;

    public TableDialog (GameTableModel model, ActionListener okListener) {
        myModel = model;
        addTable();
        addButtonPanel(okListener);
        this.setTitle(myModel.getName() + " Editor");
        pack();
    }

    private void addButtonPanel (ActionListener okListener) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addNewButton = new JButton("add new");

        addNewButton.addActionListener(new AddNewListener());

        JButton ok = new JButton("Save");
        ok.addActionListener(okListener);

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new DefaultCancelListener(this));

        buttonPanel.add(ok);
        buttonPanel.add(cancel);
        buttonPanel.add(addNewButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addTable () {
        setLayout(new BorderLayout());

        JTable table = new JTable(myModel);

        JScrollPane scrollPane = new JScrollPane(table);

        table.setRowHeight(52);
        table.setPreferredScrollableViewportSize(new Dimension(500, 300));
        table.setFillsViewportHeight(true);

        setDefaultEditorsRenderers(table);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void setDefaultEditorsRenderers (JTable table) {
        table.setDefaultRenderer(File.class,
                                 new ImageRenderer());
        table.setDefaultEditor(File.class,
                               new ImagePathEditor());
        table.setDefaultEditor(Stats.class,
                               new StatsEditor());
        table.setDefaultRenderer(Stats.class,
                                 new StatsRenderer());

    }

    private class AddNewListener implements ActionListener {

        private GameTableModel myListenerModel;

        public AddNewListener () {
            super();
            myListenerModel = myModel;
        }

        public void actionPerformed (ActionEvent e) {
            if (myListenerModel.getNew() != null)
                myListenerModel.addNewRow(myListenerModel.getNew());
        }
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

    public GameTableModel getModel () {
        return myModel;
    }
}
