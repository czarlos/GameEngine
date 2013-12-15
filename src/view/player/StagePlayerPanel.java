package view.player;

import grid.Coordinate;
import grid.GridConstants;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import controller.player.GridController;
import controllers.GameManager;
import view.GridWithSide;


/**
 * Panel to display a stage in the Player View. Contains a GridWithSide where 
 * the side panel is a SelectedInfoPanel to display information on the currently selected
 * coordinate. This panel also adds an End Turn button to the bottom of GridWithSide
 *
 */
@SuppressWarnings("serial")
public class StagePlayerPanel extends GridWithSide {
    private JScrollPane mySidePanel;
    private GridController myController;
    private GameManager myManager;
    private TurnActionsPanel myTurnActions;
    private SelectedInfoPanel myInfoPanel;

    /**
     * Create new StagePlayerPanel from a GameManager and PlayerView
     * @param manager To update SelectedInfoPanel with
     * @param pv To call end turn on when End Turn button clicked
     */
    public StagePlayerPanel (GameManager manager, PlayerView pv) {
        super(manager);
        myManager = manager;
        myController = new GridController(myManager, this);
        myGrid.addGridMouseListener(myController);
        myTurnActions = new TurnActionsPanel(pv);
        myInfoPanel = new SelectedInfoPanel(myController);

        //GridBagContstraints to add end turn button to bottom of 
        //GridWithSide
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 4;
        c.gridy = 4;
        c.fill = GridBagConstraints.NONE;
        c.gridheight = 1;
        c.gridwidth = 5;
        c.weightx = 0;
        c.weighty = 0;
        addCustomPanel(myTurnActions, c);
        repaint();
        revalidate();
    }

    @Override
    public void revalidate () {
        //updated SelectedInfoPanel on revalidate
        if (myController != null && myController.getCurrentCoordinate() != null) {
            updatedSelectedInfoPanel(myController.getCurrentCoordinate());
        }

        super.revalidate();

        //Repaint side panel after updated SelectedInfoPanel
        if (mySidePanel != null) {
            mySidePanel.repaint();
        }
    }

    /**
     * Updated the info shown in the side SelectedInfoPanel to reflect information for the 
     * given coordinate
     * 
     * @param c Coordinate who the side SelectedInfoPanel should show information on
     */
    public void updatedSelectedInfoPanel (Coordinate c) {

        myInfoPanel.makeTabs(myManager.getActionNames(c),
                             myManager.generateInfoList(GridConstants.TILE, c),
                             myManager.generateInfoList(GridConstants.GAMEOBJECT, c));
        if (mySidePanel != null) {
            remove(mySidePanel);
        }

        mySidePanel = new JScrollPane(myInfoPanel);
        mySidePanel.setMinimumSize(new Dimension(300, 0));
        addToSideColumn(mySidePanel);
        mySidePanel.repaint();
        repaint();
    }
}
