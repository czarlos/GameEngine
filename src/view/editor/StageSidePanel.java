package view.editor;

import grid.Coordinate;
import grid.GridConstants;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import view.player.SelectedInfoPanel;
import controllers.WorldManager;


@SuppressWarnings("serial")
public class StageSidePanel extends JPanel {

    private WorldManager myWorldManager;
    private final String[] defaultTypes = GridConstants.DEFAULTTABTYPES;
    private SelectedInfoPanel myInfoPanel;
    
    public StageSidePanel(WorldManager wm){
        setPreferredSize(new Dimension(300,500));
        myWorldManager = wm;
        

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        initSubPanels();
    }

    public void initSubPanels () {
        StageEditorPanel panel = new StageEditorPanel(myWorldManager,
                                                      defaultTypes);

        add(panel);
        myInfoPanel = new SelectedInfoEditorPanel(myWorldManager);

        JScrollPane scroll = new JScrollPane(myInfoPanel);
        scroll.setLayout(new ScrollPaneLayout());
        scroll.setMinimumSize(new Dimension(300,500));

        add(scroll);
    }

    public void displayInformation (Coordinate c) {
        myInfoPanel.removeAll();
        myInfoPanel.makeTabs(myWorldManager.getActions(c),
                             myWorldManager.generateInfoList(GridConstants.TILE, c),
                             myWorldManager.generateInfoList(GridConstants.GAMEOBJECT, c));
    }
}
