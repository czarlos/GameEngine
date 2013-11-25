package dialog;

import gameObject.Stats;
import grid.Tile;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import view.Customizable;


public class TableDialog extends JDialog {
    GameTableModel myModel;

    public TableDialog (GameTableModel model, ActionListener okListener) {
        addTable(model);
        myModel = model;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addNewButton = new JButton("add new");

        addNewButton.addActionListener(new AddNewListener(model));

        JButton ok = new JButton("Save");
        ok.addActionListener(okListener);

        buttonPanel.add(ok);
        buttonPanel.add(addNewButton);

        add(buttonPanel, BorderLayout.SOUTH);

        pack();
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

    private class AddNewListener implements ActionListener {

        private JDialog dialog;
        private ImageCreator imageCreator;
        private GameTableModel myModel;

        public AddNewListener (GameTableModel model) {
            super();
            myModel = model;
        }

        public void actionPerformed (ActionEvent e) {
            ArrayList<Customizable> newList = new ArrayList<Customizable>();
            Tile defTile = new Tile();
            defTile.setName("New Tile");
            defTile.setImageAndPath("resources/grass.png");
            defTile.setMoveCost(1);

            Stats s = new Stats();
            s.setStats(new HashMap<String, Integer>());

            defTile.setStats(s);

            newList.add(defTile);
            myModel.addPreviouslyDefined(newList);
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
