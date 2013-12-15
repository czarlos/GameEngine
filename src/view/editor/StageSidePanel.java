package view.editor;

import grid.Coordinate;
import grid.GridConstants;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import view.player.SelectedInfoPanel;
import controller.editor.GridEditorController;
import controllers.WorldManager;


/**
 * Displays object info panels and editor panel for a stage.
 *
 */
@SuppressWarnings("serial")
public class StageSidePanel extends JPanel {

    private WorldManager myWorldManager;
    private final String[] defaultTypes = GridConstants.DEFAULTTABTYPES;
    private SelectedInfoPanel myInfoPanel;
    private GridEditorController myController;

    public StageSidePanel (WorldManager wm, GridEditorController controller) {
        setPreferredSize(new Dimension(225, 500));
        myWorldManager = wm;
        myController = controller;

        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        initSubPanels();
    }

    protected void initSubPanels () {
        StageEditorPanel panel = new StageEditorPanel(myWorldManager,
                                                      defaultTypes);
        myController.addEditorPanel(panel);
        add(panel);
        myInfoPanel = new SelectedInfoEditorPanel();

        myInfoPanel.setMinimumSize(new Dimension(225, 200));

        add(myInfoPanel);
    }

    
    /**
     * Select a coordinate for the Info Panel to show information about
     * @param c Selected coordinate
     */
    public void displayInformation (Coordinate c) {
        myInfoPanel.makeTabs(myWorldManager.getActionNames(c),
                             myWorldManager.generateInfoList(GridConstants.TILE, c),
                             myWorldManager.generateInfoList(GridConstants.GAMEOBJECT, c));
    }
}
