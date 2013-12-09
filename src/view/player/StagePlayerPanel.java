package view.player;

import grid.Coordinate;
import grid.GridConstants;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JScrollPane;
import controller.editor.GridController;
import controllers.GameManager;
import view.GridWithSide;


/**
 * TODO: Add documentation to this.
 * 
 * 
 */
@SuppressWarnings("serial")
public class StagePlayerPanel extends GridWithSide {
    private JScrollPane mySidePanel;
    private GridController myController;
    private GameManager myManager;
    private TurnActionsPanel myTurnActions;
    private SelectedInfoPanel myInfoPanel;

    public StagePlayerPanel (GameManager manager, PlayerView pv) {
        super(manager);
        myManager = manager;
        myController = new GridController(myManager, this);
        myGrid.addGridMouseListener(myController);
        myTurnActions = new TurnActionsPanel(pv);
        myInfoPanel = new SelectedInfoPanel(myController);

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
        if (myController != null && myController.getCurrentCoordinate() != null) {
            updatedSelectedInfoPanel(myController.getCurrentCoordinate());
        }

        super.revalidate();

        if (mySidePanel != null) {
            mySidePanel.repaint();
        }
    }

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
