package view.editor;

import grid.Coordinate;
import grid.GridConstants;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import view.player.SelectedInfoPanel;
import controller.editor.GridEditorController;
import controllers.WorldManager;


@SuppressWarnings("serial")
public class StageSidePanel extends JPanel {

    private WorldManager myWorldManager;
    private final String[] defaultTypes = GridConstants.DEFAULTTABTYPES;
    private SelectedInfoPanel myInfoPanel;
    private GridEditorController myController;
    
    public StageSidePanel(WorldManager wm, GridEditorController controller){
        setPreferredSize(new Dimension(225,500));
        myWorldManager = wm;
        myController = controller;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        initSubPanels();
    }

    public void initSubPanels () {
        StageEditorPanel panel = new StageEditorPanel(myWorldManager,
                                                      defaultTypes);
        myController.addEditorPanel(panel);
        add(panel);
        myInfoPanel = new SelectedInfoEditorPanel();

        myInfoPanel.setMinimumSize(new Dimension(225,200));

        add(myInfoPanel);
    }

    public void displayInformation (Coordinate c) {
        myInfoPanel.makeTabs(myWorldManager.getActionNames(c),
                             myWorldManager.generateInfoList(GridConstants.TILE, c),
                             myWorldManager.generateInfoList(GridConstants.GAMEOBJECT, c),
                             myWorldManager.generateInfoList(GridConstants.GAMEUNIT, c));
    }
}
