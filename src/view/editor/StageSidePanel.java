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
    private int myID;
    private final String[] defaultTypes = GridConstants.DEFAULTTABTYPES;
    private SelectedInfoPanel myInfoPanel;

    public StageSidePanel (WorldManager wm, String[] defaultTypes, int stageID) {
        myWorldManager = wm;
        myID = stageID;

        setSize(new Dimension(200, 500));
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        initSubPanels();
    }

    public void initSubPanels () {
        StageEditorPanel panel = new StageEditorPanel(myWorldManager,
                                                      defaultTypes, myID);
        panel.setSize(new Dimension(200, 200));
        add(panel);
        myInfoPanel = new SelectedInfoEditorPanel();

        myInfoPanel.setSize(new Dimension(200, 200));

        JScrollPane scroll = new JScrollPane(myInfoPanel);
        scroll.setLayout(new ScrollPaneLayout());
        scroll.setMinimumSize(new Dimension(200, 100));
        add(scroll);
    }

    public void displayInformation (Coordinate c) {
        myInfoPanel.removeAll();
        myInfoPanel.makeTabs(myWorldManager.getActions(c),
                             myWorldManager.generateInfoList(GridConstants.TILE, c),
                             myWorldManager.generateInfoList(GridConstants.GAMEOBJECT, c));
    }
}
