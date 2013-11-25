package view.editor;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import controllers.WorldManager;
import dialog.UnitEditorDialog;
import dialog.UnitTableModel;


public class StageEditorPanel extends JTabbedPane {

    private static final long serialVersionUID = 2009073373970060149L;

    private WorldManager myWorldManager;
    private HashMap<String, JScrollPane> myTabs = new HashMap<String, JScrollPane>();
    private GameObjectPanel selectedPanel;

    public StageEditorPanel (WorldManager wm, String[] defaultTypes) {
        myWorldManager = wm;
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
        int index = this.indexOfTab(type);
        this.remove(myTabs.get(type));
        this.add(replacement, index);
    }

    // TODO: change this to support UnitEditorDialog
    private void createUnitEditor () {
        EditorTableFrame frame = new EditorTableFrame("Unit", this);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        // Create and set up the content pane.
        JDialog newContentPane =
                new UnitEditorDialog((UnitTableModel) myWorldManager.getViewModel("unit"));
        // newContentPane.setOpaque(true); // content panes must be opaque
        frame.setContentPane(newContentPane);
        // Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public void changeSelected (GameObjectPanel selected) {
        if (selectedPanel != null)
            selectedPanel.deSelect();
        selectedPanel = selected;
        myWorldManager.setActiveObject(selected.getType(), myWorldManager.get(selected.getType())
                .indexOf(selected.getName()));
    }

    private JScrollPane makeTab (String type) {
        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        panel.setLayout(layout);
        panel.setPreferredSize(new Dimension(200, 600));
        JScrollPane scroll =
                new JScrollPane(panel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.setLayout(new ScrollPaneLayout());

        SequentialGroup sg = layout.createSequentialGroup();
        ParallelGroup pg = layout.createParallelGroup();

        // add edit button
        String editString = "Edit " + type + "s";
        JButton editType = new JButton(editString);
        final String curType = type;

        editType.addActionListener(new ActionListener() {
            // if clicked bring up a tableEditor
            @Override
            public void actionPerformed (ActionEvent event) {
                switch (curType.toLowerCase()) {
                    case "tile":
                        break;// createTileEditor();
                    case "gameunit":
                        createUnitEditor();
                        break;
                    case "gameobject":
                        // createObjectEditor();
                }
            }
        });

        sg.addComponent(editType);
        pg.addComponent(editType);
        // make subpanels for each variation of type
        List<String> tileNames = myWorldManager.get(type);
        for (int n = 0; n < tileNames.size(); n++) {
            GameObjectPanel gop =
                    new GameObjectPanel(type, new ImageIcon(myWorldManager.getImage(type, n)),
                                        tileNames.get(n), this);
            panel.add(gop);
            sg.addComponent(gop, 70, 70, 70);
            pg.addComponent(gop, 170, 170, 170);
        }

        layout.setVerticalGroup(sg);
        layout.setHorizontalGroup(pg);
        panel.revalidate();
        panel.repaint();
        return scroll;
    }
}
