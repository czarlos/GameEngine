package view.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import controllers.WorldManager;


public class StageEditorPanel extends JTabbedPane {

    /**
     * 
     */
    private static final long serialVersionUID = 2009073373970060149L;

    private WorldManager myWorldManager;
    private HashMap<String, JPanel> myTabs = new HashMap<String, JPanel>();

    public StageEditorPanel (WorldManager wm, String[] defaultTypes) {
        myWorldManager = wm;
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        for (String type : defaultTypes) {
            addTab(type, makeTab(type));
        }
        setSize(100, 450);
        repaint();
    }

    private JPanel makeTab (String type) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setPreferredSize(new Dimension(200, 600));
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setLayout(new ScrollPaneLayout());
        String typeAdd = "Edit " + type + "s";
        JButton addNewTypeOf = new JButton(typeAdd);
        final String curType = type;
        addNewTypeOf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed (ActionEvent event) {
                String method = "addNew" + curType;
                findAddMethod(method);
            }
        });
        panel.add(addNewTypeOf);
        // make subpanels for each variation of type

        List<String> tileNames = myWorldManager.get(type);
        for (int n = 0; n < tileNames.size(); n++) {
            JPanel p = new JPanel();

            p.setLayout(new BoxLayout(p, BoxLayout.LINE_AXIS));
            p.setBorder(BorderFactory.createTitledBorder(BorderFactory
                    .createLineBorder(Color.black), tileNames.get(n)));
            p.setPreferredSize(new Dimension(100, 50));

            ImageIcon i = new ImageIcon(myWorldManager.getImage(type, n));
            JLabel label = new JLabel(i);
            //JButton edit = new JButton("Edit");
            p.add(label);
            //p.add(edit);
            p.setMaximumSize(new Dimension(170,100));
            panel.add(p);
        }
        panel.revalidate();
        panel.repaint();
        myTabs.put(type, panel);
        return panel;
    }

    // private JPanel makeTab(String type){
    // JPanel panel = new JPanel();
    // GroupLayout layout = new GroupLayout(panel);
    // layout.setAutoCreateGaps(true);
    // layout.setAutoCreateContainerGaps(true);
    // panel.setLayout(layout);
    // panel.setPreferredSize(new Dimension(200, 600));
    // JScrollPane scroll = new JScrollPane(panel);
    // scroll.setLayout(new ScrollPaneLayout());
    // SequentialGroup sg = layout.createSequentialGroup();
    // ParallelGroup pg = layout.createParallelGroup();
    // //add button
    // String typeAdd = "Add new " + type;
    // JButton addNewOfType = new JButton(typeAdd);
    // final String curType = type;
    // addNewOfType.addActionListener(new ActionListener() {
    // @Override
    // public void actionPerformed(ActionEvent event) {
    // String method = "addNew"+curType;
    // findAddMethod(method);
    // }
    // });
    // sg.addComponent(addNewOfType);
    // pg.addComponent(addNewOfType);
    // //make subpanels for each variation of type
    // List<String> tileNames = myWorldManager.get(type);
    // for(int n = 0; n<tileNames.size(); n++){
    // JPanel p = new JPanel();
    // p.setLayout(new BoxLayout(p, BoxLayout.LINE_AXIS));
    // p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black),
    // tileNames.get(n)));
    // p.setPreferredSize(new Dimension(100,50));
    // ImageIcon i = new ImageIcon(myWorldManager.getImage(type, n));
    // JLabel label = new JLabel(i);
    // JButton edit = new JButton("Edit");
    // p.add(label);
    // p.add(edit);
    // sg.addComponent(p, 100, 100, 100);
    // pg.addComponent(p, 170, 170, 170);
    // }
    //
    // layout.setVerticalGroup(sg);
    // layout.setHorizontalGroup(pg);
    // panel.revalidate();
    // panel.repaint();
    // return panel;
    // }

    public void addNewTile () {
        JPanel newInfo = new JPanel();
        newInfo.setLayout(new GridLayout(0, 2));
        JLabel name = new JLabel("Name:");
        JLabel id = new JLabel("ID:");
        JLabel path = new JLabel("Image Source:");
        JLabel cost = new JLabel("Move Cost:");
        JTextField nameF = new JTextField(16);
        JTextField idF = new JTextField(4);
        JTextField pathF = new JTextField(30);
        JTextField costF = new JTextField(4);
        newInfo.add(name);
        newInfo.add(nameF);
        newInfo.add(id);
        newInfo.add(idF);
        newInfo.add(path);
        newInfo.add(pathF);
        newInfo.add(cost);
        newInfo.add(costF);

        int value =
                JOptionPane.showConfirmDialog(this, newInfo, "Enter Tile Information",
                                              JOptionPane.OK_CANCEL_OPTION);
        if (value == JOptionPane.OK_OPTION) {
            String tileName = nameF.getText();
            int tileID = Integer.parseInt(idF.getText());
            String imagePath = pathF.getText();
            int moveCost = Integer.parseInt(costF.getText());
            try {
                myWorldManager.setCustomTile(tileID, tileName, imagePath, moveCost);
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            addPaneltoTab("Tile", tileName, tileID);
        }
    }

    public void addNewGameUnit () {
        JPanel newInfo = new JPanel();
        newInfo.setLayout(new GridLayout(0, 2));
        JLabel name = new JLabel("Name:");
        JLabel id = new JLabel("ID:");
        JLabel path = new JLabel("Image Source:");
        JLabel affiliation = new JLabel("Affiliation:");
        JLabel isControllable = new JLabel("Controllable?");
        JTextField nameF = new JTextField(16);
        JTextField idF = new JTextField(4);
        JTextField pathF = new JTextField(30);
        JTextField affiliationF = new JTextField(4);
        JComboBox<String> controlChoices = new JComboBox<String>();
        controlChoices.addItem("true");
        controlChoices.addItem("false");
        newInfo.add(name);
        newInfo.add(nameF);
        newInfo.add(id);
        newInfo.add(idF);
        newInfo.add(path);
        newInfo.add(pathF);
        newInfo.add(affiliation);
        newInfo.add(affiliationF);
        newInfo.add(isControllable);
        newInfo.add(controlChoices);

        int value =
                JOptionPane.showConfirmDialog(this, newInfo, "Enter Unit Information",
                                              JOptionPane.OK_CANCEL_OPTION);
        if (value == JOptionPane.OK_OPTION) {
            String unitName = nameF.getText();
            int unitID = Integer.parseInt(idF.getText());
            String imagePath = pathF.getText();
            int aff = Integer.parseInt(affiliationF.getText());
            boolean controllable =
                    (((String) controlChoices.getSelectedItem()).equals("true")) ? true : false;
            myWorldManager.setCustomUnit(unitID, unitName, imagePath, aff, controllable);
            addPaneltoTab("GameUnit", unitName, unitID);
        }
    }

    private void addNewGameObject () {

    }

    private void addPaneltoTab (String type, String name, int imageID) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.LINE_AXIS));
        p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black), name));
        p.setMaximumSize(new Dimension(170,100));
        ImageIcon i = new ImageIcon(myWorldManager.getImage(type, imageID));
        JLabel label = new JLabel(i);
        //JButton edit = new JButton("Edit");
        p.add(label);
        //p.add(edit);
        JPanel tab = myTabs.get(type);
        tab.add(p);
    }

    private void findAddMethod (String method) {
        try {
            Method m = this.getClass().getMethod(method);
            m.invoke(this);
        }
        catch (NoSuchMethodException | SecurityException | IllegalAccessException
                | IllegalArgumentException |
                InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}
