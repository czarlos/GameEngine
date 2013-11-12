package view.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ScrollPaneLayout;
import controllers.WorldManager;

public class StageEditorPanel extends JTabbedPane {

    /**
     * 
     */
    private static final long serialVersionUID = 2009073373970060149L;
    
    private WorldManager myWorldManager;
    
    public StageEditorPanel(WorldManager wm){
        myWorldManager = wm;
        setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        addTab("Tile",makeTab("Tile"));
        addTab("GameUnit",makeTab("GameUnit"));
        addTab("GameObject",makeTab("GameObject"));
    }
    
    private JScrollPane makeTab(String type){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,1));
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setLayout(new ScrollPaneLayout());
        //make subpanels for each variation of type
        Map<String, Image> map = myWorldManager.get(type);
        for(String s:map.keySet()){
            JPanel p = new JPanel();
            //p.setLayout(new BoxLayout(p, BoxLayout.LINE_AXIS));
            p.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            p.setSize(100,50);
            ImageIcon i = new ImageIcon(map.get(s));
            JLabel label = new JLabel(s, i, JLabel.NORTH_EAST);
            JButton edit = new JButton("Edit");
            edit.setBorder(BorderFactory.createRaisedBevelBorder());
            p.add(label);
            p.add(edit);
            panel.add(p);
        }
        panel.revalidate();
        panel.repaint();
        return scroll;
    }
    
    
}
