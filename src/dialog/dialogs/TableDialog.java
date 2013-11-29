package dialog.dialogs;

import gameObject.Stats;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import stage.Condition;
import stage.PositionCondition;
import stage.StatCondition;
import stage.TurnCondition;
import stage.UnitCountCondition;
import stage.WinCondition;
import dialog.dialogs.tableModels.ComboString;
import dialog.dialogs.tableModels.ConditionsTableModel;
import dialog.dialogs.tableModels.EnumTableModel;
import dialog.dialogs.tableModels.GameTableModel;
import dialog.dialogs.tableModels.MapTableModel;
import dialog.dialogs.tableModels.StatsTableModel;
import dialog.dialogs.tableModels.WinConditionTableModel;
import dialog.editors.ImagePathEditor;
import dialog.editors.ModelEditor;
import dialog.renderers.ImageRenderer;


@SuppressWarnings("serial")
public class TableDialog extends JDialog {
    GameTableModel myModel;
    List<String> myEnumList;

    public TableDialog (GameTableModel gtm, ActionListener okListener) {
        this(gtm, okListener, new ArrayList<String>());
    }

    public TableDialog (GameTableModel gtm, ActionListener okListener, List<String> dialogList) {
        setList(dialogList);
        myModel = gtm;
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
                               new ModelEditor(new StatsTableModel()));
        table.setDefaultEditor(WinCondition.class, new ModelEditor(new WinConditionTableModel()));
        table.setDefaultEditor(new ArrayList<Condition>().getClass(),
                               new ModelEditor(new ConditionsTableModel()));
        table.setDefaultEditor(Condition.class, new DefaultCellEditor(getConditionComboBox()));
        table.setDefaultEditor(HashMap.class, new ModelEditor(new MapTableModel()));
        table.setDefaultEditor(new ArrayList<String>().getClass(),
                               new ModelEditor(new EnumTableModel()));
        table.setDefaultEditor(ComboString.class, new DefaultCellEditor(getComboBox()));
    }

    // for Strings that you want to always be interpreted as comboboxes
    private JComboBox<ComboString> getComboBox () {

        JComboBox<ComboString> comboBox = new JComboBox<ComboString>();
        for (String s : myEnumList) {
            comboBox.addItem(new ComboString(s));
        }
        return comboBox;
    }

    public JComboBox<Condition> getConditionComboBox () {
        JComboBox<Condition> comboBox = new JComboBox<Condition>();
        comboBox.addItem(new PositionCondition());
        comboBox.addItem(new StatCondition());
        comboBox.addItem(new TurnCondition());
        comboBox.addItem(new UnitCountCondition());
        return comboBox;
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

    private void setList (List<String> list) {
        myEnumList = list;
    }
}
