package dialog.dialogs;

import gameObject.Stats;
import gameObject.action.Outcomes;
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
import dialog.dialogs.tableModels.EnumTableModel;
import dialog.dialogs.tableModels.GameTableModel;
import dialog.dialogs.tableModels.MapTableModel;
import dialog.dialogs.tableModels.OutcomesTableModel;
import dialog.dialogs.tableModels.StatsTableModel;
import dialog.dialogs.tableModels.WinConditionTableModel;
import dialog.editors.ImagePathEditor;
import dialog.editors.IntegerEditor;
import dialog.editors.ModelEditor;
import dialog.renderers.ComboStringRenderer;
import dialog.renderers.ImageRenderer;

/**
 * 
 * @author brooksmershon
 *
 */
@SuppressWarnings("serial")
public class TableDialog extends JDialog {
    GameTableModel myModel;
    List<String> myEnumList;
    JTable myTable;

    public TableDialog (GameTableModel gtm, ActionListener okListener) {
        this(gtm, okListener, new ArrayList<String>());
    }

    public TableDialog (GameTableModel gtm, ActionListener okListener,
                        List<String> dialogList) {
        setList(dialogList);
        myModel = gtm;
        addTable();
        addButtonPanel(okListener);
        this.setTitle(myModel.getName() + " Editor");
        pack();
    }
    
    /**
     * Create Save, cancel, Add New and Delete Buttons at the bottom of the JDialog
     * @param okListener
     */
    private void addButtonPanel (ActionListener okListener) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton addNewButton = new JButton("Add New " + myModel.getName());

        JButton delete = new JButton("Delete");

        addNewButton.addActionListener(new AddNewListener());
        delete.addActionListener(new DeleteListener());
        JButton ok = new JButton("Save");
        ok.addActionListener(okListener);

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(new DefaultCancelListener(this));

        buttonPanel.add(ok);
        buttonPanel.add(cancel);
        buttonPanel.add(addNewButton);
        buttonPanel.add(delete);

        add(buttonPanel, BorderLayout.SOUTH);
    }
    /**
     * add table and set Default Renderers
     */
    private void addTable () {
        setLayout(new BorderLayout());

        myTable = new JTable(myModel);

        JScrollPane scrollPane = new JScrollPane(myTable);

        myTable.setRowHeight(52);
        myTable.setPreferredScrollableViewportSize(new Dimension(500, 300));
        myTable.setFillsViewportHeight(true);

        setDefaultEditorsRenderers(myTable);

        add(scrollPane, BorderLayout.CENTER);
    }
    
    /**
     * Set renderers and editors to operate on Objects int he table of types including:
     * File, Stats, WinCondiiton, HashMap, ArrayList, ComboString, Integer, Outcomes
     * @param table
     */
    public void setDefaultEditorsRenderers (JTable table) {
        table.setDefaultRenderer(File.class, new ImageRenderer());
        table.setDefaultEditor(File.class, new ImagePathEditor());
        table.setDefaultEditor(Stats.class, new ModelEditor(
                                                            new StatsTableModel()));
        table.setDefaultEditor(WinCondition.class, new ModelEditor(
                                                                   new WinConditionTableModel()));
        table.setDefaultEditor(Condition.class, new DefaultCellEditor(
                                                                      getConditionComboBox()));
        table.setDefaultEditor(HashMap.class, new ModelEditor(
                                                              new MapTableModel()));
        table.setDefaultEditor(ArrayList.class, new ModelEditor(
                                                                new EnumTableModel(), myEnumList));
        
        table.setDefaultRenderer(ComboString.class, new ComboStringRenderer());

        table.setDefaultEditor(Integer.class, new IntegerEditor(0, 50));
        table.setDefaultEditor(Outcomes.class, new ModelEditor(new OutcomesTableModel()));
    }
    
    /**
     * JComboBox class used to identify appropriate cell editor/renderer in JTable
     * 
     * @return JComboBox<ComboString>
     */
    private JComboBox<ComboString> getComboBox () {

        JComboBox<ComboString> comboBox = new JComboBox<ComboString>();
        for (String s : myEnumList) {
            comboBox.addItem(new ComboString(s));
        }
        return comboBox;
    }
    /**
     * Condition class used to identify appropriate cell editor/render in JTable
     * 
     * @return JComboBox<Condition>
     */
    public JComboBox<Condition> getConditionComboBox () {
        JComboBox<Condition> comboBox = new JComboBox<Condition>();
        comboBox.addItem(new PositionCondition());
        comboBox.addItem(new StatCondition());
        comboBox.addItem(new TurnCondition());
        comboBox.addItem(new UnitCountCondition());
        return comboBox;
    }
    
    /**
     * 
     * Creates Listener on this JDialog's underlying table model if adding new rows is supported
     * (i.e. Table structure is mutable)
     * 
     */
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

    /**
     * 
     * Creates Listener on this JDialog's underlying table model if deleting currently selected
     * rows is supported and there are rows remaining in the tableModel.
     * 
     */
    private class DeleteListener implements ActionListener {

        private GameTableModel myListenerModel;

        public DeleteListener () {
            super();
            myListenerModel = myModel;
        }

        public void actionPerformed (ActionEvent e) {
            if (myTable.getSelectedRow() > -1) {
                myListenerModel.removeRow(myTable.getSelectedRow());
            }
        }
    }
    
    /**
     * Close Dialog when this listener is executed.
     * e.g. Cancel button functionality.
     * 
     * @author brooksmershon
     *
     */

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
    /**
     * 
     * @return GameTableModel - this JDialog's underlying GameTableModel, which extends JTableModel
     */
    public GameTableModel getModel () {
        return myModel;
    }
    /**
     * Used to set master lists for populating JComboBox choices
     * @param list
     */
    private void setList (List<String> list) {
        myEnumList = list;
    }
    
    /**
     * Check if table is currently editing, then call cellEditor and stopCellEditing.
     * Useful for ensuring that inputs are not discarded when a user chooses to accept changes
     * before unfocusing on a particular cell.
     */
    public void stopEditing () {
        if (myTable.isEditing())
            myTable.getCellEditor().stopCellEditing();
    }
}
