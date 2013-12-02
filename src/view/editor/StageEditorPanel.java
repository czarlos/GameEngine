package view.editor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.WindowConstants;
import controllers.WorldManager;
import dialog.dialogs.TableDialog;
import dialog.dialogs.tableModels.GameTableModel;


public class StageEditorPanel extends JTabbedPane {

    private static final long serialVersionUID = 2009073373970060149L;

    private WorldManager myWorldManager;
    private HashMap<String, JScrollPane> myTabs;
    private GameObjectPanel selectedPanel;
    private int myID;
    private TableDialog myTableDialog;

    public StageEditorPanel (WorldManager wm, String[] defaultTypes, int stageID) {
        myTabs = new HashMap<String, JScrollPane>();
        myWorldManager = wm;
        myID = stageID;
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        drawTabs(defaultTypes);
        setSize(100, 450);
        repaint();
    }

    private void drawTabs (String[] types) {
        this.removeAll();
        myTabs.clear();
        for (String type : types) {
            JScrollPane tab = makeTab(type);
            addTab(type, tab);
            myTabs.put(type, tab);
        }
        repaint();
    }

    public void refreshTab (String type) {
        JScrollPane replacement = makeTab(type);
        replacement.setName(type);
        int index = this.indexOfTab(type);
        this.remove(myTabs.get(type));
        this.add(replacement, index);
        this.setSelectedIndex(index);
    }

    private JScrollPane makeTab (String type) {
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension(200, 600));
        JScrollPane scroll = new JScrollPane(panel,
                                             ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                             ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setLayout(new ScrollPaneLayout());

        SequentialGroup sg = layout.createSequentialGroup();
        ParallelGroup pg = layout.createParallelGroup();

        // add edit button
        String editString = "Edit " + type + "s";
        JButton editType = new JButton(editString);

        editType.addActionListener(new EditListener(myWorldManager, type, this));

        sg.addComponent(editType);
        pg.addComponent(editType);

        List<String> tileNames = myWorldManager.get(type);
        for (int n = 0; n < tileNames.size(); n++) {
            GameObjectPanel gop =
                    new GameObjectPanel(type,
                                        myWorldManager.getImage(type, n), tileNames.get(n), this);
            panel.add(gop);
            sg.addComponent(gop, 50, 50, 50);
            pg.addComponent(gop, 170, 170, 170);
        }

        layout.setVerticalGroup(sg);
        layout.setHorizontalGroup(pg);
        panel.revalidate();
        panel.repaint();
        return scroll;
    }

    public void changeSelected (GameObjectPanel selected) {
        if (selected == selectedPanel) {
            selectedPanel.deSelect();
            myWorldManager.setActiveObject(myID - 1, "", -1);
            return;
        }
        if (selectedPanel != null)
            selectedPanel.deSelect();
        selectedPanel = selected;
        myWorldManager.setActiveObject(
                                       myID - 1,
                                       selected.getType(),
                                       myWorldManager.get(selected.getType())
                                               .indexOf(
                                                        selected.getName()));
    }

    class EditListener implements ActionListener {

        private WorldManager myWM;
        private String myType;
        private StageEditorPanel myPanel;

        public EditListener (WorldManager wm, String type, StageEditorPanel panel) {
            myWM = wm;
            myType = type;
            myPanel = panel;
        }

        @Override
        public void actionPerformed (ActionEvent e) {
            GameTableModel gtm = myWM.getTableModel(myType);
            myTableDialog =
                    new TableDialog(gtm, new DialogListener(myWM, gtm,
                                                            myPanel, myType),
                                    myWM.getDialogList(myType));
            myTableDialog.setVisible(true);
            myTableDialog
                    .setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        }
    }

    class DialogListener implements ActionListener {
        private WorldManager myWM;
        private GameTableModel myGTM;
        private StageEditorPanel myPanel;
        private String myType;

        public DialogListener (WorldManager wm, GameTableModel gtm,
                               StageEditorPanel panel, String type) {
            myWM = wm;
            myGTM = gtm;
            myPanel = panel;
            myType = type;
        }

        @Override
        public void actionPerformed (ActionEvent e) {
            myWM.setData(myGTM);
            myPanel.refreshTab(myType);
            myTableDialog.setVisible(false);
        }

    }
}
